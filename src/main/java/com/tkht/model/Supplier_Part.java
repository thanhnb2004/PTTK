package com.tkht.model;

public class Supplier_Part {
    private String id;
    private String supplierId;
    private String partId;
    

    public Supplier_Part() {}
    public Supplier_Part(String id, String supplierId, String partId) {
        this.id = id;
        this.supplierId = supplierId;
        this.partId = partId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getPartId() {
        return partId;
    }

    public void setPartId(String partId) {
        this.partId = partId;
    }


}
