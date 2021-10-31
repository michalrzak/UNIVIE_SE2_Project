package com.se2.tracker;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Transaction {

    @PrimaryKey
    public int transactionId;

    public double amount;
    public String category;

    public Transaction(int transactionId, double amount, String category){
        this.transactionId = transactionId;
        this.amount = amount;
        this.category = category;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }
}
