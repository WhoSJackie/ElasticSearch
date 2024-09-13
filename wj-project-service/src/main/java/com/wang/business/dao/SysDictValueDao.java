package com.wang.business.dao;

import com.wang.business.pojo.oracle.SysDictIndexPoJo;
import com.wang.business.pojo.oracle.SysDictValuePojo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysDictValueDao {

    List<SysDictValuePojo> getSysDictValue(@Param("item") SysDictValuePojo sysDictValuePojo);

    Integer getLatestDictItem();

    int insertDictValue(@Param("item") SysDictValuePojo pojo);

    int insertDictIndex(@Param("item") SysDictIndexPoJo pojo);
}
