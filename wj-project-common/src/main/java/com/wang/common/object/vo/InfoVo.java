package com.wang.common.object.vo;

import com.wang.common.object.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InfoVo{

    private List<Role> roles;

    private String  avatar;

    private String token;

    private String name;

}
