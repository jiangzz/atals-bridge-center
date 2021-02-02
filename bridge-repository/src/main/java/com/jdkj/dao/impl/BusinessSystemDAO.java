package com.jdkj.dao.impl;

import com.jdkj.dao.IBusinessSystemDAO;
import com.jdkj.entity.BusinessSystem;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class BusinessSystemDAO extends ESModelDAO<BusinessSystem> implements IBusinessSystemDAO{

    @Override
    public boolean addBusinessSectors(List<String> names, String idOrName) {
        Criteria criteria = Criteria.where("type").is(targetClass.getSimpleName())
                .or(Criteria.where("name").is(idOrName))
                .or(Criteria.where("_id").is(idOrName));

        CriteriaQuery criteriaQuery=new CriteriaQuery(criteria);
        BusinessSystem businessSystem = (BusinessSystem) template.queryForObject(criteriaQuery,targetClass);
        if(businessSystem==null){
            return false;
        }
        businessSystem.setBusinessSectors(names);
        return update(businessSystem);
    }

    @Override
    public boolean addBusinessSector(String name, String idOrName) {
        Criteria criteria = Criteria.where("type").is(targetClass.getSimpleName())
                .or(Criteria.where("name").is(idOrName))
                .or(Criteria.where("_id").is(idOrName));

        CriteriaQuery criteriaQuery=new CriteriaQuery(criteria);
        BusinessSystem businessSystem = (BusinessSystem) template.queryForObject(criteriaQuery, targetClass);
        if(businessSystem==null){
            return false;
        }
        List<String> businessSectors = businessSystem.getBusinessSectors();
        if (businessSectors==null){
            businessSectors=new ArrayList<>();
            businessSystem.setBusinessSectors(businessSectors);
        }
        businessSectors.add(name);
        businessSystem.setBusinessSectors(businessSectors.stream().distinct().collect(Collectors.toList()));
        return update(businessSystem);
    }

    @Override
    public boolean deleteBusinessSector(String name, String idOrName) {
        Criteria criteria = Criteria.where("type").is(targetClass.getSimpleName())
                .or(Criteria.where("name").is(idOrName))
                .or(Criteria.where("_id").is(idOrName));

        CriteriaQuery criteriaQuery=new CriteriaQuery(criteria);
        BusinessSystem businessSystem = (BusinessSystem) template.queryForObject(criteriaQuery, targetClass);
        if(businessSystem==null){
            return false;
        }
        List<String> businessSectors = businessSystem.getBusinessSectors();
        if (businessSectors==null){
            return true;
        }
        businessSectors.remove(name);
        return update(businessSystem);
    }
}
