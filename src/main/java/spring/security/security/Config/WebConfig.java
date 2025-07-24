package spring.security.security.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import spring.security.security.Interceptor.LoggingInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    LoggingInterceptor loggingInterceptor;
    // Here in this config class  basically registering the interceptor
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // Below is the version of registering the Interceptor
        registry.addInterceptor(loggingInterceptor).
                addPathPatterns("/secure/**").
                excludePathPatterns("/secure/auth");

    }
}
