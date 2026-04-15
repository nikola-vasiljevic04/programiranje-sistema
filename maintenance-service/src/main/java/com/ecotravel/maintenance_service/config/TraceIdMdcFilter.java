package com.ecotravel.maintenance_service.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class TraceIdMdcFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        
        if (request instanceof HttpServletRequest httpServletRequest) {
            String traceId = httpServletRequest.getHeader("X-Trace-ID");
            System.out.println(traceId);
            if (traceId != null) {
                MDC.put("traceId", traceId);
            }
        }
        
        try {
            chain.doFilter(request, response);
        } finally {
            MDC.remove("traceId");
        }
    }
}