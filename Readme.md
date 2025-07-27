# MQTT Spring Boot Starter

This is a Spring Boot auto-configuration module for MQTT integration using Eclipse Paho and Spring Integration.

## Features

- Auto-configuration for MQTT client
- Support for multiple broker URIs
- Authentication support (username/password)
- Configurable client ID (with random UUID as default)
- Outbound message gateway
- Inbound message channel adapter
- Configurable QoS levels
- Default topic configuration

## Installation

Add the dependency to your Spring Boot project:

```xml
<dependency>
    <groupId>ru.advantum.commons</groupId>
    <artifactId>mqtt-spring-boot-autoconfigure</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Configuration

Add the following properties to your `application.yml` or `application.properties`:

```yaml
mqtt:
  uris: ["tcp://localhost:1883"] # required - array of broker URIs
  username: "user"               # optional
  password: "pass"               # optional
  client-id: "custom-client-id"  # optional (default: random UUID)
  clean-session: true            # optional (default: true)
  connection-timeout: 30         # optional (default: 30 seconds)
  default-publish-topic: "topic/default" # optional default topic for publishing
  subscription-topics: ["topic/1", "topic/2"] # optional topics to subscribe to
  subscription-qos: [1, 2]       # optional QoS levels for subscribed topics
```

## Usage

### Sending Messages

Inject `MqttGateway` in your service/controller:

```java
@RestController
@RequiredArgsConstructor
public class MessageController {
    private final MqttGateway mqttGateway;
    
    @PostMapping("/send")
    public String send(@RequestBody String message) {
        mqttGateway.send(message); // sends to default topic
        return "Message sent";
    }
    
    @PostMapping("/send-to-topic")
    public String sendToTopic(@RequestParam String topic, 
                             @RequestBody String message) {
        mqttGateway.sendToTopic(topic, message);
        return "Message sent to topic: " + topic;
    }
}
```

### Receiving Messages

Create a service to handle incoming messages:

```java
@Slf4j
@Service
public class MqttListenerService {
    @ServiceActivator(inputChannel = "mqttInboundChannel")
    public void handleMessage(
            @Header(MqttHeaders.RECEIVED_TOPIC) String topic, 
            String payload) {
        log.info("Received message on {}: {}", topic, payload);
        // Add your message processing logic here
    }
}
```

## API Reference

### MqttGateway Methods

- `void send(String payload)` - Send message to default topic
- `void sendToTopic(String topic, String payload)` - Send message to specific topic
- `void sendToTopic(String topic, int qos, String payload)` - Send message to specific topic with QoS

### Configuration Properties

| Property | Type | Default | Description |
|----------|------|---------|-------------|
| mqtt.uris | String[] | - | MQTT broker URIs (required) |
| mqtt.username | String | - | Username for authentication |
| mqtt.password | String | - | Password for authentication |
| mqtt.client-id | String | random UUID | Client identifier |
| mqtt.clean-session | boolean | true | Clean session flag |
| mqtt.connection-timeout | int | 30 | Connection timeout in seconds |
| mqtt.default-publish-topic | String | - | Default topic for publishing |
| mqtt.subscription-topics | String[] | [] | Topics to subscribe to |
| mqtt.subscription-qos | int[] | [] | QoS levels for subscribed topics |

## Example Application

See the `example-app` module for a complete working example.

## Requirements

- Java 21+
- Spring Boot 3.4.1
- MQTT broker (e.g., Mosquitto, EMQX)