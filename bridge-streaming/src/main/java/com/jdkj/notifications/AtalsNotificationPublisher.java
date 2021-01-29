package com.jdkj.notifications;

import org.apache.atlas.hook.AtlasHook;
import org.apache.atlas.model.notification.HookNotification;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(value = "singleton")
public class AtalsNotificationPublisher extends AtlasHook {
    public  void publishMesssage(List<HookNotification> messages){
        super.notifyEntities(messages,null);
    }
}
