package com.edge.bnb.core.interceptor;

import com.edge.bnb.core.base.BaseCommand;
import com.edge.bnb.core.base.BaseEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.messaging.InterceptorChain;
import org.axonframework.messaging.Message;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.axonframework.messaging.MessageHandlerInterceptor;
import org.axonframework.messaging.unitofwork.UnitOfWork;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.BiFunction;

@Slf4j
@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class LoggingInterceptor implements MessageDispatchInterceptor<Message<?>>,
        MessageHandlerInterceptor<Message<?>> {
    @Override
    public BiFunction<Integer, Message<?>, Message<?>> handle(List<? extends Message<?>> messages) {
        return (index, message) -> {
            boolean isCommand = message instanceof CommandMessage && message.getPayload() instanceof BaseCommand;
            boolean isEvent = message instanceof EventMessage && message.getPayload() instanceof BaseEvent;

            if (isCommand) logBeforeCommandDispatched(message);
            if (isEvent) logBeforeEventDispatched(message);
            return message;
        };
    }

    private void logBeforeCommandDispatched(Message command) {
        BaseCommand payload = (BaseCommand) command.getPayload();
        log.info("Dispatching a command {} and the command structure is {}", payload.getClass(), payload.toString());
    }

    private void logBeforeEventDispatched(Message event) {
        BaseCommand payload = (BaseCommand) event.getPayload();
        log.info("Dispatching a command {} and the command structure is {}", payload.getClass(), payload.toString());
    }

    public Object handle(UnitOfWork<? extends Message<?>> unitOfWork, InterceptorChain interceptorChain) throws Exception {
        return interceptorChain.proceed();
    }
}
