package com.tkht.model;

import java.util.Date;

public class Appointment {
    private String id;
    private Integer status;
    private Date scheduledTime;
    private String location;
    private Date appointmentDate;
    private String customerId;

    public Appointment() {}

    public Appointment(String id, Integer status, Date scheduledTime, String location, Date appointmentDate, String customerId) {
        this.id = id;
        this.status = status;
        this.scheduledTime = scheduledTime;
        this.location = location;
        this.appointmentDate = appointmentDate;
        this.customerId = customerId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(Date scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
