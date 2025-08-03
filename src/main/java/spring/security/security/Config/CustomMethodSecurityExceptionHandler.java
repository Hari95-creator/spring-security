package spring.security.security.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.core.GrantedAuthorityDefaults;

@Configuration
public class CustomMethodSecurityExceptionHandler {

    //in Spring default it will provide ROLE as prefix .
    // for customizing the prefix in authorization you can use like this
    @Bean
    static GrantedAuthorityDefaults grantedAuthorityDefaults(){

        return new GrantedAuthorityDefaults("EPIC_");
    }
}
