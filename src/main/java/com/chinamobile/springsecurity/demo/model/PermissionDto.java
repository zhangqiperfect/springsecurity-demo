package com.chinamobile.springsecurity.demo.model;

import lombok.Data;

/**
 * @author Administrator
 * @version 1.0
 **/
@Data
public class PermissionDto {

    private Integer id;
    private String code;
    private String description;
    private String url;
}
