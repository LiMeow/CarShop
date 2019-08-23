package net.thumbtack.shop.controllers.rest;

import net.thumbtack.shop.requests.CreateCarRequest;
import net.thumbtack.shop.requests.UpdateCarRequest;
import net.thumbtack.shop.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/cars")

public class CarController {
    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
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
    public ResponseEntity<?> updateCar(@RequestBody UpdateCarRequest request,
                                       @PathVariable("id") int carId) {

        return ResponseEntity.ok().body(carService.updateCar(request, carId));
    }

    @GetMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCar(@PathVariable("id") int carId) {

        return ResponseEntity.ok().body(carService.getCar(carId));
    }


    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllCars() {

        return ResponseEntity.ok().body(carService.getAvailableCars());
    }

    @DeleteMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteCar(@PathVariable("id") int carId) {
        carService.deleteCar(carId);

        return ResponseEntity.noContent().build();
    }


}
