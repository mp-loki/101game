package com.valeriisosliuk.game.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import com.valeriisosliuk.game.security.CustomSecurityContextLogoutHandler;

@Configuration
@EnableWebMvcSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    private CustomSecurityContextLogoutHandler customSecurityContextLogoutHandler;
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/css/*", "/js/*").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
            .logout()
                .addLogoutHandler(customSecurityContextLogoutHandler)
                .permitAll();
        http.sessionManagement().maximumSessions(1).sessionRegistry(sessionRegistry());
    }
    
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher());
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("Kyle").password("Kyle").roles("User");
        auth.inMemoryAuthentication().withUser("Stan").password("Stan").roles("User");
        auth.inMemoryAuthentication().withUser("Cartman").password("Cartman").roles("User");
        auth.inMemoryAuthentication().withUser("Kenny").password("Kenny").roles("User");
        auth.inMemoryAuthentication().withUser("Wendy").password("Wendy").roles("User");
        auth.inMemoryAuthentication().withUser("Butters").password("Butters").roles("User");
    }
}
