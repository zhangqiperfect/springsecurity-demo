package com.chinamobile.springsecurity.demo.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

/**
 * @author ZQ
 * @create 2020-07-15
 * @description
 */
@Service
public class MethodService {
    //    @Secured("ROLE_ADMIN")
//    public String admin() {
//        return "hello admin";
//    }
//
//    @PreAuthorize("hasRole('ADMIN') and hasRole('DBA')")
//    public String dba() {
//        return "hello dba";
//    }
//
//    @PreAuthorize("hasAnyRole('ADMIN','DBA','USER')")
//    public String user() {
//        return "hello user";
//    }
    @PreAuthorize("hasAuthority('admin')")
    public String admin() {
        return "hello admin";
    }

    @PreAuthorize("hasAuthority('dba')")
    public String dba() {
        return "hello dba";
    }

    @PreAuthorize("hasAuthority('user')")
    public String user() {
        return "hello user";
    }
}
