package net.thumbtack.internship.carshop;

import net.thumbtack.internship.carshop.exceptions.CarShopException;
import net.thumbtack.internship.carshop.exceptions.ErrorCode;
import net.thumbtack.internship.carshop.models.*;
import net.thumbtack.internship.carshop.repositories.ManagerRepository;
import net.thumbtack.internship.carshop.repositories.TransactionRepository;
import net.thumbtack.internship.carshop.repositories.TransactionStatusRepository;
import net.thumbtack.internship.carshop.requests.AddTransactionStatusRequest;
import net.thumbtack.internship.carshop.services.TransactionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTests {
    @Mock
    private ManagerRepository managerRepository;
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
        Manager manager = new Manager(1, "manager", "password");
        Transaction transaction = new Transaction(1, car, customer);
        Transaction updatedTransaction = new Transaction(1, car, customer, manager);

        when(managerRepository.findByUsername(manager.getUsername())).thenReturn(manager);
        when(transactionRepository.findById(1)).thenReturn(java.util.Optional.of(transaction));
        when(transactionRepository.save(transaction)).thenReturn(transaction);

        assertEquals(updatedTransaction, transactionService.pickUpTransaction(manager.getUsername(), 1));

        verify(managerRepository).findByUsername(manager.getUsername());
        verify(transactionRepository).findById(1);
        verify(transactionRepository).save(updatedTransaction);
    }

    @Test
    public void testPickUpTransactionByNonExistingManager() {
        when(managerRepository.findByUsername("manager1")).thenReturn(null);
        try {
            transactionService.pickUpTransaction("manager1", 1);
            fail();
        } catch (CarShopException ex) {
            assertEquals(ErrorCode.USER_NOT_EXISTS, ex.getErrorCode());
        }
        verify(managerRepository).findByUsername("manager1");
    }

    @Test
    public void testPickUpNonExistingTransaction() {
        Manager manager = new Manager(1, "manager", "password");

        when(managerRepository.findByUsername(manager.getUsername())).thenReturn(manager);
        when(transactionRepository.findById(1)).thenReturn(java.util.Optional.empty());
        try {
            transactionService.pickUpTransaction(manager.getUsername(), 1);
            fail();
        } catch (CarShopException ex) {
            assertEquals(ErrorCode.TRANSACTION_NOT_EXISTS, ex.getErrorCode());
        }
        verify(managerRepository).findByUsername(manager.getUsername());
        verify(transactionRepository).findById(1);
    }

    @Test
    public void testAddTransactionStatus() {
        AddTransactionStatusRequest request = new AddTransactionStatusRequest(StatusName.REJECTED);

        Car car = new Car(1, "picture.jpg", "Audi A8", 2700000, 2017, true);
        Customer customer = new Customer(1, "Name", "+12345678912");
        Manager manager = new Manager(1, "manager", "password");

        Transaction transaction = new Transaction(1, car, customer);
        TransactionStatus transactionStatus = new TransactionStatus(request.getStatusName(), transaction);
        List<TransactionStatus> transactionStatuses = Collections.singletonList(transactionStatus);

        when(managerRepository.findByUsername(manager.getUsername())).thenReturn(manager);
        when(transactionRepository.findById(1)).thenReturn(java.util.Optional.of(transaction));
        when(statusRepository.save(transactionStatus)).thenReturn(transactionStatus);
        when(statusRepository.findAllByTransactionId(1, Sort.by("date"))).thenReturn(transactionStatuses);

        assertEquals(transactionStatuses, transactionService.addTransactionStatus(request, manager.getUsername(), 1));

        verify(managerRepository).findByUsername(manager.getUsername());
        verify(transactionRepository).findById(1);
        verify(statusRepository).save(transactionStatus);
    }

    @Test
    public void testAddTransactionStatusByNonExistingManager() {
        AddTransactionStatusRequest request = new AddTransactionStatusRequest(StatusName.REJECTED);

        when(managerRepository.findByUsername("manager1")).thenReturn(null);
        try {
            transactionService.addTransactionStatus(request, "manager1", 1);
            fail();
        } catch (CarShopException ex) {
            assertEquals(ErrorCode.USER_NOT_EXISTS, ex.getErrorCode());
        }
        verify(managerRepository).findByUsername("manager1");

    }

    @Test
    public void testAddStatusToNonExistingTransaction() {
        AddTransactionStatusRequest request = new AddTransactionStatusRequest(StatusName.REJECTED);
        Manager manager = new Manager(1, "manager", "password");

        when(managerRepository.findByUsername(manager.getUsername())).thenReturn(manager);
        when(transactionRepository.findById(1)).thenReturn(java.util.Optional.empty());

        try {
            transactionService.addTransactionStatus(request, manager.getUsername(), 1);
            fail();
        } catch (CarShopException ex) {
            assertEquals(ErrorCode.TRANSACTION_NOT_EXISTS, ex.getErrorCode());
        }
        verify(managerRepository).findByUsername(manager.getUsername());
        verify(transactionRepository).findById(1);
    }

    @Test
    public void testGetAllFreeTransactions() {
        int page = 0;
        int size = 3;
        Pageable pageable = PageRequest.of(page, size, Sort.by("date"));
        Manager manager = new Manager(1, "manager", "password");

        Car car1 = new Car(1, "picture1.jpg", "Audi A8", 2700000, 2017, true);
        Customer customer1 = new Customer(1, "Name1", "+12345678911");
        Transaction transaction1 = new Transaction(1, car1, customer1);
        TransactionStatus transactionStatus1 = new TransactionStatus(1, StatusName.APPLICATION_CONFIRMATION, transaction1);

        Car car2 = new Car(2, "picture2.jpg", "Audi A9", 2800000, 2018, true);
        Customer customer2 = new Customer(2, "Name2", "+12345678912");
        Transaction transaction2 = new Transaction(2, car2, customer2);
        TransactionStatus transactionStatus2 = new TransactionStatus(2, StatusName.APPLICATION_CONFIRMATION, transaction2);

        List<TransactionStatus> freeTransactions = Arrays.asList(transactionStatus1, transactionStatus2);

        when(managerRepository.findByUsername(manager.getUsername())).thenReturn(manager);
        when(statusRepository.findAllFree(pageable)).thenReturn(freeTransactions);

        assertEquals(freeTransactions, transactionService.getAllFreeTransactions(manager.getUsername(), page, size));

        verify(managerRepository).findByUsername(manager.getUsername());
        verify(statusRepository).findAllFree(pageable);
    }

    @Test
    public void testGetAllFreeTransactionsByNonExistingManager() {
        when(managerRepository.findByUsername("manager1")).thenReturn(null);
        try {
            transactionService.getAllFreeTransactions("manager1", 0, 1);
            fail();
        } catch (CarShopException ex) {
            assertEquals(ErrorCode.USER_NOT_EXISTS, ex.getErrorCode());
        }
        verify(managerRepository).findByUsername("manager1");
    }

    @Test
    public void testGetAllTransactionByManager() {
        Manager manager = new Manager(1, "manager", "password");

        Car car1 = new Car(1, "picture1.jpg", "Audi A8", 2700000, 2017, true);
        Customer customer1 = new Customer(1, "Name1", "+12345678911");
        Transaction transaction1 = new Transaction(1, car1, customer1, manager);
        TransactionStatus transactionStatus1 = new TransactionStatus(1, StatusName.APPLICATION_CONFIRMATION, transaction1);

        Car car2 = new Car(2, "picture2.jpg", "Audi A9", 2800000, 2018, true);
        Customer customer2 = new Customer(2, "Name2", "+12345678912");
        Transaction transaction2 = new Transaction(2, car2, customer2, manager);
        TransactionStatus transactionStatus2 = new TransactionStatus(2, StatusName.CONFIRMED, transaction2);

        List<TransactionStatus> transactionsStatuses = Arrays.asList(transactionStatus1, transactionStatus2);

        when(managerRepository.findByUsername(manager.getUsername())).thenReturn(manager);
        when(statusRepository.findAllLastTransactionsStatusesByManager(manager.getId())).thenReturn(transactionsStatuses);

        assertEquals(transactionsStatuses, transactionService.getAllTransactionByManager(manager.getUsername()));

        verify(managerRepository).findByUsername(manager.getUsername());
        verify(statusRepository).findAllLastTransactionsStatusesByManager(manager.getId());
    }

    @Test
    public void testGetAllTransactionByNonExistingManager() {
        when(managerRepository.findByUsername("manager1")).thenReturn(null);

        try {
            transactionService.getAllTransactionByManager("manager1");
            fail();
        } catch (CarShopException ex) {
            assertEquals(ErrorCode.USER_NOT_EXISTS, ex.getErrorCode());
        }
        verify(managerRepository).findByUsername("manager1");

    }

    @Test
    public void testGetTransactionStatuses() {
        Car car = new Car(1, "picture.jpg", "Audi A8", 2700000, 2017, true);
        Customer customer = new Customer(1, "Name", "+12345678912");
        Manager manager = new Manager(1, "manager", "password");
        Transaction transaction = new Transaction(1, car, customer);

        TransactionStatus status1 = new TransactionStatus(StatusName.APPLICATION_CONFIRMATION, transaction);
        TransactionStatus status2 = new TransactionStatus(StatusName.CONFIRMED, transaction);
        TransactionStatus status3 = new TransactionStatus(StatusName.TEST_DRIVE, transaction);
        List<TransactionStatus> transactionStatuses = Arrays.asList(status1, status2, status3);

        when(managerRepository.findByUsername(manager.getUsername())).thenReturn(manager);
        when(transactionRepository.findById(transaction.getId())).thenReturn(java.util.Optional.of(transaction));
        when(statusRepository.findAllByTransactionId(transaction.getId(), Sort.by("date"))).thenReturn(transactionStatuses);

        assertEquals(transactionStatuses, transactionService.getTransactionStatuses(manager.getUsername(), transaction.getId()));

        verify(managerRepository).findByUsername(manager.getUsername());
        verify(transactionRepository).findById(transaction.getId());
        verify(statusRepository).findAllByTransactionId(transaction.getId(), Sort.by("date"));

    }

    @Test
    public void testGetTransactionStatusesByNonExistingManager() {
        when(managerRepository.findByUsername("manager1")).thenReturn(null);
        try {
            transactionService.getTransactionStatuses("manager1", 1);
            fail();
        } catch (CarShopException ex) {
            assertEquals(ErrorCode.USER_NOT_EXISTS, ex.getErrorCode());
        }
        verify(managerRepository).findByUsername("manager1");
    }

    @Test
    public void testGetStatusesOfNonExistingTransaction() {
        Manager manager = new Manager(1, "manager", "password");

        when(managerRepository.findByUsername(manager.getUsername())).thenReturn(manager);
        when(transactionRepository.findById(1)).thenReturn(java.util.Optional.empty());

        try {
            transactionService.getTransactionStatuses(manager.getUsername(), 1);
            fail();
        } catch (CarShopException ex) {
            assertEquals(ErrorCode.TRANSACTION_NOT_EXISTS, ex.getErrorCode());
        }
        verify(managerRepository).findByUsername(manager.getUsername());
        verify(transactionRepository).findById(1);
    }

}
