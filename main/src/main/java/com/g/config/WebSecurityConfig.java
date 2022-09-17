package com.g.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    @Order(10)
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        http.antMatcher("/api/**")
                .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll())
                .csrf(csrf -> csrf.disable());
        return http.build();
    }

    @Bean
    public SecurityFilterChain formLoginFilterChain(HttpSecurity http,
                                                    @Qualifier("gUserDetailsService") UserDetailsService userDetailsService) throws Exception {
        http.userDetailsService(userDetailsService)
                .authorizeHttpRequests(authorize ->
                        authorize.antMatchers("/**/*.html", "/css/**", "/img/**", "/js/**", "/vendors/**")
                                .permitAll())
                .formLogin(form -> form.loginPage("/form-login")
                        .failureForwardUrl("/form-login?error")
                        .defaultSuccessUrl("/blank")
                        .permitAll())
                .logout(form -> form.logoutUrl("/logout")
                        .logoutSuccessUrl("/form-login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID"))
                .sessionManagement(form -> form.invalidSessionUrl("/form-login?invalidSession"));
        return http.build();
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
