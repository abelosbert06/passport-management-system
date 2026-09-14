package com.example.passport.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final RoleAuthInterceptor roleAuthInterceptor;

    public WebMvcConfig(RoleAuthInterceptor roleAuthInterceptor) {
        this.roleAuthInterceptor = roleAuthInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(roleAuthInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns(
                        "/api/auth/**",
                        "/api/applications/track/**",
                        "/swagger-ui/**",
                        "/v3/api-docs/**"
                );
    }
}
