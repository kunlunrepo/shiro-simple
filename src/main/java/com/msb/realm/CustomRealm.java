package com.msb.realm;

import com.alibaba.druid.util.StringUtils;
import com.msb.custom.User;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashSet;
import java.util.Set;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2023-02-02 13:52
 */
public class CustomRealm extends AuthorizingRealm {

    {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("MD5");
        matcher.setHashIterations(1024);
        this.setCredentialsMatcher(matcher);
    }

    // 认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 1.获取用户名
        String username = (String) token.getPrincipal();

        // 2.判断用户名
        if (StringUtils.isEmpty(username)) {
            return null;
        }

        // 3.基于用户名查询用户信息
        User user = this.findUserByUsername(username);

        // 4.user对象是否为null
        if (null == user) {
            return null;
        }

        // 5.声明AuthenticationInfo ，并填充用户信息
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user,user.getPassword(),"CustomRealm");
        info.setCredentialsSalt(ByteSource.Util.bytes(user.getSalt()));

        // 6.
        return info;
    }

    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 1.获取认证用户信息
        User user = (User) principals.getPrimaryPrincipal();

        // 2.查询用户的角色
        Set<String> roleSet = this.findRoleByUser();

        // 3.查询角色的权限
        Set<String> permSet = this.findPermsByRoleSet(roleSet);

        // 4.声明AuthorizationInfo，传入角色和权限
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(roleSet);
        info.setStringPermissions(permSet);

        // 5.返回
        return info;
    }

    private Set<String> findRoleByUser() {
        Set<String> set = new HashSet<>();
        set.add("超级管理员");
        set.add("运营");
        return set;
    }

    private Set<String> findPermsByRoleSet(Set<String> roleSet) {
        Set<String> set = new HashSet<>();
        set.add("user:add");
        set.add("user:update");
        return set;
    }



    private User findUserByUsername(String username) {
        if ("admin".equals(username)) {
            User user = new User();
            user.setId(1);
            user.setUsername("admin");
            user.setPassword("67178a593adc05a7048eec9e5b017b34");
            user.setSalt("qwerty");
            return user;
        }
        return null;
    }
}
