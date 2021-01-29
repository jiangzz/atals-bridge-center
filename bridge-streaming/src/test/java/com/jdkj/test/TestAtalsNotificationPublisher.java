package com.jdkj.test;

import com.jdkj.KafkaStreamSpringBootApplication;
import com.jdkj.notifications.AtalsNotificationPublisher;
import org.apache.atlas.model.instance.AtlasEntity;
import org.apache.atlas.model.instance.AtlasEntity.AtlasEntitiesWithExtInfo;
import org.apache.atlas.model.instance.AtlasObjectId;
import org.apache.atlas.model.instance.AtlasRelatedObjectId;
import org.apache.atlas.model.notification.HookNotification;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {KafkaStreamSpringBootApplication.class})
public class TestAtalsNotificationPublisher {
    @Autowired
    private AtalsNotificationPublisher publisher;
    @Test
    public void testPublisher(){
        AtlasEntity entity = new AtlasEntity("User");
        entity.setAttribute("qualifiedName","文玉@王");
        entity.setAttribute("name","文玉");

        AtlasObjectId atlasObjectId = new AtlasObjectId("User", "qualifiedName", "中洲@蒋");
        entity.setRelationshipAttribute("husband",new AtlasRelatedObjectId(atlasObjectId,"Wife_Husband"));

        ArrayList<HookNotification> messages = new ArrayList<>();
        AtlasEntitiesWithExtInfo ext = new AtlasEntitiesWithExtInfo();
        ext.addEntity(entity);
        messages.add(new HookNotification.EntityUpdateRequestV2("jiangzz",ext));
        //发布消息
        publisher.publishMesssage(messages);
    }

}
