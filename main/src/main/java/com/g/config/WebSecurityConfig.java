package com.g.config;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.Assert;

import com.g.sys.sec.web.JwtAuthenticationEntryPoint;
import com.g.sys.sec.web.JwtAuthenticationFilter;
import com.g.sys.sec.web.JwtAuthenticationProvider;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;

    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    public WebSecurityConfig(@Qualifier("gUserDetailsService") UserDetailsService userDetailsService,
                             JwtAuthenticationProvider jwtAuthenticationProvider,
                             JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
        Assert.notNull(userDetailsService, "A UserDetailsService must be set");
        Assert.notNull(jwtAuthenticationProvider, "A JwtAuthenticationProvider must be set");
        Assert.notNull(jwtAuthenticationEntryPoint, "A JwtAuthenticationEntryPoint must be set");

        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
        auth.authenticationProvider(jwtAuthenticationProvider);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(getJwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .csrf((csrf) -> csrf.disable())
                .authorizeRequests((authorizeRequests) ->
                        authorizeRequests.antMatchers("/login", "/test").permitAll()
                                .anyRequest().access("isAuthenticated() and @webSecurity.check(authentication,request)"))
                .exceptionHandling((exceptionHandling) ->
                        exceptionHandling.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    }

    private Filter getJwtAuthenticationFilter() throws Exception {
        if (jwtAuthenticationFilter == null) {
            jwtAuthenticationFilter = new JwtAuthenticationFilter(super.authenticationManager(), jwtAuthenticationEntryPoint);
        }
        return jwtAuthenticationFilter;
    }
}