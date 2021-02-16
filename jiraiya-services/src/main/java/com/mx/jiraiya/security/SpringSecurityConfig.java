package com.mx.jiraiya.security;

import com.mx.jiraiya.filter.JWTAuthenticationFilter;
import com.mx.jiraiya.filter.JWTAuthorizationFilter;
import com.mx.jiraiya.filter.service.JWTService;
import com.mx.jiraiya.service.JpaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@EnableGlobalMethodSecurity(securedEnabled = true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    Logger logger = LogManager.getLogger(SpringSecurityConfig.class);

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JpaUserDetailsService userDetailsService;

    @Autowired
    private JWTService jwtService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        logger.info("De-->SpringSecurityConfig-->configure");
        http.authorizeRequests().antMatchers("/", "/resetPassword").permitAll().and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtService))
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtService)).csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }

    @Autowired
    public void configurerGlobal(AuthenticationManagerBuilder build) throws Exception {
        logger.info("De-->SpringSecurityConfig-->configurerGlobal");
        build.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }
}
