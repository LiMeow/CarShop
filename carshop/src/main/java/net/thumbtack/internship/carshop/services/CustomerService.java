package net.thumbtack.internship.carshop.services;

import net.thumbtack.internship.carshop.exceptions.CarShopException;
import net.thumbtack.internship.carshop.exceptions.ErrorCode;
import net.thumbtack.internship.carshop.models.Car;
import net.thumbtack.internship.carshop.models.Transaction;
import net.thumbtack.internship.carshop.models.Customer;
import net.thumbtack.internship.carshop.repositories.CarRepository;
import net.thumbtack.internship.carshop.repositories.CustomerRepository;
import net.thumbtack.internship.carshop.repositories.TransactionRepository;
import net.thumbtack.internship.carshop.requests.CustomerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;

    @Autowired
    public CustomerService(CarRepository carRepository, CustomerRepository customerRepository, TransactionRepository transactionRepository) {
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
        this.transactionRepository = transactionRepository;
    }

    public Transaction createTransaction(CustomerRequest request, int carId) {
        Car car = carRepository.findById(carId).orElse(null);
        if (car == null)
            throw new CarShopException(ErrorCode.CAR_NOT_EXISTS, String.valueOf(carId));

        Customer customer = new Customer(request.getName(), request.getPhone(), request.getEmail());
        customerRepository.save(customer);

        Transaction transaction = new Transaction(car, customer);
        transactionRepository.save(transaction);

        return transaction;
    }

}
