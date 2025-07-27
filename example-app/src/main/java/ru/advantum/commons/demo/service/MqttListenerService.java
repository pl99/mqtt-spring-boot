package ru.advantum.commons.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MqttListenerService {

    @ServiceActivator(inputChannel = "mqttInboundChannel")
    public void handleMessage(@Header(MqttHeaders.RECEIVED_TOPIC) String topic, String payload) {
        log.info("Received MQTT message on topic '{}': {}", topic, payload);
        // Здесь ваша логика обработки сообщения
    }
}
