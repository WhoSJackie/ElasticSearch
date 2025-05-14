package com.wang.common.object.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.wang.common.object.vo.SuperEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_role")
public class Role extends SuperEntity<Role> {


    private String roleName;

    private int status;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String summary;

    /**
     * 该角色所能管辖的区域
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String categoryMenuUids;

}
