package com.sings.competition.config;

import com.sings.competition.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static com.sings.competition.domain.Role.ADMIN;
import static com.sings.competition.domain.Role.USER;


@Configuration
@AllArgsConstructor
public class WebSecurityConfig {

    private final UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.
                csrf().disable()
                .authorizeHttpRequests((requests) -> requests
                        .antMatchers("/", "/main", "/catalog", "/product", "/product/*", "/registration").permitAll()
                        .antMatchers("/profile", "/profile/*", "/basket", "/basket/*").hasAnyAuthority(USER.name())
                        .antMatchers("/admin", "/admin/*").hasAuthority(ADMIN.name())
                        .anyRequest().authenticated())
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll())
                .logout(LogoutConfigurer::permitAll);
        return http.build();
    }
}
