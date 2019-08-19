package net.thumbtack.internship.carshop.services;

import net.thumbtack.internship.carshop.exceptions.CarShopException;
import net.thumbtack.internship.carshop.exceptions.ErrorCode;
import net.thumbtack.internship.carshop.models.*;
import net.thumbtack.internship.carshop.repositories.CarRepository;
import net.thumbtack.internship.carshop.repositories.CustomerRepository;
import net.thumbtack.internship.carshop.repositories.TransactionRepository;
import net.thumbtack.internship.carshop.repositories.TransactionStatusRepository;
import net.thumbtack.internship.carshop.requests.CustomerRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionStatusRepository transactionStatusRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);


    @Autowired
    public CustomerService(CarRepository carRepository, CustomerRepository customerRepository, TransactionRepository transactionRepository, TransactionStatusRepository transactionStatusRepository) {
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
        this.transactionRepository = transactionRepository;
        this.transactionStatusRepository = transactionStatusRepository;
    }

    public TransactionStatus createTransaction(CustomerRequest request, int carId) {
        LOGGER.debug("CustomerService create transaction on car with id '{}'  by customer '{}'", carId, request);

        Car car = carRepository.findById(carId).orElse(null);
        if (car == null) {
            LOGGER.error("Unable to find car with id '{}'", carId);
            throw new CarShopException(ErrorCode.CAR_NOT_EXISTS, String.valueOf(carId));
        }

        Customer customer = new Customer(request.getName(), request.getPhone(), request.getEmail());
        customerRepository.save(customer);

        Transaction transaction = new Transaction(car, customer);
        transactionRepository.save(transaction);

        TransactionStatus transactionStatus = new TransactionStatus(StatusName.APPLICATION_CONFIRMATION, transaction);
        transactionStatusRepository.save(transactionStatus);
        return transactionStatus;
    }

}
