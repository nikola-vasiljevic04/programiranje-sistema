package org.raflab.gatewayservice.filters;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.raflab.gatewayservice.dto.Role;
import org.raflab.gatewayservice.dto.UserDTO;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
public class AuthenticationFilter implements GlobalFilter, Ordered {

    private final WebClient webClient = WebClient.create("http://localhost:8079");

    private static final List<String> PUBLIC_ROUTES = List.of(
            "/api/auth", "/swagger-ui", "/v3/api-docs", "/actuator", "/docs"
    );

    private record RouteKey(String path, HttpMethod method) {}
    private static final Map<RouteKey, Set<Role>> ROUTE_PERMISSIONS = Map.ofEntries(
            Map.entry(new RouteKey("/api/availability", HttpMethod.POST), Set.of(Role.ADMIN)),
            Map.entry(new RouteKey("/api/units", HttpMethod.POST),        Set.of(Role.ADMIN)),
            Map.entry(new RouteKey("/api/addresses", HttpMethod.POST),    Set.of(Role.ADMIN)),
            Map.entry(new RouteKey("/api/time-slots/", HttpMethod.POST),    Set.of(Role.ADMIN, Role.GUIDE, Role.GUEST)),
            Map.entry(new RouteKey("/api/time-slots", HttpMethod.POST),    Set.of(Role.ADMIN, Role.GUIDE)),
            Map.entry(new RouteKey("/api/gear", HttpMethod.POST),    Set.of(Role.ADMIN)),
            Map.entry(new RouteKey("/api/gear", HttpMethod.PUT),    Set.of(Role.ADMIN)),
            Map.entry(new RouteKey("/api/category", HttpMethod.POST),    Set.of(Role.ADMIN)),
            Map.entry(new RouteKey("/api/category", HttpMethod.PUT),    Set.of(Role.ADMIN)),
            Map.entry(new RouteKey("/api/adventure", HttpMethod.POST),    Set.of(Role.ADMIN)),
            Map.entry(new RouteKey("/api/adventure", HttpMethod.PUT),    Set.of(Role.ADMIN)),
            Map.entry(new RouteKey("/api/policies", HttpMethod.POST),    Set.of(Role.ADMIN)),
            Map.entry(new RouteKey("/api/addons", HttpMethod.PUT),    Set.of(Role.ADMIN)),
            Map.entry(new RouteKey("/api/bookings/", HttpMethod.POST),    Set.of(Role.GUEST)),
            Map.entry(new RouteKey("/api/bookings/", HttpMethod.PATCH),    Set.of(Role.GUEST)),
            Map.entry(new RouteKey("/api/bookings/", HttpMethod.DELETE),    Set.of(Role.GUEST)),
            Map.entry(new RouteKey("/api/guests", HttpMethod.GET),    Set.of(Role.ADMIN)),
            Map.entry(new RouteKey("/api/guests", HttpMethod.POST),    Set.of(Role.GUEST)),
            Map.entry(new RouteKey("/api/invoices", HttpMethod.GET),    Set.of(Role.ADMIN)),
            Map.entry(new RouteKey("/api/invoices", HttpMethod.POST),    Set.of(Role.ADMIN)),
            Map.entry(new RouteKey("/api/invoices", HttpMethod.PATCH),    Set.of(Role.ADMIN)),
            Map.entry(new RouteKey("/api/payment-methods", HttpMethod.POST),    Set.of(Role.ADMIN)),
            Map.entry(new RouteKey("/api/tax-rates", HttpMethod.POST),    Set.of(Role.ADMIN)),
            Map.entry(new RouteKey("/api/transactions", HttpMethod.GET),    Set.of(Role.ADMIN)),
            Map.entry(new RouteKey("/api/work-orders", HttpMethod.GET),  Set.of(Role.ADMIN)),
            Map.entry(new RouteKey("/api/work-orders", HttpMethod.POST),  Set.of(Role.ADMIN)),
            Map.entry(new RouteKey("/api/work-orders", HttpMethod.PUT),  Set.of(Role.ADMIN)),
            Map.entry(new RouteKey("/api/workers", HttpMethod.GET),       Set.of(Role.ADMIN)),
            Map.entry(new RouteKey("/api/workers", HttpMethod.POST),      Set.of(Role.ADMIN)),
            Map.entry(new RouteKey("/api/tasks", HttpMethod.GET),       Set.of(Role.ADMIN)),
            Map.entry(new RouteKey("/api/tasks", HttpMethod.POST),      Set.of(Role.ADMIN))
    );

    @Override
    public @NonNull Mono<Void> filter(ServerWebExchange exchange, @NonNull GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();

        if (PUBLIC_ROUTES.stream().anyMatch(path::startsWith)) {
            return chain.filter(exchange);
        }

        String apiKey = exchange.getRequest().getHeaders().getFirst("X-API-KEY");

        if (apiKey == null || apiKey.isBlank()) {
            return unauthorized("Missing X-API-KEY header");
        }

        String traceId = exchange.getRequest().getHeaders().getFirst("X-Trace-ID");
        if (traceId == null) traceId = "N/A";

        log.info("AuthenticationFilter processing with TraceID: {}", traceId);


        return webClient.get()
                .uri("/api/auth/validate")
                .header("X-API-KEY", apiKey)
                .header("X-TRACE-ID", traceId)
                .retrieve()
                .bodyToMono(UserDTO.class)
                .flatMap(authResponse -> {
                    if (!hasAccess(path, exchange.getRequest().getMethod(), authResponse.getRoles())) {
                        return forbidden();
                    }

                    ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                            .header("X-Authenticated-User", String.valueOf(authResponse.getId()))
                            .header("X-User-Role", authResponse.getRoles().toString())
                            .build();

                    return chain.filter(exchange.mutate().request(mutatedRequest).build());
                })
                .onErrorResume(WebClientResponseException.class, e -> {
                    if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                        return unauthorized("Invalid API key");
                    }
                    return serviceUnavailable();
                });
    }

    private boolean hasAccess(String path, HttpMethod method, Set<Role> roles) {
        String matchedPath = ROUTE_PERMISSIONS.keySet().stream()
                .map(RouteKey::path)
                .filter(path::startsWith)
                .findFirst()
                .orElse(null);

        if (matchedPath == null) {
            return method != HttpMethod.DELETE || roles.contains(Role.ADMIN);
        }

        RouteKey key = new RouteKey(matchedPath, method);
        Set<Role> allowed = ROUTE_PERMISSIONS.get(key);

        if (allowed == null) return true;
        return allowed.stream().anyMatch(roles::contains);
    }

    private Mono<Void> unauthorized(String message) {
        return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, message));
    }

    private Mono<Void> forbidden() {
        return Mono.error(new ResponseStatusException(HttpStatus.FORBIDDEN, "Insufficient permissions"));
    }

    private Mono<Void> serviceUnavailable() {
        return Mono.error(new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Auth service unavailable"));
    }

    @Override
    public int getOrder() { return -1; }
}