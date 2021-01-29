package com.jdkj.model;

import org.apache.atlas.*;
import org.apache.atlas.model.SearchFilter;
import org.apache.atlas.model.typedef.AtlasEntityDef;
import org.apache.atlas.model.typedef.AtlasRelationshipDef;
import org.apache.atlas.model.typedef.AtlasTypesDef;
import org.apache.atlas.utils.AtlasJson;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.List;

public class InitModel {
    public static void main(String[] args) throws IOException, AtlasException, AtlasServiceException {
        String allTypeJosn = initAllEntityModel();
        AtlasTypesDef atlasTypesDef = AtlasJson.fromJson(allTypeJosn, AtlasTypesDef.class);
        AtlasClientV2 atlasClientV2=  new AtlasClientV2(ApplicationProperties.get(),new String[]{"http://CentOS:9000"},new String[]{"admin","admin"});
        deleteRelationshipTypes(atlasTypesDef,atlasClientV2);

    }
    public static void deleteDefineTypes(String typedefs,AtlasClientV2 client) throws AtlasServiceException {
        AtlasTypesDef atlasTypesDef = AtlasJson.fromJson(typedefs, AtlasTypesDef.class);
        List<AtlasEntityDef> entityDefs = atlasTypesDef.getEntityDefs();
        for (AtlasEntityDef entityDef : entityDefs) {
            boolean exits = client.typeWithNameExists(entityDef.getName());
            if(exits){
                SearchFilter searchFilter = new SearchFilter();
                searchFilter.setParam("name",entityDef.getName());
                AtlasTypesDef allTypeDefs = client.getAllTypeDefs(searchFilter);
                AtlasEntityDef e = allTypeDefs.getEntityDefs().get(0);
                if(!e.getSubTypes().isEmpty()){
                    continue;
                }
                AtlasTypesDef deleteTypes = new AtlasTypesDef();
                deleteTypes.getEntityDefs().add(entityDef);
                client.deleteAtlasTypeDefs(deleteTypes);
            }
        }
    }
    public static void deleteRelationshipTypes(AtlasTypesDef atlasTypesDef ,AtlasClientV2 client) throws AtlasServiceException {
        List<AtlasRelationshipDef> relationshipDefs = atlasTypesDef.getRelationshipDefs();
        for (AtlasRelationshipDef relationshipDef : relationshipDefs) {
            System.out.println(relationshipDef.getName());
            AtlasBaseClient.API api = new AtlasBaseClient.API("api/atlas/v2/types/typedef/name/", HttpMethod.DELETE, Response.Status.NO_CONTENT);
            client.callAPI(api, (Class) null, null, relationshipDef.getName());
        }
    }
    public static String initEntityModel() throws IOException {
        InputStream resourceAsStream = InitModel.class.getClassLoader().getResourceAsStream("typedefs_v1.json");
        InputStreamReader inputStreamReader = new InputStreamReader(resourceAsStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        StringBuilder sb=new StringBuilder();
        String line=null;
        while((line=bufferedReader.readLine())!=null){
            sb.append(line);
        }
        bufferedReader.close();
        return sb.toString();
    }
    public static String initRelationshipModel() throws IOException {
        InputStream resourceAsStream = InitModel.class.getClassLoader().getResourceAsStream("relationship_v1.json");
        InputStreamReader inputStreamReader = new InputStreamReader(resourceAsStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        StringBuilder sb=new StringBuilder();
        String line=null;
        while((line=bufferedReader.readLine())!=null){
            sb.append(line);
        }
        bufferedReader.close();
        return sb.toString();
    }
    public static String initAllEntityModel() throws IOException {
        InputStream resourceAsStream = InitModel.class.getClassLoader().getResourceAsStream("allinone_v1.json");
        InputStreamReader inputStreamReader = new InputStreamReader(resourceAsStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        StringBuilder sb=new StringBuilder();
        String line=null;
        while((line=bufferedReader.readLine())!=null){
            sb.append(line);
        }
        bufferedReader.close();
        return sb.toString();
    }

}
