package net.thumbtack.bank.services;

import net.thumbtack.bank.exceptions.BankException;
import net.thumbtack.bank.exceptions.ErrorCode;
import net.thumbtack.bank.models.Card;
import net.thumbtack.bank.models.CardOperation;
import net.thumbtack.bank.models.Operation;
import net.thumbtack.bank.repositories.CardOperationRepository;
import net.thumbtack.bank.repositories.CardRepository;
import net.thumbtack.bank.requests.CreateCardRequest;
import net.thumbtack.bank.requests.DeleteCardRequest;
import net.thumbtack.bank.requests.PutMoneyRequest;
import net.thumbtack.bank.requests.TakeMoneyRequest;
import net.thumbtack.bank.responses.CardInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Random;

@Service
public class CardService {
    private final CardRepository cardRepository;
    private final CardOperationRepository cardOperationRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(CardService.class);

    @Autowired
    public CardService(CardRepository cardRepository, CardOperationRepository cardOperationRepository) {
        this.cardRepository = cardRepository;
        this.cardOperationRepository = cardOperationRepository;
    }

    public Card createCard(CreateCardRequest request) {
        LOGGER.debug("CardRepository create Card for '{}'", request.getCardHolderName());

        Card card = new Card(
                generateCardNumber(),
                generateValidity(),
                generateCvv(),
                request.getCardHolderName());
        cardRepository.save(card);

        logOperation(card, CardOperation.CARD_CREATED, card.getCardNumber());
        return card;
    }

    public CardInfo putMoney(PutMoneyRequest request) {
        LOGGER.debug("CardRepository put money to Card with number '{}'", request.getCardNumber());

        Card card = findCardByNumber(request.getCardNumber());
        card.putMoney(request.getMoney());
        cardRepository.save(card);

        logOperation(card, CardOperation.PUT_MONEY, String.valueOf(request.getMoney()));
        return getCardInfo(card);
    }

    public CardInfo takeMoney(TakeMoneyRequest request) {
        LOGGER.debug("CardRepository take money from Card with number '{}'", request.getCardNumber());

        Card card = findCardByNumber(request.getCardNumber());
        checkCard(card, request.getValidity(), request.getCvv(), request.getCardHolderName());

        if (card.getBalance() < request.getMoney())
            throw new BankException(ErrorCode.NOT_ENOUGH_MONEY);

        card.takeMoney(request.getMoney());
        cardRepository.save(card);

        logOperation(card, CardOperation.TAKE_MONEY, String.valueOf(request.getMoney()));
        return getCardInfo(card);

    }

    public void deleteCard(DeleteCardRequest request) {
        LOGGER.debug("CardRepository delete Card with number '{}'", request.getCardNumber());

        Card card = findCardByNumber(request.getCardNumber());
        checkCard(card, request.getValidity(), request.getCvv(), request.getCardHolderName());

        cardRepository.deleteById(card.getId());
        logOperation(card, CardOperation.CARD_DELETED, card.getCardNumber());
    }


    private String generateCardNumber() {
        LOGGER.debug("CardService: card number generation");

        String cardNumber = "";
        Random random = new Random();

        for (int i = 0; i < 4; i++) {
            cardNumber += (random.nextInt(8999) + 1000) + " ";
        }
        return cardNumber.substring(0, cardNumber.length() - 1);
    }

    private String generateValidity() {
        LOGGER.debug("CardService: card validity generation");

        LocalDate date = LocalDate.now();
        String validity = date.getDayOfMonth() + "/" + (date.getYear() + 5);

        return validity;
    }

    private String generateCvv() {
        LOGGER.debug("CardService: card cvv generation");

        String cvv = "";
        Random random = new Random();

        for (int i = 0; i < 3; i++) {
            cvv += random.nextInt(9);
        }
        return cvv;
    }

    private Card findCardByNumber(String cardNumber) {
        Card card = cardRepository.findByCardNumber(cardNumber);
        if (card == null) {
            LOGGER.error("Unable to find card with number '{}'", cardNumber);
            throw new BankException(ErrorCode.CARD_NOT_EXISTS, String.valueOf(cardNumber));
        }

        return card;
    }

    private void checkCard(Card card, String validity, String cvv, String cardHolderName) {
        if (!card.getValidity().equals(validity))
            throw new BankException(ErrorCode.INVALID_EXPIRATION_DATE);

        if (!card.getCvv().equals(cvv))
            throw new BankException(ErrorCode.INVALID_CVV);

        if (!card.getCardHolderName().equals(cardHolderName))
            throw new BankException(ErrorCode.INVALID_CARD_HOLDER_NAME);
    }

    private void logOperation(Card card, CardOperation operation, String... params) {
        cardOperationRepository.save(new Operation(card, String.format(operation.getMessage(), params)));
    }

    private CardInfo getCardInfo(Card card) {
        return new CardInfo(card.getCardNumber(), card.getBalance());
    }


}
