# MQTT Spring Boot Starter

Это модуль автоконфигурации Spring Boot для интеграции MQTT с использованием Eclipse Paho и Spring Integration.

## Возможности

- Автоконфигурация MQTT клиента
- Поддержка нескольких URI брокеров
- Поддержка аутентификации (логин/пароль)
- Настраиваемый идентификатор клиента (по умолчанию — случайный UUID)
- Шлюз для отправки сообщений
- Адаптер для приема сообщений
- Настраиваемые уровни QoS
- Конфигурация топика по умолчанию

## Установка

Добавьте зависимость в ваш Spring Boot проект:

```xml
<dependency>
    <groupId>ru.advantum.commons</groupId>
    <artifactId>mqtt-spring-boot-autoconfigure</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Конфигурация

Добавьте следующие свойства в ваш `application.yml` или `application.properties`:

```yaml
mqtt:
  uris: ["tcp://localhost:1883"] # обязательное - массив URI брокеров
  username: "user"               # опционально
  password: "pass"               # опционально
  client-id: "custom-client-id"  # опционально (по умолчанию: случайный UUID)
  clean-session: true            # опционально (по умолчанию: true)
  connection-timeout: 30         # опционально (по умолчанию: 30 секунд)
  default-publish-topic: "topic/default" # опционально, топик по умолчанию для публикации
  subscription-topics: ["topic/1", "topic/2"] # опционально, топики для подписки
  subscription-qos: [1, 2]       # опционально, уровни QoS для подписанных топиков
```

## Использование

### Отправка сообщений

Внедрите `MqttGateway` в ваш сервис/контроллер:

```java
@RestController
@RequiredArgsConstructor
public class MessageController {
    private final MqttGateway mqttGateway;
    
    @PostMapping("/send")
    public String send(@RequestBody String message) {
        mqttGateway.send(message); // отправка в топик по умолчанию
        return "Сообщение отправлено";
    }
    
    @PostMapping("/send-to-topic")
    public String sendToTopic(@RequestParam String topic, 
                             @RequestBody String message) {
        mqttGateway.sendToTopic(topic, message);
        return "Сообщение отправлено в топик: " + topic;
    }
}
```

### Прием сообщений

Создайте сервис для обработки входящих сообщений:

```java
@Slf4j
@Service
public class MqttListenerService {
    @ServiceActivator(inputChannel = "mqttInboundChannel")
    public void handleMessage(
            @Header(MqttHeaders.RECEIVED_TOPIC) String topic, 
            String payload) {
        log.info("Получено сообщение в топике {}: {}", topic, payload);
        // Добавьте вашу логику обработки сообщения здесь
    }
}
```

## Справочник API

### Методы MqttGateway

- `void send(String payload)` — Отправить сообщение в топик по умолчанию
- `void sendToTopic(String topic, String payload)` — Отправить сообщение в указанный топик
- `void sendToTopic(String topic, int qos, String payload)` — Отправить сообщение в указанный топик с уровнем QoS

### Свойства конфигурации

| Свойство | Тип | По умолчанию | Описание |
|----------|------|---------|-------------|
| mqtt.uris | String[] | - | URI брокеров MQTT (обязательно) |
| mqtt.username | String | - | Логин для аутентификации |
| mqtt.password | String | - | Пароль для аутентификации |
| mqtt.client-id | String | случайный UUID | Идентификатор клиента |
| mqtt.clean-session | boolean | true | Флаг очистки сессии |
| mqtt.connection-timeout | int | 30 | Таймаут подключения в секундах |
| mqtt.default-publish-topic | String | - | Топик по умолчанию для публикации |
| mqtt.subscription-topics | String[] | [] | Топики для подписки |
| mqtt.subscription-qos | int[] | [] | Уровни QoS для подписанных топиков |

## Пример приложения

Смотрите модуль `example-app` для полного рабочего примера.

## Требования

- Java 21+
- Spring Boot 3.4.1
- MQTT брокер (например, Mosquitto, EMQX)