package application.backend.security;

import application.backend.users.repositories.UserRepo;
import application.ui.login.LoginView;
import com.vaadin.flow.spring.security.VaadinAwareSecurityContextHolderStrategyConfiguration;
import com.vaadin.flow.spring.security.VaadinSecurityConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    /*
    The issue is that even if you redirect a ROLE_STUDENT user to /teacher, the SecurityFilterChain
    will immediately block their access because they don't have the required ROLE_TEACHER role. The
    user will be denied access and likely redirected to an error page or the login page. The redirection
    from the success handler will not override the security rules.

    The customAuthenticationSuccessHandler is unnecessary for enforcing access. It's only needed
    for guiding the user to a specific page after a successful login. However, if your goal is to
    prevent a student from seeing a teacher's page, the SecurityFilterChain is what accomplishes this,
    not the redirection handler.

    Therefore, you can simplify your logic by having the customAuthenticationSuccessHandler
    redirect all users to a generic entry point, like /, and rely on the SecurityFilterChain to
    handle the specific page access permissions. This separates the concerns of what a user is
    allowed to do (access control) from where they are sent after logging in (redirection).
     */
    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return (request, response, authentication) -> {
            if (authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals("ROLE_TEACHER"))) {
                response.sendRedirect("/teacher");
            } else if (authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals("ROLE_STUDENT"))) {
                response.sendRedirect("/student");
            } else if (authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals("ROLE_ADMIN"))) {
                response.sendRedirect("/admin");
            } else {
                response.sendRedirect("/");
            }
        };
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepo userRepo) {
        return new UserDetailsServiceImp1(userRepo);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
