package com.jdkj.dao;

import com.jdkj.entity.BusinessSystem;
import com.jdkj.entity.ExtProperty;
import org.elasticsearch.search.sort.SortOrder;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;
@Validated
public interface IESModelDAO<T>{
    public static enum Sort{
        DESC,ASC;
    }
    public T save(@NotNull T t);
    public T queryByName(@NotNull String name);
    public T queryByID(@NotNull String id);
    public List<T> queryAll(@NotNull Integer pageNow, @NotNull Integer pageSize, String field, SortOrder order);
    public List<T> search(@NotNull String q,@NotNull Integer pageNow, @NotNull Integer pageSize, String field, SortOrder order);


    public boolean update(@NotNull T t);


    /*扩展属性查询*/
    public List<ExtProperty> queryExtProperties(@NotNull String idOrName);
    public boolean updateExtProperties(@NotEmpty List<ExtProperty> extProperties, @NotNull String idOrName);
    public boolean addExtProperty(@NotNull String key,@NotNull String value,@NotNull String idOrName);
    public boolean deleteExtProperty(@NotNull String idOrName,@NotNull String... key);

}
