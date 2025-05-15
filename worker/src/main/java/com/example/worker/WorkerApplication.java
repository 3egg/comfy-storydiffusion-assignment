package com.example.worker;

import com.example.worker.service.CustomWebSocketClientV1;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.URI;

@SpringBootApplication
public class WorkerApplication{

//    private final String clientId;
//
//    public WorkerApplication(@Value("${clientId}") String clientId) {
//        this.clientId = clientId;
//    }

    public static void main(String[] args) {
        SpringApplication.run(WorkerApplication.class, args);
    }

    /*@Override
    public void run(String... args) {
        CustomWebSocketClientV1 customWebSocketClientV1 = new CustomWebSocketClientV1(
                URI.create("ws://127.0.0.1:8000/ws?clientId=" + clientId)
        );
        customWebSocketClientV1.connect();
    }*/

}
