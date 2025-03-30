package com.wang.business.mysqldao.test;

import com.wang.common.object.entity.test.SysDictItemPojo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysDictDao {

    Boolean insert(@Param("item") SysDictItemPojo pojo);

    List<SysDictItemPojo> querySysDictValue(@Param("item") SysDictItemPojo sysDictItemPojo);


}
