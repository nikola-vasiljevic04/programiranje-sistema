package org.raflab.gatewayservice.filters;

import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RefreshScope
public class RateLimitFilter implements GlobalFilter, Ordered {

    private final Map<String, Deque<Long>> requestLog = new ConcurrentHashMap<>();

    @Value("${gateway.rate-limit.read:100}")
    private int maxRequestsRead;

    @Value("${gateway.rate-limit.write:10}")
    private int maxRequestsWrite;

    @Value("${gateway.rate-limit.window-ms:10000}")
    private long windowMs;

    @Override
    public @NonNull Mono<Void> filter(ServerWebExchange exchange, @NonNull GatewayFilterChain chain) {
        String clientId = exchange.getRequest().getHeaders().getFirst("X-API-KEY");
        if (clientId == null) {
            clientId = exchange.getRequest().getRemoteAddress() != null
                    ? exchange.getRequest().getRemoteAddress().getAddress().getHostAddress()
                    : "unknown";
        }

        HttpMethod method = exchange.getRequest().getMethod();

        String limitType = (method == HttpMethod.GET) ? "READ" : "WRITE";
        int limit = (method == HttpMethod.GET) ? maxRequestsRead : maxRequestsWrite;

        String storageKey = clientId + "#" + limitType;

        long now = System.currentTimeMillis();
        requestLog.putIfAbsent(storageKey, new ArrayDeque<>());
        Deque<Long> timestamps = requestLog.get(storageKey);

        synchronized (timestamps) {
            while (!timestamps.isEmpty() && now - timestamps.peekFirst() > windowMs) {
                timestamps.pollFirst();
            }

            if (timestamps.size() >= limit) {
                exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
                exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

                addRateLimitHeaders(exchange, 0, limit);

                return Mono.error(new org.springframework.web.server.ResponseStatusException(
                        HttpStatus.TOO_MANY_REQUESTS,
                        "Rate limit exceeded for %s. Limit is %d per 10s.".formatted(method, limit)
                ));
            }

            timestamps.addLast(now);
            addRateLimitHeaders(exchange, limit - timestamps.size(), limit);
        }

        return chain.filter(exchange);
    }

    private void addRateLimitHeaders(ServerWebExchange exchange, int remaining, int limit) {
        exchange.getResponse().getHeaders().set("X-RateLimit-Limit", String.valueOf(limit));
        exchange.getResponse().getHeaders().set("X-RateLimit-Remaining", String.valueOf(remaining));
        exchange.getResponse().getHeaders().set("X-RateLimit-Reset", String.valueOf(windowMs / 1000));
    }

    @Override
    public int getOrder() {
        return -3;
    }
}