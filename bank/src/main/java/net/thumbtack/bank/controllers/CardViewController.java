package net.thumbtack.bank.controllers;

import net.thumbtack.bank.exceptions.BankException;
import net.thumbtack.bank.models.CardOperation;
import net.thumbtack.bank.requests.TakeMoneyRequest;
import net.thumbtack.bank.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CardViewController {
    private final CardService cardService;

    @Autowired
    public CardViewController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping("/take-money")
    public String takeMoney(@ModelAttribute("request") TakeMoneyRequest request, Model model) {
        try {
            cardService.takeMoney(request);
        } catch (BankException ex) {
            model.addAttribute("operationStatus", CardOperation.FAILURE);
            return "operationStatus";
        }
        model.addAttribute("operationStatus", CardOperation.SUCCESS);
        return "operationStatus";
    }
}
