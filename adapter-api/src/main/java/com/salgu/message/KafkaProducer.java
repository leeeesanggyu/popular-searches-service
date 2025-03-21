package com.salgu.message;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducer implements MessageProducer{

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void send(String message) {
        log.info("Produce Message: {}", message);
        kafkaTemplate.send("search-term", message);
    }
}
