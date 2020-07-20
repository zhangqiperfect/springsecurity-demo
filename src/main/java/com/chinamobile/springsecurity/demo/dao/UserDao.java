package com.chinamobile.springsecurity.demo.dao;


import com.chinamobile.springsecurity.demo.model.Role_PermissionDto;
import com.chinamobile.springsecurity.demo.model.UserDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 **/
@Mapper
@Repository
public interface UserDao {

    //根据账号查询用户信息
    UserDto getUserByUsername(@Param("username") String username);
//    {
//        String sql = "select id,username,password,fullname,mobile from t_user where username = ?";
//        //连接数据库查询用户
//        List<UserDto> list = jdbcTemplate.query(sql, new Object[]{username}, new BeanPropertyRowMapper<>(UserDto.class));
//        if(list !=null && list.size()==1){
//            return list.get(0);
//        }
//        return null;
//    }

    //根据用户id查询用户权限
    List<Role_PermissionDto> findPermissionsAndRoleByUserId(@Param("userId") Integer userId);
//     {
//        String sql = "SELECT * FROM t_permission WHERE id IN(\n" +
//                "\n" +
//                "SELECT permission_id FROM t_role_permission WHERE role_id IN(\n" +
//                "  SELECT role_id FROM t_user_role WHERE user_id = ? \n" +
//                ")\n" +
//                ")\n";
//
//        List<PermissionDto> list = jdbcTemplate.query(sql, new Object[]{userId}, new BeanPropertyRowMapper<>(PermissionDto.class));
//        List<String> permissions = new ArrayList<>();
//        list.forEach(c -> permissions.add(c.getCode()));
//        return permissions;
//    }
}
