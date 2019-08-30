package net.thumbtack.shop.requests;

public class PickUpTransactionRequest {
    private String username;
    private int transactionId;

    public PickUpTransactionRequest(String username, int transactionId) {
        this.username = username;
        this.transactionId = transactionId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }
}
