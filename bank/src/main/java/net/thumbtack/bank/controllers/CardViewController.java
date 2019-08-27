package net.thumbtack.bank.controllers;

import net.thumbtack.bank.requests.CreateCardRequest;
import net.thumbtack.bank.requests.PutMoneyRequest;
import net.thumbtack.bank.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CardViewController {
    private final CardService cardService;

    @Autowired
    public CardViewController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping("/cards/create-card")
    public String createCardPage() {
        return "createCard";
    }

    @PostMapping("/cards/create")
    public String createCard(@ModelAttribute("request") CreateCardRequest request, Model model) {
        model.addAttribute("card", cardService.createCard(request));
        return "cardInfo";
    }

    @GetMapping("/cards/put-money")
    public String putMoneyPage() {
        return "putMoney";
    }

    @PostMapping("/cards/put")
    public String putMoney(@ModelAttribute("request") PutMoneyRequest request, Model model) {
        cardService.putMoney(request);
        return "onSuccessOperation";
    }

    @GetMapping("/cards/information")
    public String cardsInfo(Model model) {
        model.addAttribute("cards", cardService.getAllCards());
        return "cardsInfo";
    }

    @GetMapping("/cards/{id}/information")
    public String cardsInfo(@PathVariable("id") int cardId, Model model) {
        model.addAttribute("card", cardService.getCard(cardId));
        return "adminCardInfo";
    }

}
