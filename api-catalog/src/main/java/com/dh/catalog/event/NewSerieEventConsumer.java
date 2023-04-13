package com.dh.catalog.event;

import com.dh.catalog.config.RabbitMQConfig;
import com.dh.catalog.service.CatalogService;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class NewSerieEventConsumer {
    @Autowired
    private CatalogService catalogService;

    //subscription to message queue
    @RabbitListener(queues = RabbitMQConfig.QUEUE_NEW_SERIE)
    public void listenNewSerieEvent(MessageSerie message) {
        System.out.println("[Notification] New serie with genre : " + message.genre);
        catalogService.createSerie(message);
    }

    @Data
    public static class MessageSerie {

        private String name;
        private String genre;
        private List<Season> seasons = new ArrayList<>();

        @Data
        public static class Season {

            private Integer seasonNumber;
            private List<Chapter> chapters = new ArrayList<>();

            @Data
            public static class Chapter {

                private String name;
                private Integer number;
                private String urlStream;

            }
        }
    }
}
