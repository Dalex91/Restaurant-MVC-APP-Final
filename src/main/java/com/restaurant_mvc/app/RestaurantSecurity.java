package com.restaurant_mvc.app;

import com.restaurant_mvc.app.utils.Constants;
import com.restaurant_mvc.app.utils.EndPoints;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class RestaurantSecurity {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder ();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        UserDetails user = User.builder()
                .username(Constants.USERNAME_SECURITY_CONF)
                .password(encoder.encode(Constants.PASSWORD_SECURITY_CONF))
                .roles(Constants.ROLES_SECURITY_CONF)
                .build();

        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.authorizeHttpRequests ((requestsList) ->
                requestsList.requestMatchers (
                        EndPoints.GENERIC,
                        EndPoints.ADMIN_REQUEST,
                        EndPoints.EMPLOYEE_REQUEST
                ).permitAll ()
        ).formLogin ((form) ->
                form.loginPage (EndPoints.LOGIN_ENDPOINT).permitAll ()
        ).logout (
                LogoutConfigurer::permitAll
        ).build ();
    }
}
