/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject1;

import java.time.LocalDateTime;

/**
 *
 * @author krasipetranov
 */
public class Payment {
    private int paymentId;
    private double amount;
    private LocalDateTime paymentDate;
    private String transactionRef;
    private boolean success;

    public Payment(int paymentId, double amount) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.paymentDate = LocalDateTime.now();
        this.transactionRef = "TXN" + System.currentTimeMillis();
    }

    public boolean processPayment() {
        this.success = true;
        return true;
    }

    public boolean validatePayment() {
        return amount > 0;
    }
    
    public boolean isConfirmed() { 
        return success;
    }
    
    public String getTransactionRef() {
        return transactionRef;
    }
}
