package com.tkht.model;

public class SaleInvoice_PurchaseInvoiceSupplierPart {
    private String id;
    private Integer quantity;
    private Float price;
    private String purchaseInvoiceSupplierPartId;

    public SaleInvoice_PurchaseInvoiceSupplierPart() {}

    public SaleInvoice_PurchaseInvoiceSupplierPart(String id, Integer quantity, Float price, String purchaseInvoiceSupplierPartId) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
        this.purchaseInvoiceSupplierPartId = purchaseInvoiceSupplierPartId;
      
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getPurchaseInvoiceSupplierPartId() {
        return purchaseInvoiceSupplierPartId;
    }

    public void setPurchaseInvoiceSupplierPartId(String purchaseInvoiceSupplierPartId) {
        this.purchaseInvoiceSupplierPartId = purchaseInvoiceSupplierPartId;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }


}
