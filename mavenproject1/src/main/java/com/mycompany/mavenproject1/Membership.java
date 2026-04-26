/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author krasipetranov
 */
class Membership {
    private int membershipId;
    private String type;       
    private LocalDate expiryDate;
    private String status;     

    public Membership(int membershipId, String type, LocalDate expiry, String status) {
        this.membershipId = membershipId;
        this.type = type;
        this.expiryDate = expiry;
        this.status = status;
    }

    public boolean isValid() {
        if (status.equals("Lapsed")) return false;
        if (status.equals("Active")) return true;
        if (status.equals("Grace") && ChronoUnit.DAYS.between(expiryDate, LocalDate.now()) <= 30)
            return true;
        return false;
    }

    public void renewMembership() {
        expiryDate = expiryDate.plusYears(1);
        status = "Active";
    }

    public void applyGracePeriod() {
        if (status.equals("Active") && ChronoUnit.DAYS.between(expiryDate, LocalDate.now()) > 0)
            status = "Grace";
    }

    public String checkStatus() {
        return status; 
    }
    
    public String getType() { 
        return type; 
    }
    
    public LocalDate getExpiryDate() { 
        return expiryDate; 
    }
}
