/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1;

import java.time.LocalDate;

/**
 *
 * @author krasipetranov
 */
public class Booking {
    private int bookingId;
    private LocalDate date;
    private String timeSlot;
    private String status;      
    private Member member;
    private Pitch pitch;
    private Payment payment;

    public Booking(int bookingId, LocalDate date, String timeSlot, Member member, Pitch pitch) {
        this.bookingId = bookingId;
        this.date = date;
        this.timeSlot = timeSlot;
        this.member = member;
        this.pitch = pitch;
        this.status = "Confirmed";
    }

    public void confirmBooking() { 
        status = "Confirmed"; 
    }
    
    public void cancelBooking() { 
        status = "Cancelled"; 
    }
    
    public boolean checkEligibility() {
        return member.getMembership() != null && member.getMembership().isValid();
    }

    public void setPayment(Payment p) { 
        this.payment = p; 
    }
    
    public LocalDate getDate() { 
        return date; 
    }
    
    public String getTimeSlot() { 
        return timeSlot; 
    }
    
    public Pitch getPitch() { 
        return pitch; 
    }
    public double getPrice() { 
        return pitch.calculatePrice(); 
    }
    
    @Override
    public String toString() {
        return "Booking #" + bookingId + " - " + pitch.getName() + " on " + date + " " + timeSlot;
    }

}
