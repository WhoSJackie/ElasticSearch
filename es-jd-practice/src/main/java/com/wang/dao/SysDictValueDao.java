package com.wang.dao;

import com.wang.pojo.oracle.SysDictValuePojo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysDictValueDao {

    List<SysDictValuePojo> getSysDictValue(@Param("item") SysDictValuePojo sysDictValuePojo);

}
