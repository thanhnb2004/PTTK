package com.tkht.model;

public class Staff {
    private String id;
    private String position;
    private String memberId;

    public Staff() {}

    public Staff(String id, String position, String memberId) {
        this.id = id;
        this.position = position;
        this.memberId = memberId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
}
