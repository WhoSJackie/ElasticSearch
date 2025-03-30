package com.wang.business.service.test.impl;

import com.wang.business.dao.RdtRuleItemDao;
import com.wang.business.service.test.RuleItemService;
import com.wang.business.utils.WordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RuleItemServiceImpl implements RuleItemService {

    public static Logger log = LoggerFactory.getLogger(RuleItemServiceImpl.class);

    @Autowired
    private RdtRuleItemDao rdtRuleItemDao;

    @Override
    public List<String> nullableRuleVerify(String filePath,String rpcode) {
        List<String> resList = new ArrayList<>();
        // 查询目前存在的校验字段
        List<String> res = rdtRuleItemDao.queryRuleItemElement(rpcode);
        // 获取非空字段
        List<String> list = WordUtil.wordReadForRuleItemVerify(filePath);
        for (String s : list) {
            if (!res.contains(s)) {
                resList.add(s);
            }
        }
        return resList;
    }



}
