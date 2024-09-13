package com.wang.business.pojo.mysql;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

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

    private String roleUid;

    private String mobile;

    private String validCode;

    private Integer loginCount;

    private Date lastLoginTime;

    private String lastLoginIp;

    private int status;

    /**
     * 令牌UID【主要用于换取token令牌，防止token直接暴露到在线用户管理中】
     */
    @TableField(exist = false)
    private String tokenUid;

    /**
     * 所拥有的角色名
     */
    @TableField(exist = false)
    private List<String> roleNames;

    /**
     * 所拥有的角色名
     */
    @TableField(exist = false)
    private Role role;

}

