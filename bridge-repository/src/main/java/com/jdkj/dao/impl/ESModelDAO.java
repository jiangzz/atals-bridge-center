package com.jdkj.dao.impl;

import com.jdkj.dao.IESModelDAO;
import com.jdkj.entity.ESBaseModel;
import com.jdkj.entity.ExtProperty;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.json.JsonXContent;
import org.elasticsearch.index.query.*;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.UpdateByQueryAction;
import org.elasticsearch.index.reindex.UpdateByQueryRequestBuilder;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.*;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.*;

public class ESModelDAO<T> implements IESModelDAO<T> {
    @Autowired
    ElasticsearchTemplate template;
    protected Class targetClass=null;
    private FieldSortBuilder order;
    public ESModelDAO() {
        ParameterizedType pt = (ParameterizedType)this.getClass().getGenericSuperclass();
        targetClass = (Class< T >)pt.getActualTypeArguments()[0];
    }

    @Override
    public T save(T t) {
        IndexQuery indexQuery=new IndexQueryBuilder().withObject(t).build();
        template.index(indexQuery);
        return t;
    }

    @Override
    public T queryByName(String name) {
        Criteria criteria = Criteria.where("name").is(name)
                .and(Criteria.where("type").is(targetClass.getSimpleName()));
        CriteriaQuery criteriaQuery=new CriteriaQuery(criteria);
        return (T) template.queryForObject(criteriaQuery, targetClass);
    }

    @Override
    public T queryByID(String id) {
        GetQuery query = new GetQuery();
        query.setId(id);
        return (T) template.queryForObject(query,targetClass);
    }

    @Override
    public boolean update(T t) {
        try{
            UpdateRequest updateRequest = new UpdateRequest();

            XContentBuilder xContentBuilder = JsonXContent.contentBuilder();
            xContentBuilder.startObject();
            //反射获取属性值
            Method[] methods = targetClass.getMethods();
            Arrays.stream(methods)
                    .filter(m->m.getName().startsWith("get") && !m.getName().equals("getClass"))
                    .forEach(m->{
                        try {
                            String key=m.getName().substring(3);
                            String fieldName=new StringBuilder().append(Character.toLowerCase(key.charAt(0))).append(key.substring(1)).toString();
                            Object value = m.invoke(t);
                            if(!fieldName.equals("extInfos") ){
                                xContentBuilder.field(fieldName,value);
                            }else {
                                //单独处理扩展属性
                                List<ExtProperty> extInfos = (List<ExtProperty>) value;
                                xContentBuilder.startArray("extInfos");
                                if(extInfos!=null && extInfos.size()>0){
                                    for (ExtProperty extInfo : extInfos) {
                                        xContentBuilder.startObject();
                                        xContentBuilder.field("key",extInfo.getKey());
                                        xContentBuilder.field("value",extInfo.getValue());
                                        xContentBuilder.endObject();
                                    }
                                }
                                xContentBuilder.endArray();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
            xContentBuilder.endObject();
            updateRequest.doc(xContentBuilder);
            UpdateQuery updateQuery = new UpdateQuery();
            updateQuery.setClazz(targetClass);
            updateQuery.setId(((ESBaseModel)t).getId());
            updateQuery.setDoUpsert(false);
            updateQuery.setUpdateRequest(updateRequest);

            template.update(updateQuery);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<T> queryAll(Integer pageNow, Integer pageSize, String field, SortOrder sort) {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("serviceType",targetClass.getSimpleName()))
                .withPageable( PageRequest.of((pageNow - 1) * pageSize, pageSize));
        if(field!=null){
            queryBuilder.withSort(SortBuilders.fieldSort(field).order(sort));
        }


        return template.queryForList(queryBuilder.build(),targetClass);
    }

    @Override
    public List<T> search(String q, Integer pageNow, Integer pageSize, String field, SortOrder order) {

        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.
                multiMatchQuery(q, "name", "displayName", "description", "project", "owner", "updater").analyzer("ik_max_word");
        MatchQueryBuilder matchQuery = QueryBuilders.matchQuery("type", targetClass.getSimpleName());
        NestedQueryBuilder nestedQuery = QueryBuilders.nestedQuery("extInfos", QueryBuilders.matchQuery("extInfos.value",q).analyzer("ik_max_word"), ScoreMode.Avg);

        BoolQueryBuilder booleanQuery = QueryBuilders.boolQuery().must(matchQuery)
                .should(multiMatchQueryBuilder)
                .should(nestedQuery);
                if(q.equals("")){
                    booleanQuery.minimumShouldMatch(0);
                }else{
                    booleanQuery.minimumShouldMatch(1);
                }

        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder()
                .withQuery(booleanQuery)
                .withPageable( PageRequest.of((pageNow - 1) * pageSize, pageSize));
                if(field!=null){
                    queryBuilder.withSort(SortBuilders.fieldSort(field).order(order));
                }
        return template.queryForList(queryBuilder.build(),targetClass);
    }

    @Override
    public List<ExtProperty> queryExtProperties(String idOrName) {
        Criteria criteria = Criteria.where("type").is(targetClass.getSimpleName())
                .or(Criteria.where("name").is(idOrName))
                .or(Criteria.where("_id").is(idOrName));

        CriteriaQuery criteriaQuery=new CriteriaQuery(criteria);
        ESBaseModel t = (ESBaseModel) template.queryForObject(criteriaQuery, targetClass);
        if(t!=null && t.getExtInfos()!=null){
            return t.getExtInfos();
        }
        return null;
    }

    @Override
    public boolean updateExtProperties(List<ExtProperty> extProperties, String idOrName) {
        Client client = template.getClient();
        Map<String, Object> params = new HashMap<>();
        ArrayList<Map<String, String>> extInfos = new ArrayList<>();
        for (ExtProperty extInfo : extProperties) {
            HashMap<String, String> extKeyValue = new HashMap<>();
            extKeyValue.put("key",extInfo.getKey());
            extKeyValue.put("value",extInfo.getValue());
            extInfos.add(extKeyValue);
        }
        params.put("extInfos",extInfos);
        Script script = new Script(ScriptType.INLINE, Script.DEFAULT_SCRIPT_LANG,"ctx._source.extInfos=params.extInfos",params);

        UpdateByQueryRequestBuilder updateByQueryRequestBuilder = UpdateByQueryAction.INSTANCE.newRequestBuilder(client);
        updateByQueryRequestBuilder.source("atlas")//索引数据
                .filter(QueryBuilders.boolQuery()
                        .must(QueryBuilders.termQuery("serviceType", targetClass.getSimpleName()))
                        .should(QueryBuilders.termQuery("_id", idOrName))
                        .should(QueryBuilders.termQuery("name", idOrName))
                        .minimumShouldMatch(1)
                )
                .script(script);
        try {
            BulkByScrollResponse bulkByScrollResponse = updateByQueryRequestBuilder.get();
            if(bulkByScrollResponse.getUpdated()!=1) {
                return false;
            }else{
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean addExtProperty(String key, String value, String idOrName) {
        Client client = template.getClient();
        Map<String, Object> params = new HashMap<>();
        HashMap<String, Object> property = new HashMap<>();
        property.put("key",key);
        property.put("value",value);
        params.put("keyValue",property);
        String idOrCode=" try {\n" +
                "    List extInfos = ctx._source.extInfos;\n" +
                "    if(extInfos!=null && extInfos.size()>0){\n" +
                "       if(!extInfos.contains(params.keyValue)){\n" +
                "         extInfos.add(params.keyValue);\n" +
                "       }\n" +
                "    }else{\n" +
                "        ctx._source.extInfos=[];\n" +
                "        ctx._source.extInfos.add(params.keyValue);\n" +
                "    }\n" +
                "    \n" +
                "} catch (Exception e) {\n" +
                "}";
        Script script = new Script(ScriptType.INLINE, Script.DEFAULT_SCRIPT_LANG,idOrCode,params);

        UpdateByQueryRequestBuilder updateByQueryRequestBuilder = UpdateByQueryAction.INSTANCE.newRequestBuilder(client);
        updateByQueryRequestBuilder.source("atlas")//索引数据
                .filter(QueryBuilders.boolQuery()
                        .must(QueryBuilders.termQuery("serviceType", targetClass.getSimpleName()))
                        .should(QueryBuilders.termQuery("_id", idOrName))
                        .should(QueryBuilders.termQuery("name", idOrName))
                        .minimumShouldMatch(1)
                )
                .script(script);
        try {
            BulkByScrollResponse bulkByScrollResponse = updateByQueryRequestBuilder.get();
            if(bulkByScrollResponse.getUpdated()!=1) {
                return false;
            }else{
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteExtProperty( String idOrName,String... keys) {
        Client client = template.getClient();
        Map<String, Object> params = new HashMap<>();
        params.put("keys",keys);
        String idOrCode="try {\n" +
                "       ctx._source.remove('keys');\n" +
                "        List extInfos = ctx._source.extInfos;\n" +
                "        if(extInfos!=null && extInfos.size()  > 0){\n" +
                "          for (int i = 0; i < extInfos.length; i++) {\n" +
                "             for (int j = 0; j < params.keys.length; j++){\n" +
                "               if(extInfos[i].key==params.keys[j]){\n" +
                "                 ctx._source.extInfos.remove(i);\n" +
                "               }\n" +
                "             }\n" +
                "          }\n" +
                "        }\n" +
                "        \n" +
                "    } catch (Exception e) {\n" +
                "    }";
        Script script = new Script(ScriptType.INLINE, Script.DEFAULT_SCRIPT_LANG,idOrCode,params);

        UpdateByQueryRequestBuilder updateByQueryRequestBuilder = UpdateByQueryAction.INSTANCE.newRequestBuilder(client);
        updateByQueryRequestBuilder.source("atlas")//索引数据
                .filter(QueryBuilders.boolQuery()
                        .must(QueryBuilders.termQuery("serviceType", targetClass.getSimpleName()))
                        .should(QueryBuilders.termQuery("_id", idOrName))
                        .should(QueryBuilders.termQuery("name", idOrName))
                        .minimumShouldMatch(1)
                )
                .script(script);
        try {
            BulkByScrollResponse bulkByScrollResponse = updateByQueryRequestBuilder.get();
            if(bulkByScrollResponse.getUpdated()!=1) {
                return false;
            }else{
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
