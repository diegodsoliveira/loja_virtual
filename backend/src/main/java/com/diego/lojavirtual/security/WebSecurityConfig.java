package com.diego.lojavirtual.security;

import com.diego.lojavirtual.service.ImplementacaoUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.http.HttpSessionListener;

public class WebSecurityConfig extends WebSecurityConfigurerAdapter implements HttpSessionListener {

    @Autowired
    private ImplementacaoUserDetailService userDetailsService;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }
}
