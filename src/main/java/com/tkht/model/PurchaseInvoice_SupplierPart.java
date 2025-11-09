package com.tkht.model;

import java.util.Date;
import java.util.List;
public class PurchaseInvoice_SupplierPart {
    private String id;
    private Float quantity;
    private Float price;
    private String supplierPartId;
    private String purchaseInvoiceId;

    public PurchaseInvoice_SupplierPart() {}

    public PurchaseInvoice_SupplierPart(String id, Float quantity, Float price, String supplierPartId, String purchaseInvoiceId) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
        this.supplierPartId = supplierPartId;
        this.purchaseInvoiceId = purchaseInvoiceId;
        
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public Float getQuantity() {
        return quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }
    
    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getSupplierPartId() {
        return supplierPartId;
    }

    public void setSupplierPartId(String supplierPartId) {
        this.supplierPartId = supplierPartId;
    }

    public String getPurchaseInvoiceId() {
        return purchaseInvoiceId;
    }

    public void setPurchaseInvoiceId(String purchaseInvoiceId) {
        this.purchaseInvoiceId = purchaseInvoiceId;
    }
}
