package net.thumbtack.shop.controllers;

import net.thumbtack.shop.models.StatusName;
import net.thumbtack.shop.models.TransactionStatus;
import net.thumbtack.shop.requests.AddTransactionStatusRequest;
import net.thumbtack.shop.responses.ChartItem;
import net.thumbtack.shop.responses.TransactionInfo;
import net.thumbtack.shop.services.ChartService;
import net.thumbtack.shop.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

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
    public String freeTransactionsPage(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        List<TransactionStatus> transactionStatuses = transactionService.getAllFreeTransactions(username, 0, 8);
        List<TransactionInfo> transactions = transactionService.getTransactionInfoList(transactionStatuses);

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
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        List<TransactionStatus> transactionStatuses = transactionService.getAllTransactionByManager(username);
        List<TransactionInfo> transactions = transactionService.getTransactionInfoList(transactionStatuses);

        model.addAttribute("transactions", transactions);
        return "transactionsInProgress";
    }

    @GetMapping("/transaction-story/{id}")
    public String transactionStoryPage(@PathVariable("id") int transactionId, Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        List<TransactionStatus> transactionStatuses = transactionService.getTransactionStatuses(username, transactionId);
        List<TransactionInfo> statuses = transactionService.getTransactionInfoList(transactionStatuses);

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

    @GetMapping("/transaction-statistics")
    public String getChart(ModelMap modelMap) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        List<ChartItem> applicationConfirmationData = chartService.getChartData(username, StatusName.APPLICATION_CONFIRMATION);
        List<ChartItem> confirmedData = chartService.getChartData(username, StatusName.CONFIRMED);

        modelMap.addAttribute("chartItems1", applicationConfirmationData);
        modelMap.addAttribute("chartItems2", confirmedData);
        return "transactionsStatistics";
    }


}