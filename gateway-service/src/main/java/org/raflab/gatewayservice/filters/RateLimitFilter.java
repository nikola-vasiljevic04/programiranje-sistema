package org.raflab.gatewayservice.filters;

import org.jspecify.annotations.NonNull;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitFilter implements GlobalFilter, Ordered {

    private final Map<String, Deque<Long>> requestLog = new ConcurrentHashMap<>();

    private static final int MAX_REQUESTS_READ = 100;
    private static final int MAX_REQUESTS_OTHER = 10;
    private static final long WINDOW_MS = 10_000;

    private static final List<String> CRITICAL_ROUTES = List.of(
            "/api/transactions",
            "/api/bookings",
            "/api/adventures"
    );

    @Override
    public @NonNull Mono<Void> filter(ServerWebExchange exchange, @NonNull GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();

        if (CRITICAL_ROUTES.stream().noneMatch(path::startsWith)) {
            return chain.filter(exchange);
        }

        String clientId = exchange.getRequest().getHeaders().getFirst("X-API-KEY");
        if (clientId == null) {
            clientId = exchange.getRequest().getRemoteAddress() != null
                    ? exchange.getRequest().getRemoteAddress().getAddress().getHostAddress()
                    : "unknown";
        }

        HttpMethod method = exchange.getRequest().getMethod();

        String limitType = (method == HttpMethod.GET) ? "READ" : "WRITE";
        int limit = (method == HttpMethod.GET) ? MAX_REQUESTS_READ : MAX_REQUESTS_OTHER;

        String storageKey = clientId + "#" + limitType;

        long now = System.currentTimeMillis();
        requestLog.putIfAbsent(storageKey, new ArrayDeque<>());
        Deque<Long> timestamps = requestLog.get(storageKey);

        synchronized (timestamps) {
            while (!timestamps.isEmpty() && now - timestamps.peekFirst() > WINDOW_MS) {
                timestamps.pollFirst();
            }

            if (timestamps.size() >= limit) {
                exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
                exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

                addRateLimitHeaders(exchange, 0, limit);

                String body = """
                    {"message": "Rate limit exceeded for %s. Limit is %d per 10s.", "status": 429}
                """.formatted(method, limit);
                DataBuffer buffer = exchange.getResponse().bufferFactory()
                        .wrap(body.getBytes(StandardCharsets.UTF_8));
                return exchange.getResponse().writeWith(Mono.just(buffer));
            }

            timestamps.addLast(now);
            addRateLimitHeaders(exchange, limit - timestamps.size(), limit);
        }

        return chain.filter(exchange);
    }

    private void addRateLimitHeaders(ServerWebExchange exchange, int remaining, int limit) {
        exchange.getResponse().getHeaders().add("X-RateLimit-Limit", String.valueOf(limit));
        exchange.getResponse().getHeaders().add("X-RateLimit-Remaining", String.valueOf(remaining));
        exchange.getResponse().getHeaders().add("X-RateLimit-Reset", String.valueOf(WINDOW_MS / 1000));
    }

    @Override
    public int getOrder() {
        return -3;
    }
}