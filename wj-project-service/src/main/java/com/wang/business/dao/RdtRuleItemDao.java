package com.wang.business.dao;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RdtRuleItemDao {

    List<String> queryRuleItemElement(String rpcode);


}
