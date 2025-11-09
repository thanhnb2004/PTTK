package com.tkht.model;

import java.util.Date;

public class Purchase_Invoice {
    private String id;
    private Date purchaseDate;
    private String staffId;
    private String status;

    public Purchase_Invoice() {}

    public Purchase_Invoice(String id, Date purchaseDate, String staffId, String status) {
        this.id = id;
        this.purchaseDate = purchaseDate;
        this.staffId = staffId;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
