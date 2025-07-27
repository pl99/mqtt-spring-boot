package ru.advantum.commons.mqtt.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.UUID;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ConfigurationProperties(prefix = "mqtt")
public class MqttProperties {

    String[] uris;
    String username;
    String password;
    String clientId = "mqtt-client-" + UUID.randomUUID();
    int connectionTimeout = 30;
    boolean cleanSession = true;
    String defaultPublishTopic;
    String[] subscriptionTopics = new String[0];
    int[] subscriptionQos = new int[0];
}

