package com.chinamobile.springsecurity.demo;

import com.chinamobile.springsecurity.demo.dao.UserDao;
import com.chinamobile.springsecurity.demo.model.Role_PermissionDto;
import com.chinamobile.springsecurity.demo.utils.Md5Util;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SpringsecurityDemoApplicationTests {
    @Autowired
    UserDao userDao;

    @Test
    void contextLoads() {
        List<Role_PermissionDto> permissionsAndRoles = userDao.findPermissionsAndRoleByUserId(1);
        permissionsAndRoles.forEach(p -> {
            String code = p.getCode();
            System.out.println(code);
            String role_name = p.getRoleName();
            System.out.println(role_name);
        });
//        List<String> roles = new ArrayList<>();
//        List<String> permissions = new ArrayList<>();
//        permissionsAndRoles.forEach(p->{
//            String role = p.getRole();
//            System.out.println(role);
//            String permission = p.getPermission();
//            System.out.println(permission);
//        });
    }
    @Test
    public void testPasswordEncoder() {
        String encode = Md5Util.stringToMd5("123");
        System.out.println(encode);
    }

}
