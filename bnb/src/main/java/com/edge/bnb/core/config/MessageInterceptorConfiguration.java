package com.edge.bnb.core.config;

import com.edge.bnb.core.interceptor.LoggingInterceptor;
import org.axonframework.commandhandling.CommandBus;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageInterceptorConfiguration {
    @Bean
    public boolean registerCommandInterceptor(ApplicationContext applicationContext, CommandBus commandBus) {
        commandBus.registerDispatchInterceptor(applicationContext.getBean(LoggingInterceptor.class));
//      commandBus.registerDispatchInterceptor(new BeanValidationInterceptor<>());
        commandBus.registerHandlerInterceptor(applicationContext.getBean(LoggingInterceptor.class));
        return true;
    }
}
