package com.hotel.config;



import com.hotel.services.jwt.JwtRequestFilter;
import com.hotel.until.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    @Autowired
    private JwtRequestFilter requestFilter;

    @Autowired
    private JwtUtil jwtUtil;
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//         return http.csrf().disable()
//                .authorizeHttpRequests()
//                 .requestMatchers("/authenticate", "/company/sign-up", "/client/sign-up", "/ads", "/search/{service}" ).permitAll()
//                 .and()
//                 .authorizeHttpRequests().requestMatchers("/api/**")
//                 .authenticated().and()
//                 .sessionManagement()
//                 .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                 .and()
//                 .addFilterBefore(requestFilter, UsernamePasswordAuthenticationFilter.class)
//                 .build();
//
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .authorizeRequests()
                .requestMatchers("/authenticate", "/company/sign-up", "/client/sign-up", "/ads", "/search/{service}").permitAll()
                .requestMatchers("/api/**").permitAll() // Izinkan akses ke semua endpoint di bawah /api tanpa otentikasi

                .requestMatchers(HttpMethod.POST, "/api/company/ad/**").permitAll() // Izinkan akses POST tanpa otentikasi

                .requestMatchers(HttpMethod.GET, "/api/company/ads/**").permitAll() // Izinkan akses GET tanpa otentikasi ke semua endpoint di /api/company/ads
                .requestMatchers(HttpMethod.PUT, "/api/company/ad/**").permitAll() // Izinkan akses PUT tanpa otentikasi
                .requestMatchers(HttpMethod.GET, "/api/company/ad/**").permitAll() // Izinkan akses GET tanpa otentikasi ke semua endpoint di /api/company/ads
                .requestMatchers(HttpMethod.DELETE, "/api/company/ad/**").permitAll() // Izinkan akses GET tanpa otentikasi ke semua endpoint di /api/company/ads

                .requestMatchers(HttpMethod.GET, "/api/client/ads/**").permitAll() // Izinkan akses GET tanpa otentikasi ke semua endpoint di /api/client/ads
                .requestMatchers(HttpMethod.PUT, "/api/client/ad/**").permitAll() // Izinkan akses PUT tanpa otentikasi
                .requestMatchers(HttpMethod.GET, "/api/client/ad/**").permitAll() // Izinkan akses GET tanpa otentikasi ke semua endpoint di /api/client/ads
                .requestMatchers(HttpMethod.DELETE, "/api/client/ad/**").permitAll()

                .requestMatchers(HttpMethod.GET, "/api/client/my-bookings/{userId}").permitAll() // Izinkan akses GET tanpa otentikasi ke semua endpoint di /api/client/ads

                .requestMatchers(HttpMethod.PUT, "/api/company/booking/{bookingId}/{status}").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/company/booking/{companyId}").permitAll()
//                .requestMatchers("/api/**").authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(requestFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


    @Bean
    public AuthenticationManager authenticationManager (AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
