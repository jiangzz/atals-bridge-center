package com.jdkj.test;

import com.jdkj.KafkaStreamSpringBootApplication;
import com.jdkj.dao.IBusinessSystemDAO;
import com.jdkj.entity.BusinessSystem;
import com.jdkj.entity.ExtProperty;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {KafkaStreamSpringBootApplication.class})
public class TestBusinessSystemDAO {
    @Autowired
    private IBusinessSystemDAO dao;
    @Autowired
    private ElasticsearchTemplate template;

    @Test
    public void testSave() {
        BusinessSystem businessSystem = new BusinessSystem("京东科技", "京东科技", "...", "蒋中洲", "蒋中洲", new Date(), new Date());
        ArrayList<ExtProperty> exts = new ArrayList<>();
        exts.add(new ExtProperty("creator","李四"));
        businessSystem.setExtInfos(exts);
        BusinessSystem pbs=dao.save(businessSystem);
        System.out.println(pbs);
    }
    @Test
    public void testQuery() {
        BusinessSystem businessSystem = dao.queryByID("M_t7VncBpFCsWzL9Fb47");
        System.out.println(businessSystem);
    }
    @Test
    public void testQueryAll() {
        List<BusinessSystem> businessSystems = dao.queryAll(1, 2, "name", SortOrder.ASC);
        for (BusinessSystem businessSystem : businessSystems) {
            System.out.println(businessSystem);
        }
    }

    @Test
    public void testUpdate() {
        BusinessSystem businessSystem = dao.queryByID("NPt8VncBpFCsWzL99r57");
        businessSystem.setDescription("这是数字百科...");
        businessSystem.setName("京东数字科技");
        businessSystem.setDisplayName("京东数字科技");
        businessSystem.setDescription("...");

        ArrayList<ExtProperty> exts = new ArrayList<>();
        exts.add(new ExtProperty("creator","zhangsan"));
        businessSystem.setExtInfos(exts);
        businessSystem.setOwner("赵晓丽");
        businessSystem.setBusinessSectors(Arrays.asList("板块A","板块B"));
        dao.update(businessSystem);
    }
    @Test
    public void testQueryExtInfo() {
        List<ExtProperty> properties = dao.queryExtProperties("NPt8VncBpFCsWzL99r57");
        if(properties!=null){
            for (ExtProperty property : properties) {
                System.out.println(property);
            }
        }
    }
    @Test
    public void testDeleteExtInfo() {
        dao.deleteExtProperty("NPt8VncBpFCsWzL99r57","creator","time");
    }
    @Test
    public void testAddExtInfo() {
        dao.addExtProperty("owner","蒋泽宇","NPt8VncBpFCsWzL99r57");
    }
    @Test
    public void testUpdateExtProperties() {
        ArrayList<ExtProperty> exts = new ArrayList<>();
        exts.add(new ExtProperty("creator","蒋中洲"));
        exts.add(new ExtProperty("time","2020-12-13"));
        dao.updateExtProperties(exts,"NPt8VncBpFCsWzL99r57");
    }


    @Test
    public void testAddBusinessSector() {
        dao.addBusinessSector("板块C","NPt8VncBpFCsWzL99r57");
    }
    @Test
    public void testUpdateBusinessSectors() {
        dao.addBusinessSectors(Arrays.asList("板块A","板块B"),"MfvIVXcBpFCsWzL9B77L");
    }
    @Test
    public void testDeleteBusinessSector() {
        dao.deleteBusinessSector("板块A","MfvIVXcBpFCsWzL9B77L");
    }
    @Test
    public void testSearch() {
        List<BusinessSystem> businessSystems = dao.search("板块", 1, 15, null, SortOrder.DESC);
        for (BusinessSystem businessSystem : businessSystems) {
            System.out.println(businessSystem);
        }
    }

}
