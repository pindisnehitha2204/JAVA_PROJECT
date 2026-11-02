package com.bloodbank.model;

import java.time.LocalDate;

public class Donor {
    private String name;
    private String bloodGroup;
    private String city;
    private LocalDate lastDonationDate;
    private boolean available;

    // Constructor without lastDonationDate (defaults to eligible/available)
    public Donor(String name, String bloodGroup, String city, boolean available) {
        this.name = name;
        this.bloodGroup = bloodGroup;
        this.city = city;
        this.available = available;
        this.lastDonationDate = null; // First time donor / default eligible
    }

    // Full Constructor
    public Donor(String name, String bloodGroup, String city, LocalDate lastDonationDate, boolean available) {
        this.name = name;
        this.bloodGroup = bloodGroup;
        this.city = city;
        this.lastDonationDate = lastDonationDate;
        this.available = available;
    }

    public String getName() { return name; }
    public String getBloodGroup() { return bloodGroup; }
    public String getCity() { return city; }
    public LocalDate getLastDonationDate() { return lastDonationDate; }
    public boolean isAvailable() { return available; }
}