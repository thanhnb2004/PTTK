package com.tkht.model;

public class ServiceSalesInvoice {
    private String id;
    private String serviceId;
    private String salesInvoiceId;

    public ServiceSalesInvoice() {}

    public ServiceSalesInvoice(String id, String serviceId, String salesInvoiceId) {
        this.id = id;
        this.serviceId = serviceId;
        this.salesInvoiceId = salesInvoiceId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getSalesInvoiceId() {
        return salesInvoiceId;
    }

    public void setSalesInvoiceId(String salesInvoiceId) {
        this.salesInvoiceId = salesInvoiceId;
    }
}

