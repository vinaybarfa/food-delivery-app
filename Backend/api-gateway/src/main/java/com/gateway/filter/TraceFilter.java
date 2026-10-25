package com.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class TraceFilter
        implements GlobalFilter {

    public static final String TRACE_ID =
            "X-Trace-Id";

    @Override
    public Mono<Void> filter(
            ServerWebExchange exchange,
            GatewayFilterChain chain
    ) {

        String traceId =
                UUID.randomUUID().toString();

        exchange.getRequest()
                .mutate()
                .header(TRACE_ID, traceId)
                .build();

        exchange.getResponse()
                .getHeaders()
                .add(TRACE_ID, traceId);

        return chain.filter(exchange);
    }
}