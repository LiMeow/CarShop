package net.thumbtack.internship.carshop.controllers;

import net.thumbtack.internship.carshop.requests.CustomerRequest;
import net.thumbtack.internship.carshop.services.CarService;
import net.thumbtack.internship.carshop.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CustomerViewController {
    private final CarService carService;
    private final CustomerService customerService;

    @Autowired
    public CustomerViewController(CarService carService, CustomerService customerService) {
        this.carService = carService;
        this.customerService = customerService;
    }

    @GetMapping("/offers/{id}")
    public String customerContactsPage(@PathVariable("id") int carId, Model model) {
        model.addAttribute("car", carService.getCar(carId));
        return "customer";
    }

    @GetMapping("/back")
    public String backHome() {
        return "redirect:/";
    }

    @PostMapping("/create/transaction/{id}")
    public String addCustomerContacts(@PathVariable("id") int carId,
                                      @ModelAttribute("request") CustomerRequest request,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "redirect:/offer/{id}";
        customerService.createTransaction(request, carId);
        return "redirect:/";
    }
}
