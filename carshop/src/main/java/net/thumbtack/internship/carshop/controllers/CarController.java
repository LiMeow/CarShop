package net.thumbtack.internship.carshop.controllers;

import net.thumbtack.internship.carshop.models.Car;
import net.thumbtack.internship.carshop.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class CarController {
    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/")
    public String homePage(Model model) {
        List<Car> cars = carService.getCars();
        model.addAttribute("cars", cars);
        return "index";
    }

    @GetMapping(value = "/buy/{id}")
    public String buyCar(@PathVariable("id") int carId, Model model) {
        return "redirect:/";
    }
}
