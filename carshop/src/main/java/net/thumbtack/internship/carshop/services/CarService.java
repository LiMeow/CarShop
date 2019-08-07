package net.thumbtack.internship.carshop.services;

import net.thumbtack.internship.carshop.models.Car;
import net.thumbtack.internship.carshop.repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarService {
    private final CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> getCars() {
        return (List<Car>) carRepository.findAll();
    }

}
