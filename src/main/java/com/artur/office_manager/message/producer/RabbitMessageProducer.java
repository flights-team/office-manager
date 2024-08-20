package com.artur.office_manager.message.producer;

import com.artur.common.message.BoardMessage;
import com.artur.common.message.OfficeMessage;
import com.artur.common.message.converter.MessageConverter;
import com.artur.office_manager.config.RabbitMQConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMessageProducer {

    @Autowired
    private RabbitMQConfig rabbitMQConfig;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private MessageConverter messageConverter;

    public void sendOfficeMessage(OfficeMessage officeMessage){
        rabbitTemplate.send(
                rabbitMQConfig.getExchangeName(),
                rabbitMQConfig.getOfficeQueue() + '.' + officeMessage.getType().name().toLowerCase(),
                new Message(messageConverter.convertToJson(officeMessage).getBytes())
        );
    }

    public void sendBoardMessage(BoardMessage boardMessage){
        rabbitTemplate.send(
                rabbitMQConfig.getExchangeName(),
                rabbitMQConfig.getBoardQueue() + '.' + boardMessage.getType().name().toLowerCase(),
                new Message(messageConverter.convertToJson(boardMessage).getBytes())
        );
    }
}
