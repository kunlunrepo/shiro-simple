package com.msb.jdbc;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2023-02-02 10:11
 */
public class Demo1 {

    @Test
    public void authen() {
        // 1.构建JdbcRealm
        JdbcRealm realm = new JdbcRealm();
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://192.168.10.140:3306/shiro");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        realm.setDataSource(dataSource);

        realm.setPermissionsLookupEnabled(true);

        // 2.构建sm绑定realm
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        securityManager.setRealm(realm);

        // 3.基于 绑定sm并声明subject
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();

        // 4.认证操作
        subject.login(new UsernamePasswordToken("admin", "admin"));

        // 5.授权操作(角色)
        System.out.println(subject.hasRole("超级管理员1"));

        // 6.授权操作(权限)
        System.out.println(subject.isPermitted("user:add")); // 中文有问题


//        // 5.角色校验
//        System.out.println(subject.hasRole("超级管理员"));
//        subject.checkRole("运营");
//
//        // 6.权限校验
//        subject.checkPermission("user:delete");
    }
}
