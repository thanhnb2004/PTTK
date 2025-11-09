package com.tkht.model;

public class Staff_Assignment {
    private String id;
    private String staffId;
    private String assignmentId;
    private String salesInvoiceId;

    public Staff_Assignment() {}

    public Staff_Assignment(String id, String staffId, String assignmentId, String salesInvoiceId) {
        this.id = id;
        this.staffId = staffId;
        this.assignmentId = assignmentId;
        this.salesInvoiceId = salesInvoiceId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(String assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getSalesInvoiceId() {
        return salesInvoiceId;
    }

    public void setSalesInvoiceId(String salesInvoiceId) {
        this.salesInvoiceId = salesInvoiceId;
    }
}

