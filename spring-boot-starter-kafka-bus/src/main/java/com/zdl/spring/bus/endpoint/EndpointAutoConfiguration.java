package com.zdl.spring.bus.endpoint;

import com.zdl.spring.bus.BusProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Created by ZDLegend on 2019/4/10 17:34
 */
@Configuration
@EnableConfigurationProperties(BusProperties.class)
public class EndpointAutoConfiguration {
    @Bean
    @ConditionalOnBean(BaseBusEndpoint.class)
    public EndpointManage endpointManage(List<BaseBusEndpoint> endpoints, BusProperties properties) {
        var endpointManage = new EndpointManage(endpoints, properties);
        endpointManage.initEndPointMap();
        return endpointManage;
    }
}
