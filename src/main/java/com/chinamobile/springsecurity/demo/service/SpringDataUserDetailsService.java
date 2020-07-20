package com.chinamobile.springsecurity.demo.service;


import com.chinamobile.springsecurity.demo.dao.UserDao;
import com.chinamobile.springsecurity.demo.model.Role_PermissionDto;
import com.chinamobile.springsecurity.demo.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 **/
@Service
public class SpringDataUserDetailsService implements UserDetailsService {
    @Autowired
    UserDao userDao;

    //根据 账号查询用户信息
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //权限集合
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        //将来连接数据库根据账号查询用户信息
        UserDto userDto = userDao.getUserByUsername(username);
        if (userDto == null) {
            //如果用户查不到，返回null，由provider来抛出异常
            return null;
        }
        List<Role_PermissionDto> permissionsAndRoles = userDao.findPermissionsAndRoleByUserId(userDto.getId());
        permissionsAndRoles.forEach(p -> {
            String role = p.getRoleName();
            //添加角色信息
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role));
            String permission = p.getCode();
            //添加权限信息
            grantedAuthorities.add(new SimpleGrantedAuthority(permission));
        });
        UserDetails userDetails = User.withUsername(userDto.getUsername()).password(userDto.getPassword()).authorities(grantedAuthorities).build();
        return userDetails;
    }
}
