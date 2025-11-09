package com.tkht.model;

import java.util.Date;

public class Assignment {
    private String id;
    private Date deliveryDate;
    private Date deadline;
    private String salesInvoiceId;

    public Assignment() {}

    public Assignment(String id, Date deliveryDate, Date deadline, String salesInvoiceId) {
        this.id = id;
        this.deliveryDate = deliveryDate;
        this.deadline = deadline;
        this.salesInvoiceId = salesInvoiceId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getSalesInvoiceId() {
        return salesInvoiceId;
    }

    public void setSalesInvoiceId(String salesInvoiceId) {
        this.salesInvoiceId = salesInvoiceId;
    }
}
