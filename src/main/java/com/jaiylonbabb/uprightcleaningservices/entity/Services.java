package com.jaiylonbabb.uprightcleaningservices.entity;

public enum Services {
    HOUSE_CLEANING("House Cleaning", 50.00),
    OFFICE_CLEANING("Office Cleaning", 80.00),
    CARPET_CLEANING("Carpet Cleaning", 100.00),
    WINDOW_CLEANING("Window Cleaning", 60.00),
    DEEP_CLEANING("Deep Cleaning", 120.00),
    MOVE_OUT_CLEANING("Move Out Cleaning", 150.00);

    private String serviceName;
    private double price;

    void CleaningService(String serviceName, double price) {
        this.serviceName = serviceName;
        this.price = price;
    }

    Services(String serviceName, double price) {
        this.serviceName = serviceName;
        this.price = price;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getPrice() {
        return price;
    }
}
