package ru.advantum.commons.demo.controller;

import ru.advantum.commons.mqtt.service.MqttGateway;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageController {

    MqttGateway mqttGateway;

    @PostMapping("/send")
    public String sendMessage(@RequestBody String message) {
        mqttGateway.send(message); // Отправка в топик по умолчанию
        return "Message sent to default topic!";
    }

    @PostMapping("/send-to-topic")
    public String sendMessageToTopic(@RequestParam String topic, @RequestBody String message) {
        mqttGateway.sendToTopic(topic, message);
        return "Message sent to topic: " + topic;
    }
}

