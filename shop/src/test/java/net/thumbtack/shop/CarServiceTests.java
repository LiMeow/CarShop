package net.thumbtack.shop;

import net.thumbtack.shop.exceptions.CarShopException;
import net.thumbtack.shop.exceptions.ErrorCode;
import net.thumbtack.shop.models.Car;
import net.thumbtack.shop.repositories.CarRepository;
import net.thumbtack.shop.requests.CreateCarRequest;
import net.thumbtack.shop.requests.UpdateCarRequest;
import net.thumbtack.shop.services.CarService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class CarServiceTests {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarService carService = new CarService();

    @Test
    public void testCreateCar() {
        CreateCarRequest request = new CreateCarRequest("picture.jpg", "Audi A8", 2700000, 2017);
        Car car = new Car(0, "picture.jpg", "Audi A8", 2700000, 2017, true);

        when(carRepository.save(car)).thenReturn(car);
        assertEquals(car, carService.createCar(request));
        verify(carRepository).save(car);
    }

    @Test
    public void testGetCar() {
        Car car = new Car(1, "picture.jpg", "Audi A8", 2700000, 2017, true);

        when(carRepository.findById(1)).thenReturn(java.util.Optional.of(car));

        assertEquals(car, carService.getCar(1));
        verify(carRepository).findById(1);
    }

    @Test
    public void testGetNonExistingCar() {
        when(carRepository.findById(1)).thenReturn(java.util.Optional.empty());
        try {
            carService.getCar(1);
            fail();
        } catch (CarShopException ex) {
            assertEquals(ErrorCode.CAR_NOT_EXISTS, ex.getErrorCode());
        }
        verify(carRepository).findById(1);
    }

    @Test
    public void testGetCars() {
        Car car1 = new Car(1, "picture1.jpg", "Audi A8", 1000000, 2016, true);
        Car car2 = new Car(2, "picture2.jpg", "Audi A9", 2000000, 2017, true);
        Car car3 = new Car(3, "picture3.jpg", "Audi A10", 3000000, 2018, true);

        List<Car> cars = Arrays.asList(car1, car2, car3);
        when(carRepository.findAll()).thenReturn(cars);

        assertEquals(cars, carService.getCars());
        verify(carRepository).findAll();
    }

    @Test
    public void testUpdateCar() {
        Car car = new Car(1, "picture.jpg", "Audi A8", 2700000, 2017, true);
        Car updatedCar = new Car(1, "picture1.jpg", "BMW", 1200000, 2018, true);
        UpdateCarRequest request = new UpdateCarRequest("picture1.jpg", "BMW", 1200000, 2018, true);

        when(carRepository.findById(1)).thenReturn(java.util.Optional.of(car));
        when(carRepository.save(updatedCar)).thenReturn(updatedCar);

        assertEquals(updatedCar, carService.updateCar(request, 1));
        verify(carRepository).findById(1);
        verify(carRepository).save(updatedCar);
    }

    @Test
    public void testUpdateCarByEmptyRequest() {
        Car car = new Car(1, "picture.jpg", "Audi A8", 2700000, 2017, true);

        when(carRepository.findById(1)).thenReturn(java.util.Optional.of(car));
        assertEquals(car, carService.updateCar(new UpdateCarRequest(), 1));
        verify(carRepository).findById(1);
    }

    @Test
    public void testUpdateNonExistingCar() {
        UpdateCarRequest request = new UpdateCarRequest("picture1.jpg", "BMW", 1200000, 2018, true);

        when(carRepository.findById(1)).thenReturn(java.util.Optional.empty());
        try {
            carService.updateCar(request, 1);
        } catch (CarShopException ex) {
            assertEquals(ErrorCode.CAR_NOT_EXISTS, ex.getErrorCode());
        }
        verify(carRepository).findById(1);
    }

    @Test
    public void testDeleteCar() {
        Car car = new Car(1, "picture.jpg", "Audi A8", 2700000, 2017, true);

        when(carRepository.findById(1)).thenReturn(java.util.Optional.of(car));
        carService.deleteCar(1);
        verify(carRepository).deleteById(1);
    }

    @Test
    public void testDeleteNonExistingCar() {
        when(carRepository.findById(1)).thenReturn(java.util.Optional.empty());
        try {
            carService.deleteCar(1);
        } catch (CarShopException ex) {
            assertEquals(ErrorCode.CAR_NOT_EXISTS, ex.getErrorCode());
        }
        verify(carRepository).findById(1);
    }
}
