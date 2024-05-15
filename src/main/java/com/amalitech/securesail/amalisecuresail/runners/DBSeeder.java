package com.amalitech.securesail.amalisecuresail.runners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DBSeeder implements CommandLineRunner {
    private final RoleSeeder roleSeeder;
    private final UserSeeder userSeeder;
    private final ClientSeeder clientSeeder;


    @Override
    public void run(String... args) {
        log.info("/////////////////////////////////////////////////////////////////////////////////////");
        log.info("seeding roles");
        roleSeeder.seedRoles();
//        sleep for 5 seconds
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error(e.getMessage());
        }
        userSeeder.seed();
        //        sleep for 5 seconds
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error(e.getMessage());
        }
        clientSeeder.seedClients();
    }
}