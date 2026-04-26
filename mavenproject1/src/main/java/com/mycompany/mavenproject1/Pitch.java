/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1;

/**
 *
 * @author krasipetranov
 */
public class Pitch {
     private int pitchId;
    private String name;
    private String location;
    private double price;
    private boolean availability;

    public Pitch(int pitchId, String name, String location, double price) {
        this.pitchId = pitchId;
        this.name = name;
        this.location = location;
        this.price = price;
        this.availability = true;
    }

    public boolean checkAvailability() { 
        return availability; 
    }
    
    public void updateAvailability(boolean available) { 
        this.availability = available; 
    }
    
    public double calculatePrice() { 
        return price; 
    }

    public int getPitchId() { 
        return pitchId; 
    }
    
    public String getName() { 
        return name; 
    }
    
    public String getLocation() { 
        return location; 
    }
    
    public double getPrice() { 
        return price; 
    }
    
    @Override
    public String toString() {
        return name + " at " + location + " (£" + price + ")";
    }
}
