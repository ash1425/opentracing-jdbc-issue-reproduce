package com.ashay.opentracing.jdbctest.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.jaegertracing.internal.JaegerTracer;
import io.jaegertracing.internal.reporters.CompositeReporter;
import io.jaegertracing.internal.reporters.LoggingReporter;
import io.jaegertracing.internal.reporters.RemoteReporter;
import io.jaegertracing.internal.samplers.ConstSampler;
import io.jaegertracing.thrift.internal.senders.UdpSender;
import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties(DataSourceProperties.class)
public class TracingConfig {

    @Value("${spring.application.name:undefined}")
    private String serviceName;

    @Bean
    public DataSource dataSource(DataSourceProperties dataSourceProperties) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(dataSourceProperties.getUrl());
        hikariConfig.setUsername(dataSourceProperties.getUsername());
        hikariConfig.setPassword(dataSourceProperties.getPassword());
        hikariConfig.setDriverClassName(dataSourceProperties.determineDriverClassName());

        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public Tracer tracer() {
        return new JaegerTracer.Builder(serviceName)
                .withSampler(new ConstSampler(true))
                .withReporter(new CompositeReporter(new LoggingReporter(), jaegerRemoterReporter()))
                .build();
    }

    private RemoteReporter jaegerRemoterReporter() {
        return new RemoteReporter.Builder().withSender(new UdpSender()).build();
    }

}
