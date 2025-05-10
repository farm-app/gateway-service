package ru.rtln.gatewayservice.filter;

import io.micrometer.tracing.CurrentTraceContext;
import io.micrometer.tracing.TraceContext;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.apache.commons.lang.StringUtils.EMPTY;

@RequiredArgsConstructor
@Component
public class TraceFilter implements GlobalFilter {

    private static final String TRACE_ID_HEADER_NAME = "X-Trace-Id";

    private final Tracer tracer;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpResponse response = exchange.getResponse();
        if (!response.getHeaders().containsKey(TRACE_ID_HEADER_NAME)) {
            String traceId = getTraceId();

            if (traceId.isEmpty()) {
                return chain.filter(exchange);
            }

            response.getHeaders().add(TRACE_ID_HEADER_NAME, traceId);
        }
        return chain.filter(exchange);
    }

    private String getTraceId() {
        return Optional.of(tracer)
                .map(Tracer::currentTraceContext)
                .map(CurrentTraceContext::context)
                .map(TraceContext::traceId)
                .orElse(EMPTY);
    }
}