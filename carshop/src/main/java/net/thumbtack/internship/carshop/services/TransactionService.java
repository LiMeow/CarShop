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
import net.thumbtack.internship.carshop.responses.TransactionInfo;
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

    public List<TransactionStatus> getAllFreeTransactions(String username, int page, int size) {
        findManagerByUsername(username);
        Pageable pageable = PageRequest.of(page, size, Sort.by("date"));

        return transactionStatusRepository.findAllFree(pageable);
    }

    public List<TransactionStatus> getAllTransactionByManager(String username) {
        Manager manager = findManagerByUsername(username);
        return transactionStatusRepository.findAllLastTransactionsStatusesByManager(manager.getId());
    }

    public List<TransactionStatus> getTransactionStatuses(String username, int transactionId) {
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
                    transactionStatus.getTransaction().getCustomer().getName(),
                    transactionStatus.getStatusName()));
        }
        return transactionInfoList;
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
