package com.wang.business.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wang.common.object.entity.Tag;
import com.wang.common.object.vo.TagVo;

import java.util.List;

public interface TagService {


    List<Tag> queryTagListByUid(List<String> uids);

    IPage<Tag>  getPageTagList(TagVo tagVo);

}
