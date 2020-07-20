package com.chinamobile.springsecurity.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ZQ
 * @create 2020-07-16
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role_PermissionDto {
    private String roleName;
    private String code;
}
