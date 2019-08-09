package net.thumbtack.internship.carshop.controllers;

import net.thumbtack.internship.carshop.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeViewController {
    private final CarService carService;

    @Autowired
    public HomeViewController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("cars", carService.getCars());
        return "index";
    }

}
