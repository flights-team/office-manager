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
    @Value("${spring.rabbitmq.template.queues}")
    private List<String> queues;


    private TopicExchange topicExchange(){
        return new TopicExchange(exchangeName);
    }

    @PostConstruct
    public void configureQueues(){
        TopicExchange topicExchange = topicExchange();
        amqpAdmin.declareExchange(topicExchange);
        queues.forEach(queueName ->{
            Queue queue = new Queue(queueName);
            amqpAdmin.declareQueue(queue);
            amqpAdmin.declareBinding(BindingBuilder.bind(queue).to(topicExchange).with(queueName + ".#"));
        });
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        return new RabbitTemplate(connectionFactory);
    }

}
