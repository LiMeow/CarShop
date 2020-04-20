package net.thumbtack.shop.controllers.rest;

import net.thumbtack.shop.requests.EditTransactionStatusDescriptionRequest;
import net.thumbtack.shop.requests.PickUpTransactionRequest;
import net.thumbtack.shop.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/transactions")
public class TransactionsController {
    private final TransactionService transactionService;

    @Autowired
    public TransactionsController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping(path = "/free")
    public ResponseEntity<?> getAllFreeTransactions() {
        return ResponseEntity.ok().body(transactionService.getAllFreeTransactions());
    }

    @PutMapping(value = "/free",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> pickUpTransaction(@Valid @RequestBody PickUpTransactionRequest request) {
        return ResponseEntity.ok().body(transactionService.pickUpTransaction(request));
    }

    @GetMapping
    public ResponseEntity<?> getAllTransactionByManager(@RequestParam String username) {
        System.out.println(username);
        return ResponseEntity.ok().body(transactionService.getAllTransactionByManager(username));
    }

    @PostMapping(value = "/{id}")
    public ResponseEntity<?> addNextTransactionStatus(@PathVariable("id") int transactionId) {
        return ResponseEntity.ok().body(transactionService.addNextTransactionStatus(transactionId));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> rejectTransaction(@PathVariable("id") int transactionId) {
        return ResponseEntity.ok().body(transactionService.rejectTransaction(transactionId));
    }

    @PutMapping(value = "/{transactionId}/statuses/{statusId}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> editTransactionStatusDescription(@Valid @RequestBody EditTransactionStatusDescriptionRequest request,
                                                              @PathVariable("transactionId") int transactionId,
                                                              @PathVariable("statusId") int statusId) {

        return ResponseEntity.ok().body(transactionService.editTransactionStatusDescription(transactionId, statusId, request));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getTransactionStatuses(@PathVariable("id") int transactionId) {

        return ResponseEntity.ok().body(transactionService.getTransactionStatuses(transactionId));
    }

}
