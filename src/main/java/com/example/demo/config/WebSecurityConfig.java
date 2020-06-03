package com.example.demo.config;

import com.example.demo.filter.ImageCodeFilter;
import com.example.demo.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private CustomUserDetailsService userDetailsService;

    @Resource
    private ImageCodeFilter imageCodeFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().loginPage("/login/form").loginProcessingUrl("/login/image")
                .and().authorizeRequests()
                .antMatchers("/hello").hasAuthority("ROLE_USER")
                .antMatchers("/**").permitAll()
                .and().addFilterBefore(imageCodeFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(initPasswordEncoder());
    }

    @Bean
    public PasswordEncoder initPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
