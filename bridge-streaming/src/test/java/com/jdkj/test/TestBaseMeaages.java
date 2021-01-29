package com.jdkj.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jdkj.event.*;
import org.apache.atlas.utils.AtlasJson;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TestBaseMeaages {
    public static void main(String[] args) throws IOException {
        MetaKafkaMessage metaKafkaMessage = new MetaKafkaMessage();
        metaKafkaMessage.setActionType(ActionType.INSERT);
        metaKafkaMessage.setOwner("jiangzz");
        metaKafkaMessage.setCreateTime(new Date());
        metaKafkaMessage.setLastUpdate(new Date());
        ServiceIdentify serviceIdentify = new ServiceIdentify("业务模型", ServiceType.Market);
        metaKafkaMessage.setServiceIdentify(serviceIdentify);

        metaKafkaMessage.setRelationsRef(new ServiceIdentify[]{serviceIdentify});
        TableIdentify tableIdentify = new TableIdentify("5k", "com/jdkj/test", "t_user");
        metaKafkaMessage.setTableRefs(new TableIdentify[]{tableIdentify});

        HashMap<String, Object> ext = new HashMap<>();
        ext.put("date",new Date());
        ext.put("sex",true);
        ext.put("double",100.0f);
        metaKafkaMessage.setExtInfo(ext);

        ObjectMapper objectMapper = new ObjectMapper();

        String values= AtlasJson.toJson(metaKafkaMessage);
                //objectMapper.writeValueAsString(metaKafkaMessage);
        System.out.println(values);
         metaKafkaMessage = objectMapper.readValue(values, MetaKafkaMessage.class);
        System.out.println(metaKafkaMessage.getActionType());

        Map<String, Object> extInfo = metaKafkaMessage.getExtInfo();
        System.out.println(metaKafkaMessage.getExtInfo());
        for (Map.Entry<String,Object> enty:extInfo.entrySet()){
            System.out.println(enty.getKey()+"  "+enty.getValue().getClass());
        }

    }
}
