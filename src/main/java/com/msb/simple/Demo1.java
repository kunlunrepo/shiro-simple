package com.msb.simple;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2023-02-01 17:30
 */
public class Demo1 {

    @Test
    public void authen() {

        // 认证发起者 --> sm --> rm

        // 1.准备Realm
        SimpleAccountRealm realm = new SimpleAccountRealm();
        realm.addAccount("admin", "admin", "超级管理员", "商家");

        // 2.准备sm
        DefaultSecurityManager securityManager = new DefaultSecurityManager();

        // 3.sm和rm 建立连接
        securityManager.setRealm(realm);

        // 4.subject和sm建立联系
        SecurityUtils.setSecurityManager(securityManager);

        // 5.声明subject
        Subject subject = SecurityUtils.getSubject();

        // 6.发起认证
        subject.login(new UsernamePasswordToken("admin", "admin"));

        // 7.判断是否认证成功
        System.out.println(subject.isAuthenticated());

        // 8.退出登录后再判断
//        subject.logout();
//        System.out.println(subject.isAuthenticated());

        // 9.授权是在认证成功之后的操作!!
        // SimpleAccountRealm 只支持角色的授权
        System.out.println(subject.hasRole("超级管理员"));
        subject.checkRole("商家"); // 如果没有角色时会抛出异常


    }
}
