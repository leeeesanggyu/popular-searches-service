package com.salgu.message;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducer implements MessageProducer{

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void send(String message) {
        kafkaTemplate.send("search-term", message);
    }
}
