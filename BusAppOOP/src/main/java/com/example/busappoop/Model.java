package com.example.busappoop;

import java.sql.Date;

public class Model {
    private int ID;
    private String locationFrom;
    private String locationTo;
    private Date departureTime;
    private Date arrivalTime;
    private double Price;
    private int maxFreeSpace;

    public Model(int ID, String locationFrom, String locationTo,
                 Date departureTime, Date arrivalTime,
                 double Price, int maxFreeSpace) {
        this.ID = ID;
        this.locationFrom = locationFrom;
        this.locationTo = locationTo;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.Price = Price;
        this.maxFreeSpace = maxFreeSpace;
    }

    public int getID() { return ID; }

    public String getLocationFrom() {
        return locationFrom;
    }

    public String getLocationTo() {
        return locationTo;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public double getPrice() {
        return Price;
    }

    public int getMaxFreeSpace() {
        return maxFreeSpace;
    }
}
