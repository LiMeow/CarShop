package net.thumbtack.shop.controllers;

import net.thumbtack.shop.models.TransactionStatus;
import net.thumbtack.shop.requests.CustomerRequest;
import net.thumbtack.shop.responses.TransactionInfo;
import net.thumbtack.shop.services.CarService;
import net.thumbtack.shop.services.CustomerService;
import net.thumbtack.shop.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CustomerViewController {
    private final CarService carService;
    private final CustomerService customerService;
    private final TransactionService transactionService;

    @Autowired
    public CustomerViewController(CarService carService, CustomerService customerService, TransactionService transactionService) {
        this.carService = carService;
        this.customerService = customerService;
        this.transactionService = transactionService;
    }

    @GetMapping("/offers/{id}")
    public String customerContactsPage(@PathVariable("id") int carId, Model model) {
        model.addAttribute("car", carService.getCar(carId));
        return "customer";
    }

    @PostMapping("/create/transaction/{id}")
    public String addCustomerContacts(@ModelAttribute("request") CustomerRequest request,
                                      @PathVariable("id") int carId,
                                      Model model) {

        TransactionStatus transactionStatus = customerService.createTransaction(request, carId);
        model.addAttribute("customerId", transactionStatus.getTransaction().getCustomer().getId());

        return "customerRegistrationRequest";
    }

    @GetMapping("/transaction-story")
    public String transactionStoryPage(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        List<TransactionStatus> transactionStatuses = transactionService.getTransactionStatusesByCustomer(username);
        List<TransactionInfo> statuses = transactionService.getTransactionInfoList(transactionStatuses);

        model.addAttribute("transaction", transactionStatuses.get(0).getTransaction());
        model.addAttribute("car", transactionStatuses.get(0).getTransaction().getCar());
        model.addAttribute("statuses", statuses);
        return "transactionInfoForCustomer";
    }

    @GetMapping("/transactions/{transactionId}/pay")
    public String payPage(@PathVariable("transactionId") int transactionId, Model model) {
        model.addAttribute("transactionId", transactionId);
        model.addAttribute("price", transactionService.getTransactionById(transactionId).getCar().getPrice());

        return "inputCardData";
    }

    @GetMapping("/failure-operation")
    public String failureOperation() {
        return "onFailureOperation";
    }

    @GetMapping("/transactions/{transactionId}/pay-success")
    public String onSuccessOperation(@PathVariable("transactionId") int transactionId) {
        transactionService.addNextTransactionStatus(transactionId);
        return "onSuccessOperation";
    }

}
