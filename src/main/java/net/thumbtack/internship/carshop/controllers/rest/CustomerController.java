package net.thumbtack.internship.carshop.controllers.rest;

import net.thumbtack.internship.carshop.requests.CustomerRequest;
import net.thumbtack.internship.carshop.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/offers")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createTransaction(@Valid @RequestBody CustomerRequest request,
                                               @PathVariable("id") int carId) {

        return ResponseEntity.ok().body(customerService.createTransaction(request, carId));
    }


}
