package com.wang.common.object.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysDictSingleInfo {

    List<SysDictData> dictValueList;

    String defaultValue;

}
