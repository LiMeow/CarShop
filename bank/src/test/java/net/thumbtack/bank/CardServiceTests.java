package net.thumbtack.bank;

import net.thumbtack.bank.exceptions.BankException;
import net.thumbtack.bank.exceptions.ErrorCode;
import net.thumbtack.bank.models.Card;
import net.thumbtack.bank.models.Operation;
import net.thumbtack.bank.repositories.CardOperationRepository;
import net.thumbtack.bank.repositories.CardRepository;
import net.thumbtack.bank.requests.CreateCardRequest;
import net.thumbtack.bank.requests.DeleteCardRequest;
import net.thumbtack.bank.requests.PutMoneyRequest;
import net.thumbtack.bank.requests.TakeMoneyRequest;
import net.thumbtack.bank.responses.CardInfo;
import net.thumbtack.bank.services.CardService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CardServiceTests {
    @Mock
    private CardOperationRepository cardOperationRepository;
    @Mock
    private CardRepository cardRepository;
    @InjectMocks
    private CardService cardService;

    @Test
    public void testCreateCard() {
        CreateCardRequest request = new CreateCardRequest("FIRST LAST");
        cardService.createCard(request);

        verify(cardRepository).save(Mockito.any());
        verify(cardOperationRepository).save(Mockito.any());
    }

    @Test
    public void testPutMoney() {
        Card card = new Card("FIRST LAST");
        Card updatedCard = new Card(card.getId(), card.getCardNumber(), card.getExpiryDate(), card.getCvv(), card.getCardHolderName(), 1000, new ArrayList<>());
        PutMoneyRequest request = new PutMoneyRequest(card.getCardNumber(), 1000);
        Operation operation = new Operation(card, "+1000.0");

        when(cardRepository.findByCardNumber(card.getCardNumber())).thenReturn(card);
        when(cardRepository.save(updatedCard)).thenReturn(updatedCard);
        when(cardOperationRepository.save(operation)).thenReturn(operation);

        assertEquals(new CardInfo(card.getCardNumber(), updatedCard.getBalance()), cardService.putMoney(request));

        verify(cardRepository).findByCardNumber(card.getCardNumber());
        verify(cardRepository).save(updatedCard);
        verify(cardOperationRepository).save(operation);

    }

    @Test
    public void testPutMoneyToCardWIthWrongNumber() {
        String cardNumber = "1234 5678 1234 5678";
        PutMoneyRequest request = new PutMoneyRequest(cardNumber, 1000);

        when(cardRepository.findByCardNumber(cardNumber)).thenReturn(null);
        try {
            cardService.putMoney(request);
        } catch (BankException ex) {
            assertEquals(ErrorCode.CARD_NOT_EXISTS, ex.getErrorCode());
        }
        verify(cardRepository).findByCardNumber(cardNumber);
    }

    @Test
    public void testTakeMoney() {
        Card card = new Card("FIRST LAST", 100);
        Card updatedCard = new Card(card.getId(), card.getCardNumber(), card.getExpiryDate(), card.getCvv(), card.getCardHolderName(), 90, new ArrayList<>());
        TakeMoneyRequest request = new TakeMoneyRequest(card.getCardNumber(), card.getExpiryDate(), card.getCvv(), card.getCardHolderName(), 10);
        Operation operation = new Operation(card, "-10.0");

        when(cardRepository.findByCardNumber(card.getCardNumber())).thenReturn(card);
        when(cardRepository.save(updatedCard)).thenReturn(updatedCard);
        when(cardOperationRepository.save(operation)).thenReturn(operation);

        assertEquals(new CardInfo(card.getCardNumber(), updatedCard.getBalance()), cardService.takeMoney(request));

        verify(cardRepository).findByCardNumber(card.getCardNumber());
        verify(cardRepository).save(updatedCard);
        verify(cardOperationRepository).save(operation);
    }

    @Test
    public void testTakeMoneyWithWrongCardNumber() {
        String wrongCardNumber = "1234 5678 1234 5678";
        Card card = new Card("FIRST LAST", 100);
        TakeMoneyRequest request = new TakeMoneyRequest(wrongCardNumber, card.getExpiryDate(), card.getCvv(), card.getCardHolderName(), 10);

        when(cardRepository.findByCardNumber(wrongCardNumber)).thenReturn(null);
        try {
            cardService.takeMoney(request);
        } catch (BankException ex) {
            assertEquals(ErrorCode.CARD_NOT_EXISTS, ex.getErrorCode());
        }
        verify(cardRepository).findByCardNumber(wrongCardNumber);
    }

    @Test
    public void testTakeMoneyWithWrongExpiringDate() {
        Card card = new Card("FIRST LAST", 100);
        TakeMoneyRequest request = new TakeMoneyRequest(card.getCardNumber(), "01/2010", card.getCvv(), card.getCardHolderName(), 10);

        when(cardRepository.findByCardNumber(card.getCardNumber())).thenReturn(card);
        try {
            cardService.takeMoney(request);
        } catch (BankException ex) {
            assertEquals(ErrorCode.INVALID_EXPIRATION_DATE, ex.getErrorCode());
        }
        verify(cardRepository).findByCardNumber(card.getCardNumber());
    }

    @Test
    public void testTakeMoneyWithWrongCvv() {
        Card card = new Card("FIRST LAST", 100);
        TakeMoneyRequest request = new TakeMoneyRequest(card.getCardNumber(), card.getExpiryDate(), "000", card.getCardHolderName(), 10);

        when(cardRepository.findByCardNumber(card.getCardNumber())).thenReturn(card);
        try {
            cardService.takeMoney(request);
        } catch (BankException ex) {
            assertEquals(ErrorCode.INVALID_CVV, ex.getErrorCode());
        }
        verify(cardRepository).findByCardNumber(card.getCardNumber());
    }

    @Test
    public void testTakeMoneyWithWrongCardHolderName() {
        Card card = new Card("FIRST LAST", 100);
        TakeMoneyRequest request = new TakeMoneyRequest(card.getCardNumber(), card.getExpiryDate(), card.getCvv(), "BLA BLA", 10);

        when(cardRepository.findByCardNumber(card.getCardNumber())).thenReturn(card);
        try {
            cardService.takeMoney(request);
        } catch (BankException ex) {
            assertEquals(ErrorCode.INVALID_CARD_HOLDER_NAME, ex.getErrorCode());
        }
        verify(cardRepository).findByCardNumber(card.getCardNumber());
    }

    @Test
    public void testTakeMoneyFromCardWithNotEnoughMoney() {
        Card card = new Card("FIRST LAST", 100);
        TakeMoneyRequest request = new TakeMoneyRequest(card.getCardNumber(), card.getExpiryDate(), card.getCvv(), card.getCardHolderName(), 110);

        when(cardRepository.findByCardNumber(card.getCardNumber())).thenReturn(card);
        try {
            cardService.takeMoney(request);
        } catch (BankException ex) {
            assertEquals(ErrorCode.NOT_ENOUGH_MONEY, ex.getErrorCode());
        }
        verify(cardRepository).findByCardNumber(card.getCardNumber());
    }

    @Test
    public void testDeleteCard() {
        Card card = new Card("FIRST LAST");
        DeleteCardRequest request = new DeleteCardRequest(card.getCardNumber(), card.getExpiryDate(), card.getCvv(), card.getCardHolderName());
        Operation operation = new Operation(card, "Card with number '" + card.getCardNumber() + "' has been deleted.");

        when(cardRepository.findByCardNumber(card.getCardNumber())).thenReturn(card);
        when(cardOperationRepository.save(operation)).thenReturn(operation);

        cardService.deleteCard(request);

        verify(cardRepository).findByCardNumber(card.getCardNumber());
        verify(cardRepository).deleteById(card.getId());
        verify(cardOperationRepository).save(operation);
    }

    @Test
    public void testDeleteCardByWrongCardNumber() {
        String wrongCardNumber = "1234 5678 1234 5678";
        Card card = new Card("FIRST LAST");
        DeleteCardRequest request = new DeleteCardRequest(wrongCardNumber, card.getExpiryDate(), card.getCvv(), card.getCardHolderName());

        when(cardRepository.findByCardNumber(wrongCardNumber)).thenReturn(null);
        try {
            cardService.deleteCard(request);
        } catch (BankException ex) {
            assertEquals(ErrorCode.CARD_NOT_EXISTS, ex.getErrorCode());
        }
        verify(cardRepository).findByCardNumber(wrongCardNumber);

    }


}
