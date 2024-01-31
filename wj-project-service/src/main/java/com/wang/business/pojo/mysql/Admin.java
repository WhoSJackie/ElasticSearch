package com.wang.business.pojo.mysql;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_admin")
public class Admin {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    private String username;

    private String passWord;

    private int gender;

    private String email;

    private Date birthday;

    private long roleUid;

    private String mobile;

    private String validCode;

    private Integer loginCount;

    private Date lastLoginTime;

    private String lastLoginIp;

    private int status;

}
