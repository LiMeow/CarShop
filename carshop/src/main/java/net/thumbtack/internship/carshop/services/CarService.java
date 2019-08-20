package net.thumbtack.internship.carshop.services;

import net.thumbtack.internship.carshop.exceptions.CarShopException;
import net.thumbtack.internship.carshop.exceptions.ErrorCode;
import net.thumbtack.internship.carshop.models.Car;
import net.thumbtack.internship.carshop.repositories.CarRepository;
import net.thumbtack.internship.carshop.requests.CreateCarRequest;
import net.thumbtack.internship.carshop.requests.UpdateCarRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {
    @Autowired
    private CarRepository carRepository;

    public Car createCar(CreateCarRequest request) {
        Car car = new Car(request.getPicture(), request.getModel(), request.getPrice(), request.getProduction());
        carRepository.save(car);
        return car;
    }

    public Car updateCar(UpdateCarRequest request, int carId) {
        Car car = findCarById(carId);

        if (request.getPicture() != null && !request.getPicture().isEmpty())
            car.setPicture(request.getPicture());

        if (request.getModel() != null && !request.getModel().isEmpty())
            car.setModel(request.getModel());

        if (request.getPrice() != 0)
            car.setPrice(request.getPrice());

        if (request.getProduction() != 0)
            car.setProduction(request.getProduction());

        if (request.getAvailable() != null)
            car.setAvailable(request.getAvailable());

        carRepository.save(car);
        return car;
    }

    public Car getCar(int carId) {
        return findCarById(carId);
    }

    public List<Car> getCars() {
        return (List<Car>) carRepository.findAll();
    }

    public void deleteCar(int carId) {
        findCarById(carId);
        carRepository.deleteById(carId);
    }

    private Car findCarById(int carId) {
        Car car = carRepository.findById(carId).orElse(null);

        if (car == null)
            throw new CarShopException(ErrorCode.CAR_NOT_EXISTS, String.valueOf(carId));

        return car;
    }

}
