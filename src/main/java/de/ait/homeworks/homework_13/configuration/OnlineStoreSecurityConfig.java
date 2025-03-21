package de.ait.homeworks.homework_13.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class OnlineStoreSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager(PasswordEncoder passwordEncoder) {
        UserDetails customer = User.withUsername("customer")
                .password(passwordEncoder.encode("customerpass"))
                .roles("CUSTOMER")
                .build();

        UserDetails manager = User.withUsername("manager")
                .password(passwordEncoder.encode("managerpass"))
                .roles("MANAGER")
                .build();

        return  new InMemoryUserDetailsManager(customer, manager);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(("/products/public/**")).permitAll()
                        .requestMatchers("/products/customer/**").hasRole("CUSTOMER")
                        .requestMatchers("/products/manager/**").hasRole("MANAGER")
                        .anyRequest().authenticated()
                )
                .formLogin(withDefaults());
        return  http.build();
    }
}
