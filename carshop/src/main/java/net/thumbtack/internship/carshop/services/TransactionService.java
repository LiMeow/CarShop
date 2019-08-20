package net.thumbtack.internship.carshop.services;

import net.thumbtack.internship.carshop.exceptions.CarShopException;
import net.thumbtack.internship.carshop.exceptions.ErrorCode;
import net.thumbtack.internship.carshop.models.Manager;
import net.thumbtack.internship.carshop.models.StatusName;
import net.thumbtack.internship.carshop.models.Transaction;
import net.thumbtack.internship.carshop.models.TransactionStatus;
import net.thumbtack.internship.carshop.repositories.ManagerRepository;
import net.thumbtack.internship.carshop.repositories.TransactionRepository;
import net.thumbtack.internship.carshop.repositories.TransactionStatusRepository;
import net.thumbtack.internship.carshop.requests.TransactionStatusRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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

    public TransactionStatus pickUpTransaction(int managerId, int transactionId) {
        Manager manager = findManagerById(managerId);
        Transaction transaction = findTransactionById(transactionId);

        transaction.setManager(manager);
        transactionRepository.save(transaction);

        TransactionStatus transactionStatus = new TransactionStatus(StatusName.APPLICATION_CONFIRMATION, transaction);
        transactionStatusRepository.save(transactionStatus);

        return transactionStatus;
    }

    public List<TransactionStatus> addTransactionStatus(TransactionStatusRequest request, int userId, int transactionId) {
        findManagerById(userId);
        Transaction transaction = findTransactionById(transactionId);

        TransactionStatus status = new TransactionStatus(request.getStatusName(), transaction);
        transactionStatusRepository.save(status);

        return transactionStatusRepository.findAllByTransactionId(transactionId, Sort.by("date"));
    }

    public void deleteTransactionStatus(int userId, int transactionId, int transactionStatusId) {
        findManagerById(userId);
        findTransactionById(transactionId);
        findTransactionStatusById(transactionStatusId);

        transactionStatusRepository.deleteById(transactionStatusId);
    }

    public List<TransactionStatus> editTransactionStatus(TransactionStatusRequest request, int userId, int transactionId, int transactionStatusId) {
        findManagerById(userId);
        findTransactionById(transactionId);

        TransactionStatus status = findTransactionStatusById(transactionStatusId);

        status.setStatusName(request.getStatusName());
        transactionStatusRepository.save(status);

        return transactionStatusRepository.findAllByTransactionId(transactionId, Sort.by("date"));
    }

    public List<Transaction> getAllFreeTransactions(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return transactionRepository.findAllFree(pageable);
    }

    public List<TransactionStatus> getAllTransactionByManager(int userId, int page, int size) {
        findManagerById(userId);

        Pageable pageable = PageRequest.of(page, size, Sort.by("date"));
        return transactionStatusRepository.findAllByManager(userId, pageable);
    }

    public List<TransactionStatus> getTransactionStatuses(int userId, int transactionId) {
        findManagerById(userId);
        findTransactionById(transactionId);

        return transactionStatusRepository.findAllByTransactionId(transactionId, Sort.by("date"));
    }

    private Manager findManagerById(int userId) {
        Manager manager = managerRepository.findById(userId).orElse(null);

        if (manager == null)
            throw new CarShopException(ErrorCode.USER_NOT_EXISTS, String.valueOf(userId));

        return manager;
    }

    private Transaction findTransactionById(int transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId).orElse(null);

        if (transaction == null)
            throw new CarShopException(ErrorCode.TRANSACTION_NOT_EXISTS, String.valueOf(transactionId));

        return transaction;
    }

    private TransactionStatus findTransactionStatusById(int transactionStatusId) {
        TransactionStatus status = transactionStatusRepository.findById(transactionStatusId).orElse(null);
        if (status == null)
            throw new CarShopException(ErrorCode.TRANSACTION_STATUS_NOT_EXISTS, String.valueOf(transactionStatusId));

        return status;
    }

}
