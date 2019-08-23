package net.thumbtack.bank.controllers.rest;

import net.thumbtack.bank.requests.TestRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BankPingController {

    @GetMapping("/bank/ping")
    public ResponseEntity<?> ping() {

        return ResponseEntity.ok().body("ping");
    }

    @PostMapping("/test")
    public ResponseEntity<?> test(@RequestBody TestRequest request) {

        return ResponseEntity.ok().body(request.getUsername());
    }
}