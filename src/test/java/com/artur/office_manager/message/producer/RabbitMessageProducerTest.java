package com.artur.office_manager.message.producer;

import com.artur.common.bean.board.Airplane;
import com.artur.common.bean.board.Board;
import com.artur.common.bean.office.AirPort;
import com.artur.common.message.BoardMessage;
import com.artur.common.message.OfficeMessage;
import com.artur.common.message.Type;
import com.artur.common.message.converter.MessageConverter;
import com.artur.common.model.Position;
import com.artur.office_manager.OfficeManagerApplicationTests;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.test.context.SpringRabbitTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@SpringRabbitTest
class RabbitMessageProducerTest extends OfficeManagerApplicationTests {

    @Autowired
    private RabbitMessageProducer messageProducer;
    @MockBean
    @Qualifier("rabbitTemplate")
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private MessageConverter messageConverter;

    @Test
    void sendOfficeMessage() {
        AirPort airPort = new AirPort(UUID.randomUUID().toString(), "test-airport", Position.builder().x(0).x(0).angle(0).build());
        OfficeMessage officeMessage = new OfficeMessage(airPort, Type.CREATE);
        String jsonMessage = messageConverter.convertToJson(officeMessage);
        messageProducer.sendOfficeMessage(officeMessage);
        ArgumentCaptor<Message> messageCaptor = ArgumentCaptor.forClass(Message.class);


        verify(rabbitTemplate).send(anyString(), anyString(), messageCaptor.capture());
        Message sentMessage = messageCaptor.getValue();
        assertEquals(new String(jsonMessage.getBytes()), new String(sentMessage.getBody()));
    }

    @Test
    void sendBoardMessage() {
        Board airplane = new Airplane(UUID.randomUUID().toString(), "test-airplane", Position.builder().x(0).x(0).angle(0).build());
        BoardMessage boardMessage = new BoardMessage(airplane, Type.CREATE);
        String jsonMessage = messageConverter.convertToJson(boardMessage);
        messageProducer.sendBoardMessage(boardMessage);
        ArgumentCaptor<Message> messageCaptor = ArgumentCaptor.forClass(Message.class);


        verify(rabbitTemplate).send(anyString(), anyString(), messageCaptor.capture());
        Message sentMessage = messageCaptor.getValue();
        assertEquals(new String(jsonMessage.getBytes()), new String(sentMessage.getBody()));
    }
}