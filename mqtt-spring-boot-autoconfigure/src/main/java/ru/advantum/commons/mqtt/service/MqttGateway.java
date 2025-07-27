package ru.advantum.commons.mqtt.service;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;

@MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
public interface MqttGateway {

    @Gateway(requestChannel = "mqttOutboundChannel")
    void send(String payload);

    @Gateway(requestChannel = "mqttOutboundChannel")
    void sendToTopic(@Header(MqttHeaders.TOPIC) String topic, String payload);

    @Gateway(requestChannel = "mqttOutboundChannel")
    void sendToTopic(@Header(MqttHeaders.TOPIC) String topic, @Header(MqttHeaders.QOS) int qos, String payload);
}

