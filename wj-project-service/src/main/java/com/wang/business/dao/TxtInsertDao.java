package com.wang.business.dao;

import com.wang.business.pojo.TxtInsertPojo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TxtInsertDao {

    boolean insertIntoMappingTb(@Param("item")TxtInsertPojo item);


}
