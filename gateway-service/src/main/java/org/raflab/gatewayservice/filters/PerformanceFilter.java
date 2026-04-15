package org.raflab.gatewayservice.filters;

import org.jspecify.annotations.NonNull;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class PerformanceFilter implements GlobalFilter, Ordered {

    @Override
    public @NonNull Mono<Void> filter(ServerWebExchange exchange, @NonNull GatewayFilterChain chain) {
        long startTime = System.currentTimeMillis();

        String traceId = exchange.getRequest().getHeaders().getFirst("X-Trace-ID");
        if (traceId == null) traceId = UUID.randomUUID().toString();
        final String finalTraceId = traceId;

        exchange.getResponse().beforeCommit(() -> {
            long duration = System.currentTimeMillis() - startTime;
            exchange.getResponse().getHeaders().add("X-Processing-Time-Ms", String.valueOf(duration));
            exchange.getResponse().getHeaders().add("X-Trace-ID", finalTraceId);
            exchange.getResponse().getHeaders().add("X-Eco-Service", "EcoTravel-Gateway");
            return Mono.empty();
        });

        ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                .header("X-Trace-ID", finalTraceId)
                .build();

        return chain.filter(exchange.mutate().request(mutatedRequest).build());
    }

    @Override
    public int getOrder() { return -2; }
}