package com.soumen.rideshearing;

public class CarModel {
    String start;
    String Destination;
    String carNumber,carName;
    boolean isComplete,isBook;


    public CarModel(String start, String destination, String carNumber, String carName, boolean isComplete, boolean isBook) {
        this.start = start;
        Destination = destination;
        this.carNumber = carNumber;
        this.carName = carName;
        this.isComplete = isComplete;
        this.isBook = isBook;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }




    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }
}
