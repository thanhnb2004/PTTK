package com.tkht.model;

public class Part {
    private String id;
    private String unit;
    private String name;

    public Part() {}

    public Part(String id, String unit, String name) {
        this.id = id;
        this.unit = unit;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
