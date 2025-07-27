package ru.advantum.commons.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.annotation.IntegrationComponentScan;
import ru.advantum.commons.mqtt.service.MqttGateway;

@SpringBootApplication
@IntegrationComponentScan(basePackageClasses = MqttGateway.class)
public class ExampleAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExampleAppApplication.class, args);
    }

}
