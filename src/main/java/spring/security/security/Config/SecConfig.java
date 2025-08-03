package spring.security.security.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import spring.security.security.Filters.JwtAuthFilter;
import spring.security.security.Service.CustomUserDetailsService;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecConfig {

    @Autowired
    JwtAuthFilter jwtAuthFilter;

    //Here it uses basic authentication
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.csrf(AbstractHttpConfigurer::disable).
//                authorizeHttpRequests(auth -> auth.requestMatchers("/secure/auth","/jwt/authenticate").permitAll()
//                        .anyRequest().authenticated()).
//                httpBasic(withDefaults());//this guy is responsible for basic authentication filter
//        return http.build();
//    }

    // for jwt we remove basic authentication filter but  spring provide default username password authentication filter
    // before that username password auth filter we are going to implement jwt auth filter

    //Here i checked for authorisation using request matcher delegating Authorization manager ( hasRoel )
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable).
                authorizeHttpRequests(auth -> auth.requestMatchers("/jwt/authenticate").permitAll()
                        .requestMatchers("/secure/data").hasRole("ADMIN")//ARRANGE THE REQUEST FIRST IN ORDER FOR SPECIFIC URL
                        //BECAUSE IN SPRING FIRST RULE FIRST WIN
                        .requestMatchers("/secure/**").authenticated()
                        .anyRequest().authenticated());
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(daoAuthenticationProvider);


    }

}
