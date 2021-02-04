package com.nepxion.discovery.plugin.strategy.service.isolation;

import com.nepxion.discovery.plugin.strategy.service.annotation.ServiceStrategy;
import com.nepxion.matrix.proxy.aop.DefaultAutoScanProxy;
import com.nepxion.matrix.proxy.mode.ProxyMode;
import com.nepxion.matrix.proxy.mode.ScanMode;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Annotation;

public class ServiceProviderIsolationStrategyAutoScanProxy extends DefaultAutoScanProxy {

    private static final long serialVersionUID = 6147822053647878553L;

    private String[] commonInterceptorNames;

    @SuppressWarnings("rawtypes")
    private Class[] classAnnotations;

    public ServiceProviderIsolationStrategyAutoScanProxy(String scanPackages) {
        super(scanPackages, ProxyMode.BY_CLASS_ANNOTATION_ONLY, ScanMode.FOR_CLASS_ANNOTATION_ONLY);
    }

    @Override
    protected String[] getCommonInterceptorNames() {
        if (commonInterceptorNames == null) {
            commonInterceptorNames = new String[]{"serviceProviderIsolationStrategyInterceptor"};
        }

        return commonInterceptorNames;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Class<? extends Annotation>[] getClassAnnotations() {
        if (classAnnotations == null) {
            classAnnotations = new Class[]{RestController.class, ServiceStrategy.class};
        }

        return classAnnotations;
    }
}