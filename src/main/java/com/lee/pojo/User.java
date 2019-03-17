package com.lee.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class User implements UserDetails {
    private static final long serialVersionUID = 8816925479008869807L;

    @Id
    private Long id;

    @Column(name = "user_role")
    private Integer userRole;

    private String avatar;

    private String email;

    @JsonIgnore   //不传输密码
    private String password;

    private String username;

    @Column(name = "bg_img")
    private String bgImg;

    //关联查出的角色
    @Transient
    private String role;
    @Transient
    private Integer fansCounts;  //粉丝数量
    @Transient
    private Integer followCounts; //关注的用户数量


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