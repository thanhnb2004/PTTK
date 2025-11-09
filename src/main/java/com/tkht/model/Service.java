package com.tkht.model;

import java.util.ArrayList;
import java.util.List;

public class Service {
    private String id;
    private String name;
    private List<String> salesInvoiceIds = new ArrayList<>();

    public Service() {}

    public Service(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getSalesInvoiceIds() {
        return salesInvoiceIds;
    }

    public void setSalesInvoiceIds(List<String> salesInvoiceIds) {
        this.salesInvoiceIds = salesInvoiceIds;
    }

    public void addSalesInvoiceId(String salesInvoiceId) {
        this.salesInvoiceIds.add(salesInvoiceId);
    }
}
