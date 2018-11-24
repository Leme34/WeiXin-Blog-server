package com.lee.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;
import java.nio.file.AccessDeniedException;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //启用@prePostEnabled注解
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String KEY = "com.lee.blog";
    //spring security提供的UserDetailsService
    //在UserServiceImpl中实现他(用户信息获取服务)的接口loadUserByUsername
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private BlogAuthenticationSuccessHandler blogAuthenticationSuccessHandler;
    @Autowired
    private BlogAuthenticationFailureHandler blogAuthenticationFailureHandler;
    @Autowired
    private BlogAccessDeniedHandler blogAccessDeniedHandler;
    @Autowired
    private BlogLogoutSuccessHandler blogLogoutSuccessHandler;
    @Autowired
    private BlogInvalidSessionStrategy blogInvalidSessionStrategy;
    @Autowired
    private BlogExpiredSessionStrategy blogExpiredSessionStrategy;
    @Autowired
    private DataSource dataSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 使用提供的 BCrypt 加密
        return new BCryptPasswordEncoder();
    }
    /**
     * 记住我功能的token存取器配置,
     * 会把从浏览器cookie得到的username和token存入数据库
     * 若不配置自己实现的persistentTokenRepository则只是将username,token存到浏览器cookie中
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        // 系统在启动时自动生成表,生成一次后一定要注释掉
        // tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }


    //定义授权规则(权限)
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); //禁用CSRF
        http
                .formLogin()   //基于 Form 表单登录验证
//                .defaultSuccessUrl("",true)  //认证成功后总是跳转此url
//                .loginPage("/static/login-test.html")  //自定义登录页面
                .loginProcessingUrl("/doLogin")  //自定义登录页面中提交表单的action (form内容会给UsernamePasswordAuthenticationFilter认证)
                .successHandler(blogAuthenticationSuccessHandler)  //认证成功后使用注入的自定义SavedRequestAwareAuthenticationSuccessHandler的子类处理
//                .failureUrl("/login-error") // 认证失败跳转的页面,会交给failureHandler处理
                .failureHandler(blogAuthenticationFailureHandler)  //认证失败后使用注入的自定义SimpleUrlAuthenticationFailureHandler的子类处理

                .and()

                .logout()
                .logoutUrl("/signOut")  //登出表单提交的action
                .logoutSuccessHandler(blogLogoutSuccessHandler)  //登出成功后的处理
//                .logoutSuccessUrl("/index")  //logoutSuccessHandler处理完后跳转的url
                .deleteCookies("JSESSIONID")  //删除用户cookie

                .and()

                //自定义记住我功能:根据从浏览器cookie得到的token查询存在数据库的用户信息
                .rememberMe().key(KEY) // 启用 remember me
//                .tokenRepository(persistentTokenRepository())
//                .tokenValiditySeconds(3600*7)  //记住我有效时间为1周
//                .userDetailsService(userDetailsService)  //查询用户信息的userDetailsService

                .and()

                .exceptionHandling()
                .accessDeniedHandler(blogAccessDeniedHandler) // 由自定义AccessDeniedHandler处理权限被拒绝异常
//                .accessDeniedPage("/accessDenied")  // blogAccessDeniedHandler处理后重定向到 403 页面

                .and()

                .sessionManagement()
                .invalidSessionStrategy(blogInvalidSessionStrategy) //当session失效时的操作
//                .invalidSessionUrl("/session/invalid")  //invalidSessionStrategy处理完后跳转的url
                .maximumSessions(1)   //同一用户session的最大数量
                .expiredSessionStrategy(blogExpiredSessionStrategy)   //场景1：(允许把已登录用户踢下线)同一用户旧的session替换为新的session时的操作
//                .expiredUrl("/session/invalid") //expiredSessionStrategy处理完后跳转的url
//                .maxSessionsPreventsLogin(true)  //场景2：当同一用户session数量达到最大时阻止用户登录(同一用户只能在1处登录)

                .and()
                .and()

//                .addFilterBefore(xxxFiler, UsernamePasswordAuthenticationFilter.class)   //把此过滤器加到UsernamePasswordAuthenticationFilter之前
                .authorizeRequests()
                .antMatchers("/doLogin","/blog/list", "/session/invalid","/register").permitAll() // 都可以访问
                .antMatchers("/users/**").hasAnyRole("USER","ADMIN");  //需要角色才能访问,框架会转换成ROLE_USER和ROLE_ADMIN进行验证
//                .antMatchers(HttpMethod.POST,"/users/**").hasAnyRole("USER","ADMIN")  //POST请求需要角色才能访问
//                .anyRequest().authenticated()  //剩下没有配置的url都需要登录认证才能访问

    }

    //用户认证
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //设置认证提供者
        auth.authenticationProvider(authenticationProvider());
        //自定义的UserDetailsService提供自定义用户信息获取服务
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    /**
     * AuthenticationProvider 提供用户UserDetails的具体验证方式，
     * 在其中可以自定义用户密码的加密、验证方式
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        //通过dao层提供验证
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        // 设置密码加密方式,用于登录时的密码比对
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }

}
