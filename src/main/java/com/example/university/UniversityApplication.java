package com.example.university;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
public class UniversityApplication {
    private static final String BROKER = "tcp://localhost:1883"; // Địa chỉ broker
    private static final String TOPIC = "test/topic"; // Topic để nhận message
    private static final String CLIENT_ID = "JavaServer-" + UUID.randomUUID().toString();

    public static void main(String[] args) {
        SpringApplication.run(UniversityApplication.class, args);
//        try {
//            // Tạo client MQTT
//            MqttClient client = new MqttClient(BROKER, CLIENT_ID, new MemoryPersistence());
//
//            // Cấu hình options kết nối
//            MqttConnectOptions connOpts = new MqttConnectOptions();
//            connOpts.setCleanSession(true);
//
//            // Callback để xử lý các sự kiện
//            client.setCallback(new MqttCallback() {
//                @Override
//                public void connectionLost(Throwable cause) {
//                    System.out.println("Connection lost: " + cause.getMessage());
//                }
//
//                @Override
//                public void messageArrived(String topic, MqttMessage message) throws Exception {
//                    // Khi nhận được message
//                    System.out.println("Received message on topic '" + topic + "': " + new String(message.getPayload()));
//                }
//
//                @Override
//                public void deliveryComplete(IMqttDeliveryToken token) {
//                    // Không dùng trong trường hợp này
//                }
//            });
//
//            // Kết nối tới broker
//            System.out.println("Connecting to broker: " + BROKER);
//            client.connect(connOpts);
//            System.out.println("Connected");
//
//            // Subscribe vào topic
//            client.subscribe(TOPIC);
//            System.out.println("Subscribed to topic: " + TOPIC);
//
//            // Giữ chương trình chạy để tiếp tục nhận message
//            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
//                try {
//                    if (client.isConnected()) {
//                        client.disconnect();
//                        System.out.println("Disconnected from broker");
//                    }
//                } catch (MqttException e) {
//                    e.printStackTrace();
//                }
//            }));
//
//        } catch (MqttException e) {
//            System.err.println("Error: " + e.getMessage());
//            e.printStackTrace();
//        }

    }

}
