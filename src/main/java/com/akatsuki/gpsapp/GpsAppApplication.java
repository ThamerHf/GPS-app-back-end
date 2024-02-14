package com.akatsuki.gpsapp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

@Slf4j
@SpringBootApplication
public class GpsAppApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(GpsAppApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        KeyGenerator gen  = KeyGenerator.getInstance("AES");
        gen.init(256);
        SecretKey secretKey = gen.generateKey();

        log.info("secret " + secretKey.toString());
    }
}
