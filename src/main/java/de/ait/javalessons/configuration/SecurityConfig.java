package de.ait.javalessons.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .authenticationProvider(daoAuthenticationProvider(
                        .
                ))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/**").hasRole(("ADMIN"))
                        .requestMatchers("/user/**").hasRole("USER")
                        .requestMatchers("/", "/public/**", "h2-console/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .logout(logout -> logout.permitAll());

        http.headers(headers -> headers.frameOptions().disable());

        return http.build();
    }

    @Bean
    public static void initializeAuthenticationManagerBuilder(AuthenticationManagerBuilder authenticationManagerBuilder,
                                                              DataSource dataSource) throws Exception{
        authenticationManagerBuilder.jdbcAuthentication().dataSource(dataSource);

    }

    @Bean
    public JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        return  jdbcUserDetailsManager;
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider daoAuthenticationProvider = new
    }
}
