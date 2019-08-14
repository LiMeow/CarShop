package net.thumbtack.internship.carshop.controllers;

import net.thumbtack.internship.carshop.models.StatusName;
import net.thumbtack.internship.carshop.models.TransactionStatus;
import net.thumbtack.internship.carshop.requests.AddTransactionStatusRequest;
import net.thumbtack.internship.carshop.responses.TransactionInfo;
import net.thumbtack.internship.carshop.services.ChartService;
import net.thumbtack.internship.carshop.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class TransactionViewController {
    private final TransactionService transactionService;
    private final ChartService chartService;

    @Autowired
    public TransactionViewController(TransactionService transactionService, ChartService chartService) {
        this.transactionService = transactionService;
        this.chartService = chartService;
    }

    @GetMapping("/free-transactions")
    public String freeTrannsactionsPage(Model model) {
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss dd.MM.yyyy");

        List<TransactionStatus> transactionStatuses = transactionService.getAllFreeTransactions(0, 8);
        List<TransactionInfo> transactions = new ArrayList<>();

        for (TransactionStatus transactionStatus : transactionStatuses) {
            transactions.add(new TransactionInfo(
                    transactionStatus.getTransaction().getId(),
                    formatter.format(transactionStatus.getDate()),
                    transactionStatus.getTransaction().getCar().getModel(),
                    transactionStatus.getTransaction().getCar().getPrice(),
                    transactionStatus.getTransaction().getCustomer().getName()));
        }

        model.addAttribute("freeTransactions", transactions);
        return "freeTransactions";
    }

    @PostMapping("/pick-up/{id}")
    public String pickUpTransaction(@PathVariable("id") int transactionId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        transactionService.pickUpTransaction(username, transactionId);
        return "redirect:/transactions-in-progress";
    }

    @GetMapping("/transactions-in-progress")
    public String transactionsInProgressPage(Model model) {
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss dd.MM.yyyy");
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        List<TransactionStatus> transactionStatuses = transactionService.getAllTransactionByManager(username, 0, 8);
        List<TransactionInfo> transactions = new ArrayList<>();

        for (TransactionStatus transactionStatus : transactionStatuses) {
            transactions.add(new TransactionInfo(
                    transactionStatus.getTransaction().getId(),
                    formatter.format(transactionStatus.getDate()),
                    transactionStatus.getTransaction().getCar().getModel(),
                    transactionStatus.getTransaction().getCustomer().getName(),
                    transactionStatus.getStatusName()));
        }

        model.addAttribute("transactions", transactions);
        return "transactionsInProgress";
    }

    @GetMapping("/transaction-story/{id}")
    public String transactionStoryPage(@PathVariable("id") int transactionId, Model model) {
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss dd.MM.yyyy");
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        List<TransactionStatus> statuses = transactionService.getTransactionStatuses(username, transactionId);

        model.addAttribute("transactionId", transactionId);
        model.addAttribute("statuses", statuses);
        return "transactionInfo";
    }

    @GetMapping("/transaction-story/{id}/add-status")
    public String addTransactionStatus(@PathVariable("id") int transactionId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<TransactionStatus> transactionStatuses = transactionService.getTransactionStatuses(username, transactionId);
        AddTransactionStatusRequest request = new AddTransactionStatusRequest(StatusName.values()[transactionStatuses.size()]);
        transactionService.addTransactionStatus(request, username, transactionId);
        return "redirect:/transaction-story/" + transactionId;
    }

    @GetMapping("/transaction-story/{id}/reject")
    public String rejectTransaction(@PathVariable("id") int transactionId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        AddTransactionStatusRequest request = new AddTransactionStatusRequest(StatusName.REJECTED);
        transactionService.addTransactionStatus(request, username, transactionId);
        return "redirect:/transaction-story/" + transactionId;
    }

    @GetMapping("/chart")
    public String getChart(ModelMap modelMap) {
        List<List<Map<Object, Object>>> chartData = chartService.getChartData();
        modelMap.addAttribute("dataPointsList", chartData);
        return "statistic";
    }

}
