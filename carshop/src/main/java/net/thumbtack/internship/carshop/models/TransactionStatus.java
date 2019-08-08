package net.thumbtack.internship.carshop.models;

public enum TransactionStatus {
    APPLICATION_CONFIRMATION("Pending application confirmation"),
    CONFIRMED("Application confirmed."),
    TEST_DRIVE("Meeting with buyer expected"),
    CONTRACT_PREPARATION("Preparation of a purchase agreement"),
    PAYMENT_EXPECTED("Payment expected"),
    CAR_DELIVERY("Car delivery"),
    REJECTED("Application rejected");

    private String description;

    TransactionStatus() {
    }

    TransactionStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
