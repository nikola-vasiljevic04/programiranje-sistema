
package com.ecotravel.maintenance_service.config;

import com.ecotravel.maintenance_service.exception.GlobalFeignErrorDecoder;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class FeignInterceptorConfig {
    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                
                String traceId = request.getHeader("X-Trace-ID");
                if (traceId != null) {
                    requestTemplate.header("X-Trace-ID", traceId);
                }
                
                String userId = request.getHeader("X-Authenticated-User");
                if (userId != null) {
                    requestTemplate.header("X-Authenticated-User", userId);
                }

                String userRole = request.getHeader("X-User-Role");
                if (userRole != null) {
                    requestTemplate.header("X-User-Role", userRole);
                }
            }
        };
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new GlobalFeignErrorDecoder();
    }
}