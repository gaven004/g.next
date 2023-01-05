package com.g.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@EnableWebSecurity
public class WebSecurityConfig {

// 停用API的Security设置，方便开发

/*
    @Configuration
    @Order(1)
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        private final UserDetailsService userDetailsService;
        private final JwtAuthenticationProvider jwtAuthenticationProvider;
        private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
        private JwtAuthenticationFilter jwtAuthenticationFilter;

        public ApiWebSecurityConfigurationAdapter(@Qualifier("gUserDetailsService") UserDetailsService userDetailsService,
                                                  JwtAuthenticationProvider jwtAuthenticationProvider,
                                                  JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
            Assert.notNull(userDetailsService, "A UserDetailsService must be set");
            Assert.notNull(jwtAuthenticationProvider, "A JwtAuthenticationProvider must be set");
            Assert.notNull(jwtAuthenticationEntryPoint, "A JwtAuthenticationEntryPoint must be set");

            this.userDetailsService = userDetailsService;
            this.jwtAuthenticationProvider = jwtAuthenticationProvider;
            this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        }

        @Bean(name = "restAuthenticationManager")
        public AuthenticationManager restAuthenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }

        @Bean(name = "jwtAuthenticationManager")
        public AuthenticationManager jwtAuthenticationManagerBean() throws Exception {
            ObjectPostProcessor<Object> objectPostProcessor = getApplicationContext().getBean(ObjectPostProcessor.class);
            AuthenticationManagerBuilder authenticationManagerBuilder = new AuthenticationManagerBuilder(objectPostProcessor);
            authenticationManagerBuilder.authenticationProvider(jwtAuthenticationProvider);
            return authenticationManagerBuilder.build();
        }

        protected void configure(HttpSecurity http) throws Exception {
            http.userDetailsService(userDetailsService)
                    .addFilterBefore(getJwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                    .csrf((csrf) -> csrf.disable())
                    .antMatcher("/api/**")
                    .authorizeRequests((authorizeRequests) ->
                            authorizeRequests.antMatchers("/api/login", "/api/test").permitAll()
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
*/

    @Configuration
    public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
        private final UserDetailsService userDetailsService;

        public FormLoginWebSecurityConfigurerAdapter(@Qualifier("gUserDetailsService") UserDetailsService userDetailsService) {
            this.userDetailsService = userDetailsService;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.userDetailsService(userDetailsService)
                    .authorizeRequests(authorize ->
                            authorize.antMatchers("/**/*.html", "/css/**", "/img/**", "/js/**", "/vendors/**",
                                            "/form-login", "/error", "/sys/users/reset-pwd", "/sys/users/reset-password").permitAll()
                                    .anyRequest().access("isAuthenticated() and @webSecurity.check(authentication,request)")
                    )
                    .formLogin(form -> form.loginPage("/form-login")
                            .failureForwardUrl("/form-login?error")
                            .defaultSuccessUrl("/blank")
                            .permitAll())
                    .logout(logout -> logout.logoutUrl("/logout")
                            .logoutSuccessUrl("/form-login?logout")
                            .invalidateHttpSession(true)
                            .deleteCookies("JSESSIONID"))
                    .sessionManagement(sm -> sm.invalidSessionUrl("/form-login?invalidSession"));
        }
    }
}
