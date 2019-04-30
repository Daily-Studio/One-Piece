package org.dailystudio.onepiece.config;

import lombok.RequiredArgsConstructor;
import org.dailystudio.onepiece.security.ajax.filter.AjaxLoginFilter;
import org.dailystudio.onepiece.security.ajax.handler.AjaxLoginFailureHandler;
import org.dailystudio.onepiece.security.ajax.handler.AjaxLoginSuccessHandler;
import org.dailystudio.onepiece.security.ajax.provider.AjaxLoginAuthenticationProvider;
import org.dailystudio.onepiece.security.jwt.filter.JwtAuthenticationFilter;
import org.dailystudio.onepiece.security.jwt.handler.JwtFailureHandler;
import org.dailystudio.onepiece.security.jwt.provider.JwtAuthenticationProvider;
import org.dailystudio.onepiece.security.matcher.FilterSkipMatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String SIGNUP_END_POINT = "/api/account/signup";
    private static final String LOGIN_END_POINT = "/api/account/login";
    private static final String NEED_JWT_POINT = "/api/**";

    private final AjaxLoginAuthenticationProvider ajaxLoginProvider;
    private final JwtFailureHandler jwtFailureHandler;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final AjaxLoginSuccessHandler ajaxLoginSuccessHandler;
    private final AjaxLoginFailureHandler ajaxLoginFailureHandler;


    @Bean
    public AuthenticationManager getAuthenticationManager() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth
                .authenticationProvider(this.ajaxLoginProvider)
                .authenticationProvider(this.jwtAuthenticationProvider);
    }

    protected AjaxLoginFilter ajaxLoginFilter() throws Exception {
        AjaxLoginFilter filter = new AjaxLoginFilter(LOGIN_END_POINT, ajaxLoginSuccessHandler, ajaxLoginFailureHandler);
        filter.setAuthenticationManager(getAuthenticationManager());
        filter.setAuthenticationSuccessHandler(ajaxLoginSuccessHandler);
        filter.setAuthenticationFailureHandler(ajaxLoginFailureHandler);
        return filter;
    }

    protected JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        FilterSkipMatcher skipMatcher = getSkipMatcher();
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(skipMatcher, jwtFailureHandler);
        filter.setAuthenticationManager(getAuthenticationManager());
        filter.setAuthenticationFailureHandler(jwtFailureHandler);
        return filter;
    }

    private FilterSkipMatcher getSkipMatcher() {
        return new FilterSkipMatcher(Arrays.asList(LOGIN_END_POINT, SIGNUP_END_POINT), NEED_JWT_POINT);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(ajaxLoginFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http
                .csrf()
                .disable();
        http
                .headers()
                .frameOptions()
                .disable();
    }
}
