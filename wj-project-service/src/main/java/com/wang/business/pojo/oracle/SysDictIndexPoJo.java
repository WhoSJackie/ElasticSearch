package com.wang.business.pojo.oracle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysDictIndexPoJo {

    private Integer dictitem;

    private String itemname;

    private String loadlevel;

    private Integer validlen;

    private String dispflag;

    private String ctrlflag;

    private String dictgroupid;

    private String usersetflag;

}
