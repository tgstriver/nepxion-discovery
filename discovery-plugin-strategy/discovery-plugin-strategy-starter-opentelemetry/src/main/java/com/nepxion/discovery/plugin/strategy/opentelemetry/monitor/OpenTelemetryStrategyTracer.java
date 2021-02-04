package com.nepxion.discovery.plugin.strategy.opentelemetry.monitor;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.plugin.strategy.constant.StrategyConstant;
import com.nepxion.discovery.plugin.strategy.monitor.AbstractStrategyTracer;

public class OpenTelemetryStrategyTracer extends AbstractStrategyTracer<Span> {

    @Value("${" + StrategyConstant.SPRING_APPLICATION_STRATEGY_TRACER_EXCEPTION_DETAIL_OUTPUT_ENABLED + ":false}")
    protected Boolean tracerExceptionDetailOutputEnabled;

    @Autowired
    private Tracer tracer;

    @Override
    protected Span buildSpan() {
        return tracer.spanBuilder(tracerSpanValue).startSpan();
    }

    @Override
    protected void outputSpan(Span span, String key, String value) {
        span.setAttribute(key, value);
    }

    @Override
    protected void errorSpan(Span span, Throwable e) {
        span.setAttribute(DiscoveryConstant.EVENT, DiscoveryConstant.ERROR);
        if (tracerExceptionDetailOutputEnabled) {
            span.setAttribute(DiscoveryConstant.ERROR_OBJECT, ExceptionUtils.getStackTrace(e));
        } else {
            span.recordException(e);
        }
    }

    @Override
    protected void finishSpan(Span span) {
        span.end();
    }

    //  Never used probably
    @Override
    protected Span getActiveSpan() {
        return null;
    }

    @Override
    protected String toTraceId(Span span) {
        return span.getSpanContext().getTraceIdAsHexString();
    }

    @Override
    protected String toSpanId(Span span) {
        return span.getSpanContext().getSpanIdAsHexString();
    }
}