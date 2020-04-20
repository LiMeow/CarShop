package net.thumbtack.shop;

import net.thumbtack.shop.exceptions.CarShopException;
import net.thumbtack.shop.exceptions.ErrorCode;
import net.thumbtack.shop.models.*;
import net.thumbtack.shop.repositories.CarRepository;
import net.thumbtack.shop.repositories.CustomerRepository;
import net.thumbtack.shop.repositories.TransactionRepository;
import net.thumbtack.shop.repositories.TransactionStatusRepository;
import net.thumbtack.shop.requests.CustomerRequest;
import net.thumbtack.shop.services.CustomerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTests {
    @Mock
    private CarRepository carRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private TransactionStatusRepository statusRepository;
    @InjectMocks
    private CustomerService customerService;

    @Test
    public void testCreateTransaction() {
        Car car = new Car(1, "picture.jpg", "Audi A8", 2700000, 2017, true);
        CustomerRequest request = new CustomerRequest("Name", "+12345678912");
        Customer customer = new Customer(0, "Name", "+12345678912");
        Transaction transaction = new Transaction(0, car, customer);
        TransactionStatus status = new TransactionStatus(StatusName.APPLICATION_CONFIRMATION, transaction);

        when(carRepository.findById(1)).thenReturn(java.util.Optional.of(car));
        when(customerRepository.save(customer)).thenReturn(customer);
        when(transactionRepository.save(transaction)).thenReturn(transaction);
        when(statusRepository.save(status)).thenReturn(status);

        assertEquals(status, customerService.createTransaction(request, 1));

        verify(carRepository).findById(1);
        verify(customerRepository).save(customer);
        verify(transactionRepository).save(transaction);
        verify(statusRepository).save(status);
    }

    @Test
    public void testCreateTransactionForNonExistingCar() {
        CustomerRequest request = new CustomerRequest("Name", "+12345678912");

        when(carRepository.findById(1)).thenReturn(java.util.Optional.empty());
        try {
            customerService.createTransaction(request, 1);
        } catch (CarShopException ex) {
            assertEquals(ErrorCode.CAR_NOT_EXISTS, ex.getErrorCode());
        }
        verify(carRepository).findById(1);
    }

}
