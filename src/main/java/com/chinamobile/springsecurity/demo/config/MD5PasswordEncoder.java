package com.chinamobile.springsecurity.demo.config;

import com.chinamobile.springsecurity.demo.utils.Md5Util;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author ZQ
 * @create 2020-07-16
 * @description 自定义MD5PasswordEncoder实现MD5密码加密验证
 */

@Component
public class MD5PasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        String encode = Md5Util.stringToMd5(rawPassword.toString());
        return encode;
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String encode = Md5Util.stringToMd5(rawPassword.toString());
        return encode.equals(encodedPassword);
    }
}
