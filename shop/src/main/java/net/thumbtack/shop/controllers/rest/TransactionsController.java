package net.thumbtack.shop.controllers.rest;

import net.thumbtack.shop.requests.AddTransactionStatusRequest;
import net.thumbtack.shop.requests.EditTransactionStatusDescriptionRequest;
import net.thumbtack.shop.services.TransactionService;
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

    @GetMapping(path = "/{username}/transactions/free",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllFreeTransactions(@PathVariable("username") String username,
                                                    @RequestParam(required = false, defaultValue = "0") int page,
                                                    @RequestParam(required = false, defaultValue = "8") int size) {
        return ResponseEntity.ok().body(transactionService.getAllFreeTransactions(username, page, size));
    }

    @PutMapping(value = "/{username}/transactions/free/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> pickUpTransaction(@PathVariable("username") String username,
                                               @PathVariable("id") int transactionId) {
        return ResponseEntity.ok().body(transactionService.pickUpTransaction(username, transactionId));
    }

    @GetMapping(value = "/{username}/transactions",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllTransactionByManager(@PathVariable("username") String username) {

        return ResponseEntity.ok().body(transactionService.getAllTransactionByManager(username));
    }

    @PostMapping(value = "/{username}/transactions/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addTransactionStatus(AddTransactionStatusRequest request,
                                                  @PathVariable("username") String username,
                                                  @PathVariable("id") int transactionId) {
        return ResponseEntity.ok().body(transactionService.addTransactionStatus(request, username, transactionId));
    }

    @PutMapping(value = "/{username}/transactions/{transactionId}/statuses/{statusId}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> editTransactionStatusDescription(@Valid @RequestBody EditTransactionStatusDescriptionRequest request,
                                                              @PathVariable("username") String username,
                                                              @PathVariable("transactionId") int transactionId,
                                                              @PathVariable("statusId") int statusId) {

        return ResponseEntity.ok().body(transactionService.editTransactionStatusDescription(username, transactionId, statusId, request));
    }

    @GetMapping(value = "/{username}/transactions/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTransactionStatuses(@PathVariable("username") String username,
                                                    @PathVariable("id") int transactionId) {
        return ResponseEntity.ok().body(transactionService.getTransactionStatuses(username, transactionId));
    }

}
