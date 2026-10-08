package vn.iotstar.dormitory.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.ignoringRequestMatchers("/ws-dormitory/**", "/api/chat/**"))
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                .requestMatchers("/", "/home", "/tra-cuu-phong", "/tra-cuu-ho-so", "/tra-cuu/**", "/nop-don", "/nop-don/**").permitAll()
                .requestMatchers("/login", "/register", "/verify-otp", "/resend-otp", "/forgot-password", "/reset-password", "/error").permitAll()
                .requestMatchers("/ws-dormitory/**").permitAll()
                .requestMatchers("/api/chat/**").authenticated()
                .requestMatchers("/admin/**").hasAnyRole("QUAN_TRI", "ADMIN")
                .requestMatchers("/quansinh/**").hasRole("QUAN_SINH")
                .requestMatchers("/nhanvien/**").hasRole("NHAN_VIEN")
                .requestMatchers("/sinhvien/**").hasRole("SINH_VIEN")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );
        return http.build();
    }
}
