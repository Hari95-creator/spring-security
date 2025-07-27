package spring.security.security.Config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordCheckRunner {

    @Bean
    public CommandLineRunner testPassword(PasswordEncoder passwordEncoder) {
        return args -> {
            String rawPassword = "admin123";
            String encodedPassword = "$2a$10$TtsIOgledk0BdmxV0t/L5u1yuvLuclHH5nVM3haoYupfNwhvI5FTG";

            String encoded = passwordEncoder.encode(rawPassword);
            System.out.println("🔐 New encoded password: " + encoded);

            boolean match = passwordEncoder.matches(rawPassword, encodedPassword);
            System.out.println("🔐 Password match: " + match);
        };
    }
}
