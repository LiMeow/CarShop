package net.thumbtack.internship.carshop.controllers.rest;

import net.thumbtack.internship.carshop.requests.CreateCarRequest;
import net.thumbtack.internship.carshop.requests.EditCarRequest;
import net.thumbtack.internship.carshop.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/cars")
public class CarRestController {
    private final CarService carService;

    @Autowired
    public CarRestController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createCar(@Valid @RequestBody CreateCarRequest request) {

        return ResponseEntity.ok().body(carService.createCar(request));
    }

    @PutMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> editCar(@RequestBody EditCarRequest request,
                                     @PathVariable("id") int carId) {

        return ResponseEntity.ok().body(carService.editCar(request, carId));
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllCars() {

        return ResponseEntity.ok().body(carService.getCars());
    }

    @DeleteMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteCar(@PathVariable("id") int carId) {
        carService.deleteCar(carId);

        return ResponseEntity.noContent().build();
    }



}
