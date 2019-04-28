package org.dailystudio.onepiece.config;

import lombok.RequiredArgsConstructor;
import org.dailystudio.onepiece.security.filter.AjaxLoginFilter;
import org.dailystudio.onepiece.security.handler.AjaxLoginFailureHandler;
import org.dailystudio.onepiece.security.handler.AjaxLoginSuccessHandler;
import org.dailystudio.onepiece.security.provider.AjaxLoginAuthenticationProvider;
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

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String LOGIN_END_POINT = "/api/account/login";

    private final AjaxLoginAuthenticationProvider formLoginProvider;
    private final AjaxLoginSuccessHandler ajaxLoginSuccessHandler;
    private final AjaxLoginFailureHandler ajaxLoginFailureHandler;


    @Bean
    public AuthenticationManager getAuthenticationManager() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth
                .authenticationProvider(this.formLoginProvider);
    }

    protected AjaxLoginFilter ajaxLoginFilter() throws Exception {
        AjaxLoginFilter filter = new AjaxLoginFilter(LOGIN_END_POINT, ajaxLoginSuccessHandler, ajaxLoginFailureHandler);
        filter.setAuthenticationManager(getAuthenticationManager());
        filter.setAuthenticationSuccessHandler(ajaxLoginSuccessHandler);
        filter.setAuthenticationFailureHandler(ajaxLoginFailureHandler);
        return filter;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(ajaxLoginFilter(), UsernamePasswordAuthenticationFilter.class);
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
