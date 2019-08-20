package net.thumbtack.internship.carshop.controllers.rest;

import net.thumbtack.internship.carshop.requests.TransactionStatusRequest;
import net.thumbtack.internship.carshop.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public ResponseEntity<?> addTransactionStatus(@Valid @RequestBody TransactionStatusRequest request,
                                                  @PathVariable("userId") int userId,
                                                  @PathVariable("id") int transactionId) {
        return ResponseEntity.ok().body(transactionService.addTransactionStatus(request, userId, transactionId));
    }

    @DeleteMapping(value = "/{userId}/transactions/{transactionId}/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteTransactionStatus(@PathVariable("userId") int userId,
                                                     @PathVariable("transactionId") int transactionId,
                                                     @PathVariable("id") int transactionStatusId) {
        transactionService.deleteTransactionStatus(userId, transactionId, transactionStatusId);

        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{userId}/transactions/{transactionId}/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> editTransactionStatus(@Valid @RequestBody TransactionStatusRequest request,
                                                   @PathVariable("userId") int userId,
                                                   @PathVariable("transactionId") int transactionId,
                                                   @PathVariable("id") int transactionStatusId) {
        return ResponseEntity.ok().body(transactionService.editTransactionStatus(request, userId, transactionId, transactionStatusId));
    }

    @GetMapping(value = "/{userId}/transactions/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTransactionStatuses(@PathVariable("userId") int userId,
                                                    @PathVariable("id") int transactionId) {
        return ResponseEntity.ok().body(transactionService.getTransactionStatuses(userId, transactionId));
    }



}
