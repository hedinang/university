package com.example.university.util.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.RabbitListenerErrorHandler;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class RabbitmqListenerHandler implements RabbitListenerErrorHandler {

    @Override
    public Object handleError(Message amqpMessage,
                              org.springframework.messaging.Message<?> message,
                              ListenerExecutionFailedException exception) {

        String exchange = amqpMessage.getMessageProperties().getReceivedExchange();
        String queue = amqpMessage.getMessageProperties().getConsumerQueue();
        byte[] body = amqpMessage.getBody();
        String payload = new String(body);
        log.warn(String.format("Error is from exchange: %s and queue: %s with payload: %s", exchange, queue, payload));
        return null;
    }
}
