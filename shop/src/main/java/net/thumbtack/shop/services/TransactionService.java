package net.thumbtack.shop.services;

import net.thumbtack.shop.exceptions.CarShopException;
import net.thumbtack.shop.exceptions.ErrorCode;
import net.thumbtack.shop.models.*;
import net.thumbtack.shop.repositories.*;
import net.thumbtack.shop.requests.EditTransactionStatusDescriptionRequest;
import net.thumbtack.shop.requests.PickUpTransactionRequest;
import net.thumbtack.shop.responses.TransactionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionStatusRepository transactionStatusRepository;
    private final UserRepository userRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    public TransactionService(
            CarRepository carRepository, CustomerRepository customerRepository, TransactionRepository transactionRepository,
            TransactionStatusRepository transactionStatusRepository,
            UserRepository userRepository) {
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
        this.transactionRepository = transactionRepository;
        this.transactionStatusRepository = transactionStatusRepository;
        this.userRepository = userRepository;
    }

    public Transaction pickUpTransaction(PickUpTransactionRequest request) {
        LOGGER.debug("TransactionService pick up transaction with id '{}' by manager with username '{}'", request.getTransactionId(), request.getUsername());

        User manager = findManagerByUsername(request.getUsername());
        Transaction transaction = findTransactionById(request.getTransactionId());

        transaction.setManager(manager);
        transactionRepository.save(transaction);

        return transaction;
    }

    public List<TransactionStatus> rejectTransaction(int transactionId) {
        LOGGER.debug("TransactionService: reject transaction with id '{}'", transactionId);

        Transaction transaction = findTransactionById(transactionId);
        TransactionStatus status = new TransactionStatus(StatusName.REJECTED, transaction);
        transactionStatusRepository.save(status);

        return transactionStatusRepository.findAllByTransactionId(transactionId, Sort.by("date"));
    }

    public List<TransactionStatus> addNextTransactionStatus(int transactionId) {
        LOGGER.debug("TransactionService  add new status to transaction with id '{}'", transactionId);

        Transaction transaction = findTransactionById(transactionId);
        List<TransactionStatus> statuses = transactionStatusRepository.findAllByTransactionId(transaction.getId(), Sort.by("date"));
        StatusName nextStatus = StatusName.values()[statuses.size()];

        if (nextStatus.equals(StatusName.CONTRACT_PREPARATION)) {
            transaction.getCar().setAvailable(false);
            carRepository.save(transaction.getCar());
        }

        TransactionStatus status = new TransactionStatus(nextStatus, transaction);
        transactionStatusRepository.save(status);

        return transactionStatusRepository.findAllByTransactionId(transactionId, Sort.by("date"));
    }

    public Transaction getTransactionById(int transactionId) {
        return findTransactionById(transactionId);
    }

    public List<TransactionStatus> getAllFreeTransactions() {
        LOGGER.debug("TransactionService get all free transactions");
        return transactionStatusRepository.findAllFree(Sort.by("date"));
    }

    public List<TransactionStatus> getAllTransactionByManager(String username) {
        LOGGER.debug("TransactionService get all transactions by manager with username '{}'", username);

        User manager = findManagerByUsername(username);
        return transactionStatusRepository.findAllLastTransactionsStatusesByManager(manager.getId());
    }

    public TransactionStatus editTransactionStatusDescription(int transactionId, int statusId, EditTransactionStatusDescriptionRequest request) {
        LOGGER.debug("TransactionService edit description to status '{}'", statusId);

        findTransactionById(transactionId);
        TransactionStatus status = transactionStatusRepository.findById(statusId).orElse(null);

        if (status == null)
            throw new CarShopException(ErrorCode.TRANSACTION_STATUS_NOT_EXISTS, String.valueOf(statusId));

        status.setDescription(request.getDescription());
        transactionStatusRepository.save(status);

        return status;
    }

    public List<TransactionStatus> getTransactionStatuses(int transactionId) {
        LOGGER.debug("TransactionService get statuses of transaction with id '{}'", transactionId);
        findTransactionById(transactionId);
        return transactionStatusRepository.findAllByTransactionId(transactionId, Sort.by("date"));
    }

    public List<TransactionStatus> getTransactionStatusesByCustomer(String username) {
        LOGGER.debug("TransactionService get statuses of transaction by customer with username '{}'", username);

        Customer customer = findCustomerByUsername(username);
        Transaction transaction = transactionRepository.findByCustomerId(customer.getId());

        return transactionStatusRepository.findAllByTransactionId(transaction.getId(), Sort.by("date"));
    }

    public List<TransactionInfo> getTransactionInfoList(List<TransactionStatus> transactionStatuses) {
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss     dd.MM.yyyy");
        List<TransactionInfo> transactionInfoList = new ArrayList<>();

        for (TransactionStatus transactionStatus : transactionStatuses) {
            transactionInfoList.add(new TransactionInfo(
                    transactionStatus.getTransaction().getId(),
                    formatter.format(transactionStatus.getDate()),
                    transactionStatus.getTransaction().getCar().getModel(),
                    transactionStatus.getTransaction().getCar().getPrice(),
                    transactionStatus.getTransaction().getCustomer().getName(),
                    transactionStatus.getId(),
                    transactionStatus.getStatusName(),
                    transactionStatus.getDescription()));
        }
        return transactionInfoList;
    }


    private User findManagerByUsername(String username) {
        User manager = userRepository.findManagerByUsername(username);
        if (manager == null) {
            LOGGER.error("Unable to find manager with username '{}'", username);
            throw new CarShopException(ErrorCode.USER_NOT_EXISTS, username);
        }

        return manager;
    }

    private Customer findCustomerByUsername(String username) {
        Customer customer = customerRepository.findCustomerByUsername(username);

        if (customer == null) {
            LOGGER.error("Unable to find customer with username '{}'", username);
            throw new CarShopException(ErrorCode.USER_NOT_EXISTS, username);
        }

        return customer;
    }

    private Transaction findTransactionById(int transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId).orElse(null);

        if (transaction == null) {
            LOGGER.error("Unable to find transaction with id '{}'", transactionId);
            throw new CarShopException(ErrorCode.TRANSACTION_NOT_EXISTS, String.valueOf(transactionId));
        }

        return transaction;
    }

}
