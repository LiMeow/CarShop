package net.thumbtack.shop.controllers;

import net.thumbtack.shop.models.StatusName;
import net.thumbtack.shop.models.TransactionStatus;
import net.thumbtack.shop.requests.EditTransactionStatusDescriptionRequest;
import net.thumbtack.shop.requests.PickUpTransactionRequest;
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
import org.springframework.web.bind.annotation.ModelAttribute;
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
        List<TransactionStatus> transactionStatuses = transactionService.getAllFreeTransactions();
        List<TransactionInfo> transactions = transactionService.getTransactionInfoList(transactionStatuses);

        model.addAttribute("freeTransactions", transactions);
        return "freeTransactions";
    }

    @PostMapping("/pick-up/{id}")
    public String pickUpTransaction(@PathVariable("id") int transactionId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        transactionService.pickUpTransaction(new PickUpTransactionRequest(username, transactionId));
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
        List<TransactionStatus> transactionStatuses = transactionService.getTransactionStatuses(transactionId);
        List<TransactionInfo> statuses = transactionService.getTransactionInfoList(transactionStatuses);

        model.addAttribute("transactionId", transactionId);
        model.addAttribute("statuses", statuses);
        return "transactionInfoForManager";
    }

    @GetMapping("/transaction-story/{id}/add-status")
    public String addNextTransactionStatus(@PathVariable("id") int transactionId) {
        transactionService.addNextTransactionStatus(transactionId);
        return "redirect:/transaction-story/" + transactionId;
    }

    @PostMapping("/transaction-story/{transactionId}/status/{statusId}/edit-description")
    public String editTransactionStatusDescription(@PathVariable("transactionId") int transactionId,
                                                   @PathVariable("statusId") int statusId,
                                                   @ModelAttribute("request") EditTransactionStatusDescriptionRequest request) {

        transactionService.editTransactionStatusDescription(transactionId, statusId, request);

        return "redirect:/transaction-story/" + transactionId;
    }

    @GetMapping("/transaction-story/{id}/reject")
    public String rejectTransaction(@PathVariable("id") int transactionId) {
        transactionService.rejectTransaction(transactionId);
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
