package com.chinamobile.springsecurity.demo.controller;

import com.chinamobile.springsecurity.demo.service.MethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ZQ
 * @create 2020-07-15
 * @description
 */
@RestController
public class HelloController {
    @Autowired
    MethodService methodService;

    @RequestMapping("/admin/hello")
    public String admin() {
//
        return methodService.admin();
    }

    @RequestMapping("/user/hello")
    public String user() {
//        return "hello user";
        return methodService.user();
    }

    @RequestMapping("/dba/hello")
    public String dba() {
//        return "hello dba";
        return methodService.dba();
    }

    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }
}
