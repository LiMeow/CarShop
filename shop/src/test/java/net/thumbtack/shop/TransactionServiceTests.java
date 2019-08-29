package net.thumbtack.shop;

import net.thumbtack.shop.exceptions.CarShopException;
import net.thumbtack.shop.exceptions.ErrorCode;
import net.thumbtack.shop.models.*;
import net.thumbtack.shop.repositories.*;
import net.thumbtack.shop.requests.EditTransactionStatusDescriptionRequest;
import net.thumbtack.shop.requests.PickUpTransactionRequest;
import net.thumbtack.shop.services.TransactionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTests {
    @Mock
    private CarRepository carRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private TransactionStatusRepository statusRepository;
    @InjectMocks
    private TransactionService transactionService;

    @Test
    public void testPickUpTransaction() {
        Car car = new Car(1, "picture.jpg", "Audi A8", 2700000, 2017, true);
        Customer customer = new Customer(1, "Name", "+12345678912");
        User manager = new User(1, "manager", "password", UserRole.ROLE_MANAGER);
        Transaction transaction = new Transaction(1, car, customer);
        Transaction updatedTransaction = new Transaction(1, car, customer, manager);
        PickUpTransactionRequest request = new PickUpTransactionRequest(manager.getUsername(), transaction.getId());

        when(userRepository.findManagerByUsername(manager.getUsername())).thenReturn(manager);
        when(transactionRepository.findById(1)).thenReturn(java.util.Optional.of(transaction));
        when(transactionRepository.save(transaction)).thenReturn(transaction);

        assertEquals(updatedTransaction, transactionService.pickUpTransaction(request));

        verify(userRepository).findManagerByUsername(manager.getUsername());
        verify(transactionRepository).findById(1);
        verify(transactionRepository).save(updatedTransaction);
    }

    @Test
    public void testPickUpNonExistingTransaction() {
        User manager = new User(1, "manager", "password", UserRole.ROLE_MANAGER);
        PickUpTransactionRequest request = new PickUpTransactionRequest(manager.getUsername(), 1);

        when(userRepository.findManagerByUsername(manager.getUsername())).thenReturn(manager);
        when(transactionRepository.findById(1)).thenReturn(java.util.Optional.empty());
        try {
            transactionService.pickUpTransaction(request);
            fail();
        } catch (CarShopException ex) {
            assertEquals(ErrorCode.TRANSACTION_NOT_EXISTS, ex.getErrorCode());
        }
        verify(userRepository).findManagerByUsername(manager.getUsername());
        verify(transactionRepository).findById(1);
    }

    @Test
    public void testAddNextTransactionStatus() {
        Car car = new Car(1, "picture.jpg", "Audi A8", 2700000, 2017, true);
        Customer customer = new Customer(1, "Name", "+12345678912");
        User manager = new User(1, "manager", "password", UserRole.ROLE_MANAGER);

        Transaction transaction = new Transaction(1, car, customer, manager);
        TransactionStatus transactionStatus1 = new TransactionStatus(StatusName.APPLICATION_CONFIRMATION, transaction);
        TransactionStatus transactionStatus2 = new TransactionStatus(StatusName.CONFIRMED, transaction);

        List<TransactionStatus> transactionStatuses = Collections.singletonList(transactionStatus1);

        when(transactionRepository.findById(1)).thenReturn(Optional.of(transaction));
        when(statusRepository.save(transactionStatus2)).thenReturn(transactionStatus2);
        when(statusRepository.findAllByTransactionId(1, Sort.by("date"))).thenReturn(transactionStatuses);

        assertEquals(transactionStatuses, transactionService.addNextTransactionStatus(transaction.getId()));

        verify(transactionRepository).findById(1);
        verify(statusRepository).save(transactionStatus2);
        verify(statusRepository, times(2)).findAllByTransactionId(transaction.getId(), Sort.by("date"));
    }

    @Test
    public void testAddNextStatusToNonExistingTransaction() {
        when(transactionRepository.findById(1)).thenReturn(Optional.empty());

        try {
            transactionService.addNextTransactionStatus(1);
            fail();
        } catch (CarShopException ex) {
            assertEquals(ErrorCode.TRANSACTION_NOT_EXISTS, ex.getErrorCode());
        }
        verify(transactionRepository).findById(1);
    }

    @Test
    public void testAddContractPreparationStatusToTransaction() {
        Car car = new Car(1, "picture.jpg", "Audi A8", 2700000, 2017, true);
        Car updatedCar = new Car(1, "picture.jpg", "Audi A8", 2700000, 2017, false);
        Customer customer = new Customer(1, "Name", "+12345678912");
        User manager = new User(1, "manager", "password", UserRole.ROLE_MANAGER);

        Transaction transaction = new Transaction(1, car, customer);
        TransactionStatus transactionStatus1 = new TransactionStatus(StatusName.APPLICATION_CONFIRMATION, transaction);
        TransactionStatus transactionStatus2 = new TransactionStatus(StatusName.CONFIRMED, transaction);
        TransactionStatus transactionStatus3 = new TransactionStatus(StatusName.TEST_DRIVE, transaction);
        TransactionStatus transactionStatus4 = new TransactionStatus(StatusName.CONTRACT_PREPARATION, transaction);

        List<TransactionStatus> transactionStatuses = Arrays.asList(transactionStatus1, transactionStatus2, transactionStatus3);

        when(transactionRepository.findById(1)).thenReturn(Optional.of(transaction));
        when(carRepository.save(updatedCar)).thenReturn(updatedCar);
        when(statusRepository.save(transactionStatus4)).thenReturn(transactionStatus4);
        when(statusRepository.findAllByTransactionId(1, Sort.by("date"))).thenReturn(transactionStatuses);

        assertEquals(transactionStatuses, transactionService.addNextTransactionStatus(transaction.getId()));

        verify(transactionRepository).findById(1);
        verify(carRepository).save(updatedCar);
        verify(statusRepository).save(transactionStatus4);
        verify(statusRepository, times(2)).findAllByTransactionId(transaction.getId(), Sort.by("date"));
    }

    @Test
    public void testEditTransactionStatusDescription() {
        EditTransactionStatusDescriptionRequest request = new EditTransactionStatusDescriptionRequest("New Description");

        Car car = new Car(1, "picture.jpg", "Audi A8", 2700000, 2017, true);
        Customer customer = new Customer(1, "Name", "+12345678912");

        Transaction transaction = new Transaction(1, car, customer);
        TransactionStatus status = new TransactionStatus(StatusName.APPLICATION_CONFIRMATION, transaction);
        TransactionStatus updatedStatus = new TransactionStatus(0, status.getDate(), status.getStatusName(), request.getDescription(), transaction);

        when(transactionRepository.findById(transaction.getId())).thenReturn(Optional.of(transaction));
        when(statusRepository.findById(status.getId())).thenReturn(Optional.of(updatedStatus));
        when(statusRepository.save(updatedStatus)).thenReturn(updatedStatus);

        assertEquals(updatedStatus, transactionService.editTransactionStatusDescription(transaction.getId(), status.getId(), request));

        verify(transactionRepository).findById(transaction.getId());
        verify(statusRepository).findById(status.getId());
        verify(statusRepository).save(updatedStatus);
    }

    @Test
    public void testEditStatusDescriptionToNonExistingTransaction() {
        EditTransactionStatusDescriptionRequest request = new EditTransactionStatusDescriptionRequest("New Description");

        when(transactionRepository.findById(1)).thenReturn(Optional.empty());
        try {
            transactionService.editTransactionStatusDescription(1, 1, request);
            fail();
        } catch (CarShopException ex) {
            assertEquals(ErrorCode.TRANSACTION_NOT_EXISTS, ex.getErrorCode());
        }
        verify(transactionRepository).findById(1);
    }

    @Test
    public void testEditDescriptionToNonExistingStatus() {
        EditTransactionStatusDescriptionRequest request = new EditTransactionStatusDescriptionRequest("New Description");
        Car car = new Car(1, "picture.jpg", "Audi A8", 2700000, 2017, true);
        Customer customer = new Customer(1, "Name", "+12345678912");
        Transaction transaction = new Transaction(1, car, customer);

        when(transactionRepository.findById(transaction.getId())).thenReturn(Optional.of(transaction));
        when(statusRepository.findById(1)).thenReturn(Optional.empty());

        try {
            transactionService.editTransactionStatusDescription(1, 1, request);
            fail();
        } catch (CarShopException ex) {
            assertEquals(ErrorCode.TRANSACTION_STATUS_NOT_EXISTS, ex.getErrorCode());
        }
        verify(transactionRepository).findById(transaction.getId());
        verify(statusRepository).findById(1);
    }

    @Test
    public void getTransactionById() {
        Car car = new Car(1, "picture.jpg", "Audi A8", 2700000, 2017, true);
        Customer customer = new Customer(1, "Name", "+12345678912");
        User manager = new User(1, "manager", "password", UserRole.ROLE_MANAGER);
        Transaction transaction = new Transaction(1, car, customer, manager);

        when(transactionRepository.findById(1)).thenReturn(Optional.of(transaction));
        assertEquals(transaction, transactionService.getTransactionById(1));
        verify(transactionRepository).findById(1);
    }


    @Test
    public void testGetAllFreeTransactions() {
        Car car1 = new Car(1, "picture1.jpg", "Audi A8", 2700000, 2017, true);
        Customer customer1 = new Customer(1, "Name1", "+12345678911");
        Transaction transaction1 = new Transaction(1, car1, customer1);
        TransactionStatus transactionStatus1 = new TransactionStatus(1, StatusName.APPLICATION_CONFIRMATION, transaction1);

        Car car2 = new Car(2, "picture2.jpg", "Audi A9", 2800000, 2018, true);
        Customer customer2 = new Customer(2, "Name2", "+12345678912");
        Transaction transaction2 = new Transaction(2, car2, customer2);
        TransactionStatus transactionStatus2 = new TransactionStatus(2, StatusName.APPLICATION_CONFIRMATION, transaction2);

        List<TransactionStatus> freeTransactions = Arrays.asList(transactionStatus1, transactionStatus2);

        when(statusRepository.findAllFree(Sort.by("date"))).thenReturn(freeTransactions);
        assertEquals(freeTransactions, transactionService.getAllFreeTransactions());
        verify(statusRepository).findAllFree(Sort.by("date"));
    }

    @Test
    public void testGetAllTransactionByManager() {
        User manager = new User(1, "manager", "password", UserRole.ROLE_MANAGER);

        Car car1 = new Car(1, "picture1.jpg", "Audi A8", 2700000, 2017, true);
        Customer customer1 = new Customer(1, "Name1", "+12345678911");
        Transaction transaction1 = new Transaction(1, car1, customer1, manager);
        TransactionStatus transactionStatus1 = new TransactionStatus(1, StatusName.APPLICATION_CONFIRMATION, transaction1);

        Car car2 = new Car(2, "picture2.jpg", "Audi A9", 2800000, 2018, true);
        Customer customer2 = new Customer(2, "Name2", "+12345678912");
        Transaction transaction2 = new Transaction(2, car2, customer2, manager);
        TransactionStatus transactionStatus2 = new TransactionStatus(2, StatusName.CONFIRMED, transaction2);

        List<TransactionStatus> transactionsStatuses = Arrays.asList(transactionStatus1, transactionStatus2);

        when(userRepository.findManagerByUsername(manager.getUsername())).thenReturn(manager);
        when(statusRepository.findAllLastTransactionsStatusesByManager(manager.getId())).thenReturn(transactionsStatuses);

        assertEquals(transactionsStatuses, transactionService.getAllTransactionByManager(manager.getUsername()));

        verify(userRepository).findManagerByUsername(manager.getUsername());
        verify(statusRepository).findAllLastTransactionsStatusesByManager(manager.getId());
    }

    @Test
    public void testGetAllTransactionByNonExistingManager() {
        when(userRepository.findManagerByUsername("manager1")).thenReturn(null);

        try {
            transactionService.getAllTransactionByManager("manager1");
            fail();
        } catch (CarShopException ex) {
            assertEquals(ErrorCode.USER_NOT_EXISTS, ex.getErrorCode());
        }
        verify(userRepository).findManagerByUsername("manager1");

    }

    @Test
    public void testGetTransactionStatuses() {
        Car car = new Car(1, "picture.jpg", "Audi A8", 2700000, 2017, true);
        Customer customer = new Customer(1, "Name", "+12345678912");
        User manager = new User(1, "manager", "password", UserRole.ROLE_MANAGER);
        Transaction transaction = new Transaction(1, car, customer, manager);

        TransactionStatus status1 = new TransactionStatus(StatusName.APPLICATION_CONFIRMATION, transaction);
        TransactionStatus status2 = new TransactionStatus(StatusName.CONFIRMED, transaction);
        TransactionStatus status3 = new TransactionStatus(StatusName.TEST_DRIVE, transaction);
        List<TransactionStatus> transactionStatuses = Arrays.asList(status1, status2, status3);

        when(transactionRepository.findById(transaction.getId())).thenReturn(java.util.Optional.of(transaction));
        when(statusRepository.findAllByTransactionId(transaction.getId(), Sort.by("date"))).thenReturn(transactionStatuses);

        assertEquals(transactionStatuses, transactionService.getTransactionStatuses(transaction.getId()));

        verify(transactionRepository).findById(transaction.getId());
        verify(statusRepository).findAllByTransactionId(transaction.getId(), Sort.by("date"));

    }

    @Test
    public void testGetTransactionStatusesByCustomerName() {
        Car car = new Car(1, "picture.jpg", "Audi A8", 2700000, 2017, true);
        User user = new User(1, "user", "password", UserRole.ROLE_CUSTOMER);
        User manager = new User(1, "manager", "password", UserRole.ROLE_MANAGER);
        Customer customer = new Customer(1, user, "Name", "+12345678912");
        Transaction transaction = new Transaction(1, car, customer, manager);

        TransactionStatus status1 = new TransactionStatus(StatusName.APPLICATION_CONFIRMATION, transaction);
        TransactionStatus status2 = new TransactionStatus(StatusName.CONFIRMED, transaction);
        TransactionStatus status3 = new TransactionStatus(StatusName.TEST_DRIVE, transaction);
        List<TransactionStatus> transactionStatuses = Arrays.asList(status1, status2, status3);

        when(customerRepository.findCustomerByUsername(user.getUsername())).thenReturn(customer);
        when(transactionRepository.findByCustomerId(customer.getId())).thenReturn(transaction);
        when(statusRepository.findAllByTransactionId(transaction.getId(), Sort.by("date"))).thenReturn(transactionStatuses);

        assertEquals(transactionStatuses, transactionService.getTransactionStatusesByCustomer(user.getUsername()));

        verify(customerRepository).findCustomerByUsername(user.getUsername());
        verify(transactionRepository).findByCustomerId(customer.getId());
        verify(statusRepository).findAllByTransactionId(transaction.getId(), Sort.by("date"));

    }

    @Test
    public void testGetStatusesOfNonExistingTransaction() {
        when(transactionRepository.findById(1)).thenReturn(java.util.Optional.empty());

        try {
            transactionService.getTransactionStatuses(1);
            fail();
        } catch (CarShopException ex) {
            assertEquals(ErrorCode.TRANSACTION_NOT_EXISTS, ex.getErrorCode());
        }
        verify(transactionRepository).findById(1);
    }

}
