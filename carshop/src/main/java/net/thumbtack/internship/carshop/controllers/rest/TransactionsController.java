package net.thumbtack.internship.carshop.controllers.rest;

import net.thumbtack.internship.carshop.requests.AddTransactionStatusRequest;
import net.thumbtack.internship.carshop.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransactionsController {
    private final TransactionService transactionService;

    @Autowired
    public TransactionsController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping(path = "/transactions/free",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllFreeTransactions(@RequestParam(required = false, defaultValue = "0") int page,
                                                    @RequestParam(required = false, defaultValue = "8") int size) {
        return ResponseEntity.ok().body(transactionService.getAllFreeTransactions(page, size));
    }

    @PutMapping(value = "/{userId}/transactions/free/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> pickUpTransaction(@PathVariable("userId") int userId,
                                               @PathVariable("id") int transactionId) {
        return ResponseEntity.ok().body(transactionService.pickUpTransaction(userId, transactionId));
    }

    @GetMapping(value = "/{userId}/transactions",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllTransactionByManager(@PathVariable("userId") int userId,
                                                        @RequestParam(required = false, defaultValue = "0") int page,
                                                        @RequestParam(required = false, defaultValue = "8") int size) {

        return ResponseEntity.ok().body(transactionService.getAllTransactionByManager(userId, page, size));
    }

    @PostMapping(value = "/{userId}/transactions/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addTransactionStatus(AddTransactionStatusRequest request,
                                                  @PathVariable("userId") int userId,
                                                  @PathVariable("id") int transactionId) {
        return ResponseEntity.ok().body(transactionService.addTransactionStatus(request, userId, transactionId));
    }

    @GetMapping(value = "/{userId}/transactions/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTransactionStatuses(@PathVariable("userId") int userId,
                                                    @PathVariable("id") int transactionId) {
        return ResponseEntity.ok().body(transactionService.getTransactionStatuses(userId, transactionId));
    }



}
