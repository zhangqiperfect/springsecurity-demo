package com.chinamobile.springsecurity.demo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ZQ
 * @create 2020-07-15
 * @description
 */
//访问权限控制
@Configuration
//开启基于注解的安全配置：
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
/*
 代码解释：
prePostEnabled=true 会解锁＠PreAuthorize @PostAuthorize
@PreAuthorize 会在方法执行前进行验证，而＠PostAuthorize方法执行后进行验证
securedEnabled＝true 会解锁＠Secured 注解
*/
public class MyWebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    PasswordEncoder passwordEncoder;

    /* //Bcrypt加密
     @Bean
     PasswordEncoder passwordEncoder() {
         return new BCryptPasswordEncoder();
     }*/
    //认证管理
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //基于数据库认证
        auth.userDetailsService(userDetailsService);
    }

    //访问控制
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
//                .antMatchers("/admin/*")
//                .hasRole("ADMIN")
//                .antMatchers("/user/**")
//                .access("hasAnyRole('ADMIN','USER')")
//                .antMatchers("/db/**")
//                .access("hasRole('ADMIN')and hasRole('DBA')")
                //其它请求必须认证
                .anyRequest()
                .authenticated()
                .and()
                //开启表单登录
                .formLogin()
//                .loginPage("/login_page")
                //登录请求处理接口
                .loginProcessingUrl("/login")
//                认证所需的用户名和密码的参数名,默认用户名参数是 usemame ，密码参数是 password
                .usernameParameter("username")
                .passwordParameter("password")
                //登录成功的处理逻辑
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {
                        PrintWriter out = resp.getWriter();
                        Object principal = authentication.getPrincipal();
                        resp.setContentType("application/json;charset=utf-8");
                        resp.setStatus(200);
                        Map<String, Object> map = new HashMap<>();
                        map.put("status", 200);
                        map.put("msg", principal);
                        ObjectMapper objectMapper = new ObjectMapper();
                        out.write(objectMapper.writeValueAsString(map));
                        out.flush();
                        out.close();
                    }
                })
                //验证失败
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse resp, AuthenticationException e) throws IOException, ServletException {
                        PrintWriter out = resp.getWriter();
                        resp.setContentType("application/json;charset=utf-8");
                        resp.setStatus(40);
                        Map<String, Object> map = new HashMap<>();
                        map.put("status", 401);
                        if (e instanceof LockedException) {
                            map.put("msg", "账户被锁定");
                        } else if (e instanceof BadCredentialsException) {
                            map.put("msg", "账户名或密码输入错误");
                        } else if (e instanceof DisabledException) {
                            map.put("msg", "用户被禁用");
                        } else if (e instanceof AccountExpiredException) {
                            map.put("msg", "用户已过期 登录失败");
                        } else if (e instanceof CredentialsExpiredException) {
                            map.put("msg", "密码已过期，登录失败");
                        } else {
                            map.put("msg", "登录失败");
                        }
                        ObjectMapper objectMapper = new ObjectMapper();
                        out.write(objectMapper.writeValueAsString(map));
                        out.flush();
                        out.close();
                    }
                })
                .and()
                .logout()
                .logoutUrl("/logout")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                //登出处理器
                .addLogoutHandler(new LogoutHandler() {
                                      @Override
                                      public void logout(HttpServletRequest httpServletRequest, HttpServletResponse resp, Authentication authentication) {
                                          //可以完成一些数据清除工作，入cookie的清除
                                      }
                                  }
                )
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    @Override
                    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {
                        try {
                            resp.sendRedirect("/login_page");
                        } catch (IOException e) {
                        }
                    }
                })
                //和登录先关的几口不需要认证
                .permitAll()
                .and()
                .csrf()
                .disable();


    }
}
