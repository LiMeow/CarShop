package net.thumbtack.shop.models;

public enum StatusName {
    APPLICATION_CONFIRMATION("Pending application confirmation"),
    CONFIRMED("Application confirmed."),
    TEST_DRIVE("Meeting with buyer expected"),
    CONTRACT_PREPARATION("Preparation of a purchase agreement"),
    PAYMENT_EXPECTED("Payment expected"),
    CAR_DELIVERY("Car delivery"),
    COMPLETED("Application completed"),
    REJECTED("Application rejected");

    private String description;

    StatusName() {
    }

    StatusName(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
