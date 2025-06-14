package com.wang.business.mysqldao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wang.common.object.entity.BlogSort;
import com.wang.common.object.entity.Tag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TagDao extends BaseMapper<Tag> {

    int updateBatchStatusByIds(@Param("uisList") List<String> uidList);

}
