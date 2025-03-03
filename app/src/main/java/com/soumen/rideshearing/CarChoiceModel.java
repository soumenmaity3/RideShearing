package com.soumen.rideshearing;

public class CarChoiceModel {
    private int carLogo;
    private String carName;
    private double price;
    private String carType;
    private String carNumber;
    private String contactNumber;

    public CarChoiceModel(int carLogo, String carName, double price, String carType, String carNumber, String contactNumber) {
        this.carLogo = carLogo;
        this.carName = carName;
        this.price = price;
        this.carType = carType;
        this.carNumber = carNumber;
        this.contactNumber = contactNumber;
    }

    public int getCarLogo() {
        return carLogo;
    }

    public void setCarLogo(int carLogo) {
        this.carLogo = carLogo;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }
}
