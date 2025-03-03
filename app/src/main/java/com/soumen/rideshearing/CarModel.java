package com.soumen.rideshearing;

public class CarModel {
    String start;
    String Destination;
    String startTime,endTime,carNumber,carName;


    public CarModel(String start, String destination, String startTime, String carNumber, String endTime, String carName) {
        this.start = start;
        Destination = destination;
        this.startTime = startTime;
        this.carNumber = carNumber;
        this.endTime = endTime;
        this.carName = carName;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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
