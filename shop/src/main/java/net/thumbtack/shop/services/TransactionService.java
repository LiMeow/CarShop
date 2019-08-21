package net.thumbtack.shop.services;

import net.thumbtack.shop.exceptions.CarShopException;
import net.thumbtack.shop.exceptions.ErrorCode;
import net.thumbtack.shop.models.Manager;
import net.thumbtack.shop.models.Transaction;
import net.thumbtack.shop.models.TransactionStatus;
import net.thumbtack.shop.repositories.ManagerRepository;
import net.thumbtack.shop.repositories.TransactionRepository;
import net.thumbtack.shop.repositories.TransactionStatusRepository;
import net.thumbtack.shop.requests.AddTransactionStatusRequest;
import net.thumbtack.shop.responses.TransactionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {
    private final ManagerRepository managerRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionStatusRepository transactionStatusRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    public TransactionService(ManagerRepository managerRepository,
                              TransactionRepository transactionRepository,
                              TransactionStatusRepository transactionStatusRepository) {
        this.managerRepository = managerRepository;
        this.transactionRepository = transactionRepository;
        this.transactionStatusRepository = transactionStatusRepository;
    }

    public Transaction pickUpTransaction(String username, int transactionId) {
        LOGGER.debug("TransactionService pick up transaction with id '{}' by manager with username '{}'", transactionId, username);

        Manager manager = findManagerByUsername(username);
        Transaction transaction = findTransactionById(transactionId);

        transaction.setManager(manager);
        transactionRepository.save(transaction);

        return transaction;
    }

    public List<TransactionStatus> addTransactionStatus(AddTransactionStatusRequest request, String username, int transactionId) {
        LOGGER.debug("TransactionService  add status '{}' to transaction with id '{}' by manager with username '{}'", request.getStatusName(), transactionId, username);

        findManagerByUsername(username);
        Transaction transaction = findTransactionById(transactionId);

        TransactionStatus status = new TransactionStatus(request.getStatusName(), transaction);
        transactionStatusRepository.save(status);

        return transactionStatusRepository.findAllByTransactionId(transactionId, Sort.by("date"));
    }

    public List<TransactionStatus> getAllFreeTransactions(String username, int page, int size) {
        LOGGER.debug("TransactionService get all free transactions by manager with username '{}'", username);

        findManagerByUsername(username);
        Pageable pageable = PageRequest.of(page, size, Sort.by("date"));

        return transactionStatusRepository.findAllFree(pageable);
    }

    public List<TransactionStatus> getAllTransactionByManager(String username) {
        LOGGER.debug("TransactionService get all transactions by manager with username '{}'", username);

        Manager manager = findManagerByUsername(username);
        return transactionStatusRepository.findAllLastTransactionsStatusesByManager(manager.getId());
    }

    public List<TransactionStatus> getTransactionStatuses(String username, int transactionId) {
        LOGGER.debug("TransactionService get statuses of transaction with id '{}' by manager with username '{}'", transactionId, username);
        findManagerByUsername(username);
        findTransactionById(transactionId);

        return transactionStatusRepository.findAllByTransactionId(transactionId, Sort.by("date"));
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
                    transactionStatus.getStatusName()));
        }
        return transactionInfoList;
    }


    private Manager findManagerByUsername(String username) {
        Manager manager = managerRepository.findByUsername(username);
        if (manager == null) {
            LOGGER.error("Unable to find manager with username '{}'", username);
            throw new CarShopException(ErrorCode.USER_NOT_EXISTS, username);
        }

        return manager;
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
