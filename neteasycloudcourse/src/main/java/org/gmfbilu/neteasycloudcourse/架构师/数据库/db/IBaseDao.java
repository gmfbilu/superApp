package org.gmfbilu.neteasycloudcourse.架构师.数据库.db;

import java.util.List;

public interface IBaseDao<T>{

    //插入一条数据，因为不知道插入具体什么对象，使用泛型替代
    long insert(T entity);

    //更新一条数据，entity是更新后的数据，where是更新前的数据
    long update(T entity,T where);

    //删除一条数据，，where是需要被删除的数据
    int delete(T where);

    //查询数据，where是需要查询的数据
    List<T> query(T where);

    //orderBy以什么样的排序方式，startIndex代表是从哪条数据开始取，limit是查询一次取多少条
    List<T> query(T where,String orderBy,Integer startIndex,Integer limit);

    //where是具体查语句
    List<T> query(T where,String queryKey,String[] queryValue,String orderBy,Integer startIndex,Integer limit);
}
