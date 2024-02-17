package com.akatsuki.gpsapp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Base64;

@Slf4j
@SpringBootApplication
public class GpsAppApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(GpsAppApplication.class, args);
    }


    //You can test some code: on application start this code will be executed automatically
    @Override
    public void run(String... args) throws Exception {
        //To generate a secret key for jws encryption, decryption
        /*
        KeyGenerator gen  = KeyGenerator.getInstance("AES");
        gen.init(256);
        SecretKey secretKey = gen.generateKey();

        log.info("secret " + Arrays.toString(new String[]{Base64.getEncoder().encodeToString(secretKey.getEncoded())}));*/
    }
}
