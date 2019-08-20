package net.thumbtack.internship.carshop.services;

import net.thumbtack.internship.carshop.exceptions.CarShopException;
import net.thumbtack.internship.carshop.exceptions.ErrorCode;
import net.thumbtack.internship.carshop.models.Manager;
import net.thumbtack.internship.carshop.models.Transaction;
import net.thumbtack.internship.carshop.models.TransactionStatus;
import net.thumbtack.internship.carshop.repositories.ManagerRepository;
import net.thumbtack.internship.carshop.repositories.TransactionRepository;
import net.thumbtack.internship.carshop.repositories.TransactionStatusRepository;
import net.thumbtack.internship.carshop.requests.AddTransactionStatusRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {
    private final ManagerRepository managerRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionStatusRepository transactionStatusRepository;

    @Autowired
    public TransactionService(ManagerRepository managerRepository,
                              TransactionRepository transactionRepository,
                              TransactionStatusRepository transactionStatusRepository) {
        this.managerRepository = managerRepository;
        this.transactionRepository = transactionRepository;
        this.transactionStatusRepository = transactionStatusRepository;
    }

    public Transaction pickUpTransaction(String username, int transactionId) {
        Manager manager = findManagerByUsername(username);
        Transaction transaction = findTransactionById(transactionId);

        transaction.setManager(manager);
        transactionRepository.save(transaction);

        return transaction;
    }

    public List<TransactionStatus> addTransactionStatus(AddTransactionStatusRequest request, String username, int transactionId) {
        findManagerByUsername(username);
        Transaction transaction = findTransactionById(transactionId);

        TransactionStatus status = new TransactionStatus(request.getStatusName(), transaction);
        transactionStatusRepository.save(status);

        return transactionStatusRepository.findAllByTransactionId(transactionId, Sort.by("date"));
    }

    public List<TransactionStatus> getAllFreeTransactions(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("date"));
        return transactionStatusRepository.findAllFree(pageable);
    }

    public List<TransactionStatus> getAllTransactionByManager(String username, int page, int size) {
        Manager manager = findManagerByUsername(username);
        Pageable pageable = PageRequest.of(0, 1);
        List<Transaction> transactions = transactionRepository.findAllByManager(manager.getId());
        List<TransactionStatus> transactionStatuses = new ArrayList<>();
        for (Transaction transaction : transactions) {
            transactionStatuses.add(transactionStatusRepository.findLastTransactionStatus(transaction.getId(), pageable).get(0));
        }
        return transactionStatuses;
    }

    public List<TransactionStatus> getTransactionStatuses(String username, int transactionId) {
        findManagerByUsername(username);
        findTransactionById(transactionId);
        return transactionStatusRepository.findAllByTransactionId(transactionId, Sort.by("date"));
    }

    private Manager findManagerById(int userId) {
        Manager manager = managerRepository.findById(userId).orElse(null);

        if (manager == null)
            throw new CarShopException(ErrorCode.USER_NOT_EXISTS, String.valueOf(userId));

        return manager;
    }

    private Manager findManagerByUsername(String username) {
        Manager manager = managerRepository.findByUsername(username);
        if (manager == null)
            throw new CarShopException(ErrorCode.USER_NOT_EXISTS, username);

        return manager;
    }

    private Transaction findTransactionById(int transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId).orElse(null);

        if (transaction == null)
            throw new CarShopException(ErrorCode.TRANSACTION_NOT_EXISTS, String.valueOf(transactionId));

        return transaction;
    }

}
