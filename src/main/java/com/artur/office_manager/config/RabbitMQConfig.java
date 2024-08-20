package com.artur.office_manager.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class RabbitMQConfig {
    private final AmqpAdmin amqpAdmin;

    @Value("${spring.rabbitmq.template.exchange}")
    private String exchangeName;
    @Value("${spring.rabbitmq.template.queues.office-queue}")
    private String officeQueue;
    @Value("${spring.rabbitmq.template.queues.board-queue}")
    private String boardQueue;

    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(exchangeName);
    }

    @Bean
    public Binding officeQueue(TopicExchange topicExchange){
        var queue = new Queue(officeQueue);
        amqpAdmin.declareQueue(queue);
        return BindingBuilder.bind(queue).to(topicExchange).with( officeQueue + ".#");
    }

    @Bean
    public Binding boardQueue(TopicExchange topicExchange){
        var queue = new Queue(boardQueue);
        amqpAdmin.declareQueue(queue);
        return BindingBuilder.bind(queue).to(topicExchange).with( boardQueue + ".#");
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        return new RabbitTemplate(connectionFactory);
    }

}
