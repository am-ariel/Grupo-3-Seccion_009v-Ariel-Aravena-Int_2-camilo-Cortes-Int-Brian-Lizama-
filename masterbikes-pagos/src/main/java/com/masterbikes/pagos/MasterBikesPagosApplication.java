package com.masterbikes.pagos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MasterBikesPagosApplication {

    public static void main(String[] args) {
        SpringApplication.run(MasterBikesPagosApplication.class, args);
    }
}
