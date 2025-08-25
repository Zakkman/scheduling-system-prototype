package application.security;

import application.views.anonymousviews.LoginView;
import com.vaadin.flow.spring.security.VaadinAwareSecurityContextHolderStrategyConfiguration;
import com.vaadin.flow.spring.security.VaadinSecurityConfigurer;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@Import(VaadinAwareSecurityContextHolderStrategyConfiguration.class)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
            .requestMatchers("/", "/login", "/logout", "/public/**").permitAll()
            .requestMatchers("/student").hasRole("STUDENT")
            .requestMatchers("/teacher").hasRole("TEACHER")
        );

        http.with(VaadinSecurityConfigurer.vaadin(), configurer -> {
            configurer.loginView(LoginView.class);
        });

        http.formLogin(login -> login
            .successHandler(customAuthenticationSuccessHandler())
        );

        http.logout(logout -> logout
            .logoutSuccessUrl("/")
            .invalidateHttpSession(true)
            .clearAuthentication(true)
        );

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return (request, response, authentication) -> {
            if (authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals("ROLE_TEACHER"))) {
                response.sendRedirect("/teacher");
            } else if (authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals("ROLE_STUDENT"))) {
                response.sendRedirect("/student");
            } else {
                response.sendRedirect("/");
            }
        };
    }

    //Development Demo
    @Bean
    public UserDetailsManager userDetailsManager() {
        LoggerFactory.getLogger(SecurityConfig.class)
            .warn("Using in-memory user details manager!");
        var student = User.withUsername("student")
            .password("{noop}student")
            .roles("STUDENT")
            .build();
        var teacher = User.withUsername("teacher")
            .password("{noop}teacher")
            .roles("TEACHER")
            .build();
        return new InMemoryUserDetailsManager(student, teacher);
    }
    //Development Demo

}
