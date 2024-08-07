package com.study.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.common.filter.CsrfCookieFilter;
import com.study.common.filter.JwtAuthenticationFilter;
import com.study.common.handler.LoginFailureHandler;
import com.study.common.handler.LoginSuccessHandler;
import com.study.module.auth.application.service.JwtTokenProvider;
import com.study.module.user.application.service.CustomUserDetailsService;
import com.study.module.user.application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig {
    private final CustomUserDetailsService userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        CsrfTokenRequestAttributeHandler requestAttributeHandler = new CsrfTokenRequestAttributeHandler();
        requestAttributeHandler.setCsrfRequestAttributeName("_csrf");
        http
                .csrf(csrf -> csrf
                        .csrfTokenRequestHandler(requestAttributeHandler)
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .ignoringRequestMatchers("/api/test","/api/signIn","/login", "/logout", "/signUp", "/api/signUp", "/api/signUp/mail-send", "/resources/**", "/static/**", "/css/**", "/js/**"))
                .authorizeHttpRequests(request -> request
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/api/test", "/api/signIn","/signUp", "/find/id", "/find/password", "/api/signUp", "/api/signUp/mail-send", "/api/v1/qr").permitAll()
                        .requestMatchers("/members/test").hasRole("USER")
                        .anyRequest().authenticated())
//                .formLogin(login -> login
//                        .loginPage("/login")
//                        .loginProcessingUrl("/login")
//                        .usernameParameter("userId")
//                        .passwordParameter("password")
//                        .defaultSuccessUrl("/index")
//                        .successHandler(loginSuccessHandler())
//                        .failureHandler(loginFailureHandler())
//                        .permitAll())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .rememberMe(remember -> remember
//                        .rememberMeParameter("remember-me")
//                        .tokenValiditySeconds(3600)
//                        .alwaysRemember(false)
//                        .userDetailsService(userDetailsService)
//                        .authenticationSuccessHandler(loginSuccessHandler()))
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.sendRedirect("/login");
                        })
                        .deleteCookies("remember-me")
                        .permitAll())
                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
//                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public LoginSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler(userDetailsService);
    }

    @Bean
    public LoginFailureHandler loginFailureHandler() {
        return new LoginFailureHandler();
    }

}
