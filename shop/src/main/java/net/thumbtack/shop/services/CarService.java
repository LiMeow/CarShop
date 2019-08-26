package net.thumbtack.shop.services;

import net.thumbtack.shop.exceptions.CarShopException;
import net.thumbtack.shop.exceptions.ErrorCode;
import net.thumbtack.shop.models.Car;
import net.thumbtack.shop.repositories.CarRepository;
import net.thumbtack.shop.requests.CreateCarRequest;
import net.thumbtack.shop.requests.UpdateCarRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {
    @Autowired
    private CarRepository carRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(CarService.class);


    public Car createCar(CreateCarRequest request) {
        LOGGER.debug("CarRepository create Car {}", request);

        Car car = new Car(request.getPicture(), request.getModel(), request.getPrice(), request.getProduction());
        carRepository.save(car);
        return car;
    }

    public Car updateCar(UpdateCarRequest request, int carId) {
        LOGGER.debug("CarRepository update Car with id '{}'", carId);
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
        LOGGER.debug("CarRepository get Car with id '{}'", carId);
        return findCarById(carId);
    }

    public List<Car> getCars() {
        LOGGER.debug("CarRepository get Cars");
        return (List<Car>) carRepository.findAll();
    }

    public void deleteCar(int carId) {
        LOGGER.debug("CarRepository delete Car with id '{}'", carId);
        findCarById(carId);
        carRepository.deleteById(carId);
    }

    private Car findCarById(int carId) {
        Car car = carRepository.findById(carId).orElse(null);

        if (car == null) {
            LOGGER.error("Unable to find car with id '{}'", carId);
            throw new CarShopException(ErrorCode.CAR_NOT_EXISTS, String.valueOf(carId));
        }
        return car;
    }

}
