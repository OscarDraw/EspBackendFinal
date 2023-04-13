package com.dh.serie.event;

import com.dh.serie.config.RabbitMQConfig;
import com.dh.serie.model.Serie;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NewSerieEventProducer {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void publishNewSerieEvent(Serie movie) {
        MessageMovie message = new MessageMovie();
        BeanUtils.copyProperties(movie, message);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.TOPIC_NEW_SERIE, message);
    }


    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class MessageMovie {
        private String name;
        private String genre;
        private String urlStream;
    }
}
