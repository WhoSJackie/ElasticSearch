package com.wang.dao;

import com.wang.pojo.TxtInsertPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface TxtInsertDao {

    boolean insertIntoMappingTb(@Param("item")TxtInsertPojo item);


}
