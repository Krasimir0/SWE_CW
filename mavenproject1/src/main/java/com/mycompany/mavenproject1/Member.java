/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author krasipetranov
 */
public class Member {
    private int memberId;
    private String name;
    private String email;
    private String phone;
    private String password;
    private Membership membership;
    private List<Booking> bookings;

    public Member(int memberId, String name, String email, String phone, String password) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.bookings = new ArrayList<>();
    }

    public boolean login(String email, String password) {
        return this.email.equals(email) && this.password.equals(password);
    }

    public void register() { /* simplified */ }
    public void updateDetails(String email, String phone) {
        this.email = email;
        this.phone = phone;
    }
    public List<Booking> viewBookings() { return bookings; }
    public void addBooking(Booking b) { bookings.add(b); }

    // Getters & setters
    public int getMemberId() { return memberId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public Membership getMembership() { return membership; }
    public void setMembership(Membership m) { this.membership = m; }
}
