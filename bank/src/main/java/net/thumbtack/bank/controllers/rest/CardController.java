package net.thumbtack.bank.controllers.rest;

import net.thumbtack.bank.requests.CreateCardRequest;
import net.thumbtack.bank.requests.DeleteCardRequest;
import net.thumbtack.bank.requests.PutMoneyRequest;
import net.thumbtack.bank.requests.TakeMoneyRequest;
import net.thumbtack.bank.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/cards")
public class CardController {
    private final CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping(value = "/create",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createCard(@Valid @RequestBody CreateCardRequest request) {

        return ResponseEntity.ok().body(cardService.createCard(request));
    }

    @GetMapping(value = "/{id}/info",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCard(@PathVariable("id") int cardId) {

        return ResponseEntity.ok().body(cardService.getCard(cardId));
    }

    @GetMapping(value = "/info",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllCard() {

        return ResponseEntity.ok().body(cardService.getAllCards());
    }

    @PutMapping(value = "/put-money",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> putMoney(@Valid @RequestBody PutMoneyRequest request) {

        return ResponseEntity.ok().body(cardService.putMoney(request));
    }

    @PostMapping(value = "/take-money")
    public ResponseEntity<?> takeMoney(TakeMoneyRequest request) {

        return ResponseEntity.ok().body(cardService.takeMoney(request));
    }

    @DeleteMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteCard(@Valid @RequestBody DeleteCardRequest request) {

        cardService.deleteCard(request);
        return ResponseEntity.noContent().build();
    }


}
