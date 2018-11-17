package com.lee.pojo;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class User implements UserDetails {
    @Id
    private Long id;

    @Column(name = "user_role")
    private Integer userRole;

    private String avatar;

    private String email;

    private String name;

    private String password;

    private String username;

    //关联查出的角色
    private String role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 返回<SimpleGrantedAuthority>
        List<SimpleGrantedAuthority> simpleAuthorities = new ArrayList<>();
        simpleAuthorities.add(new SimpleGrantedAuthority(role));
        System.out.println("取得此用户权限："+simpleAuthorities);
        return simpleAuthorities;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}