package com.state.machine.test.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class KafkaProducer {
  private final KafkaTemplate<String, String> kafkaTemplate;
  public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) { this.kafkaTemplate = kafkaTemplate; }

  public void publish(String topic, String json) {
    kafkaTemplate.send(topic, json);
  }
}