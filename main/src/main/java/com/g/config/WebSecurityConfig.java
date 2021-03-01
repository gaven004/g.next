package com.g.config;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.Assert;

import com.g.sys.sec.web.JwtAuthenticationEntryPoint;
import com.g.sys.sec.web.JwtAuthenticationFilter;
import com.g.sys.sec.web.JwtAuthenticationProvider;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Configuration
    @Order(10)
    public static class RestConfigurationAdapter extends WebSecurityConfigurerAdapter {
        private final UserDetailsService userDetailsService;

        @Autowired
        public RestConfigurationAdapter(@Qualifier("gUserDetailsService") UserDetailsService userDetailsService) {
            Assert.notNull(userDetailsService, "A UserDetailsService must be set");
            this.userDetailsService = userDetailsService;
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService);
        }

        @Bean(name = "restAuthenticationManager")
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }

        @Override
        protected AuthenticationManager authenticationManager() throws Exception {
            return super.authenticationManagerBean();
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable()
                    .authorizeRequests().antMatchers("/login", "/test").permitAll()
                    .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }
    }

    @Configuration
    @Order(20)
    public static class JwtConfigurationAdapter extends WebSecurityConfigurerAdapter {
        private final UserDetailsService userDetailsService;

        private final JwtAuthenticationProvider jwtAuthenticationProvider;

        private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

        private JwtAuthenticationFilter jwtAuthenticationFilter;

        @Autowired
        public JwtConfigurationAdapter(@Qualifier("gUserDetailsService") UserDetailsService userDetailsService,
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
            auth.authenticationProvider(jwtAuthenticationProvider);
        }

        @Bean(name = "jwtAuthenticationManager")
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }

        @Override
        protected AuthenticationManager authenticationManager() throws Exception {
            return super.authenticationManagerBean();
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable()
                    .addFilterBefore(getJwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                    .authorizeRequests().anyRequest().authenticated()
                    .and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                    .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }

        private Filter getJwtAuthenticationFilter() throws Exception {
            if (jwtAuthenticationFilter == null) {
                jwtAuthenticationFilter = new JwtAuthenticationFilter(super.authenticationManager(), jwtAuthenticationEntryPoint);
            }
            return jwtAuthenticationFilter;
        }
    }
}