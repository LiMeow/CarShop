package net.thumbtack.bank.controllers.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BankPingController {

    @GetMapping("/ping")
    public ResponseEntity<?> ping() {

        return ResponseEntity.ok().body("ping");
    }

}