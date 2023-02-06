package com.msb.ini;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2023-02-02 09:17
 */
public class Demo1 {

    @Test
    public void authen() {
        // 1.构建InitRealm
        IniRealm realm = new IniRealm("classpath:shiro.ini");

        // 2.构建sm绑定realm
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        securityManager.setRealm(realm);

        // 3.基于 绑定sm并声明subject
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();

        // 4.认证操作
        subject.login(new UsernamePasswordToken("admin", "admin"));

        // 5.角色校验
        System.out.println(subject.hasRole("超级管理员"));
        subject.checkRole("运营");

        // 6.权限校验
        subject.checkPermission("user:delete");
    }
}
