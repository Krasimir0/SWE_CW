package com.mycompany.mavenproject1;


import com.mycompany.mavenproject1.Booking;
import com.mycompany.mavenproject1.Member;
import com.mycompany.mavenproject1.Payment;
import com.mycompany.mavenproject1.Pitch;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author krasipetranov
 */
public class BookPitchController {
    private List<Member> members;
    private List<Pitch> pitches;
    private List<Booking> bookings;
    private Member currentMember;
    private Pitch selectedPitch;
    private int nextBookingId = 1001;
    private int nextPaymentId = 5001;

    public BookPitchController() {
        members = new ArrayList<>();
        pitches = new ArrayList<>();
        bookings = new ArrayList<>();
    }
    
    public Member authenticate(String email, String password) {
        for (Member m : members) {
            if (m.login(email, password)) {
                currentMember = m;
                return m;
            }
        }
        return null;
    }

    public List<Pitch> searchAvailablePitches(String location, LocalDate date) {
        List<Pitch> available = new ArrayList<>();
        for (Pitch p : pitches) {
            if (p.checkAvailability() && p.getLocation().equalsIgnoreCase(location)) {
                available.add(p);
            }
        }
        return available;
    }

    public String checkMembershipValidity() {
        Membership mem = currentMember.getMembership();
        if (mem == null) return "No membership found";
        mem.applyGracePeriod();  // update status if expired but within grace
        if (mem.isValid()) return "Valid";
        else return "Overdue – renewal required";
    }

    public Booking initiateBooking(Pitch pitch, LocalDate date, String timeSlot) {
        if (!pitch.checkAvailability()) return null;
        if (!currentMember.getMembership().isValid()) return null;
        selectedPitch = pitch;
        Booking b = new Booking(nextBookingId++, date, timeSlot, currentMember, pitch);
        return b;
    }

    public Payment processPayment(Booking booking) {
        double amount = booking.getPrice();
        Payment p = new Payment(nextPaymentId++, amount);
        p.processPayment();
        booking.setPayment(p);
        booking.getPitch().updateAvailability(false); // mark unavailable
        currentMember.addBooking(booking);
        bookings.add(booking);
        return p;
    }

    public Member getCurrentMember() { 
        return currentMember;
    }
    
    public List<String> getLocations() {
        List<String> locs = new ArrayList<>();
        for (Pitch p : pitches) if (!locs.contains(p.getLocation())) locs.add(p.getLocation());
        return locs;
    }
}
