package com.g.config;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.Assert;

import com.g.sys.sec.web.JwtAuthenticationEntryPoint;
import com.g.sys.sec.web.JwtAuthenticationFilter;
import com.g.sys.sec.web.JwtAuthenticationProvider;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//    @Configuration
//    @Order(1)
//    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
//        protected void configure(HttpSecurity http) throws Exception {
//            http
//                    .antMatcher("/api/**")
//                    .authorizeRequests(authorize -> authorize
//                            .anyRequest().hasRole("ADMIN")
//                    )
//                    .httpBasic(withDefaults());
//        }
//    }

    @Configuration
    @Order(2)
    public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
        private final UserDetailsService userDetailsService;

        @Autowired
        public FormLoginWebSecurityConfigurerAdapter(@Qualifier("gUserDetailsService") UserDetailsService userDetailsService) {
            this.userDetailsService = userDetailsService;
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService);
        }

        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers("/**/*.html", "/css/**", "/img/**", "/js/**", "/vendors/**");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests(authorize -> authorize
                            .antMatchers("/form-login", "/reset-password").permitAll()
                            .anyRequest().authenticated()
                    )
                    .formLogin(form -> form
                            .loginPage("/form-login")
                            .failureForwardUrl("/form-login?error")
                            .defaultSuccessUrl("/blank")
                            .permitAll()
                    )
                    .logout(form -> form
                            .logoutUrl("/logout")
                            .logoutSuccessUrl("/form-login?logout")
                            .invalidateHttpSession(true)
                            .deleteCookies("JSESSIONID")
                    )
                    .sessionManagement(form -> form
                            .invalidSessionUrl("/form-login?invalidSession"));
        }
    }


//    private final UserDetailsService userDetailsService;
//
//    private final JwtAuthenticationProvider jwtAuthenticationProvider;
//
//    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
//
//    private JwtAuthenticationFilter jwtAuthenticationFilter;
//
//    @Autowired
//    public WebSecurityConfig(@Qualifier("gUserDetailsService") UserDetailsService userDetailsService,
//                             JwtAuthenticationProvider jwtAuthenticationProvider,
//                             JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
//        Assert.notNull(userDetailsService, "A UserDetailsService must be set");
//        Assert.notNull(jwtAuthenticationProvider, "A JwtAuthenticationProvider must be set");
//        Assert.notNull(jwtAuthenticationEntryPoint, "A JwtAuthenticationEntryPoint must be set");
//
//        this.userDetailsService = userDetailsService;
//        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
//        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService);
//        auth.authenticationProvider(jwtAuthenticationProvider);
//    }
//
//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.addFilterBefore(getJwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
//                .csrf((csrf) -> csrf.disable())
//                .authorizeRequests((authorizeRequests) ->
//                        authorizeRequests.antMatchers("/**").permitAll())
////                        authorizeRequests.antMatchers("/login", "/sys/users/reset-password", "/test", "/actuator/**").permitAll()
////                                .anyRequest().access("isAuthenticated() and @webSecurity.check(authentication,request)"))
//                .exceptionHandling((exceptionHandling) ->
//                        exceptionHandling.authenticationEntryPoint(jwtAuthenticationEntryPoint))
//                .sessionManagement((sessionManagement) ->
//                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//    }
//
//    private Filter getJwtAuthenticationFilter() throws Exception {
//        if (jwtAuthenticationFilter == null) {
//            jwtAuthenticationFilter = new JwtAuthenticationFilter(super.authenticationManager(), jwtAuthenticationEntryPoint);
//        }
//        return jwtAuthenticationFilter;
//    }
}