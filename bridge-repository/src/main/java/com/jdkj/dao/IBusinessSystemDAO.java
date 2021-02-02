package com.jdkj.dao;


import com.jdkj.entity.BusinessSystem;
import com.jdkj.entity.ExtProperty;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
public interface IBusinessSystemDAO extends IESModelDAO<BusinessSystem> {
      //业务板块属性
      public boolean addBusinessSectors(@NotEmpty List<String> names,@NotNull String idOrName);
      public boolean addBusinessSector(@NotNull String name,@NotNull String idOrName);
      public boolean deleteBusinessSector(@NotNull String name,@NotNull String idOrName);

}
