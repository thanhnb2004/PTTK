package com.tkht.model;

import java.util.Date;

public class Sales_Invoice {
    private String id;
    private Date saleDate;
    private Float totalAmount;
    private String paymentStatus;
    private String vehicleId;

    public Sales_Invoice() {}

    public Sales_Invoice(String id, Date saleDate, Float totalAmount, String paymentStatus, String vehicleId) {
        this.id = id;
        this.saleDate = saleDate;
        this.totalAmount = totalAmount;
        this.paymentStatus = paymentStatus;
        this.vehicleId = vehicleId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public Float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }
}
