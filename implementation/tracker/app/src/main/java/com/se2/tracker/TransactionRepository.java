package com.se2.tracker;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TransactionRepository {

    private TransactionDAO transactionDAO;
    private LiveData<List<Transaction>> transactionList;

    TransactionRepository(Application application){
        TransactionDatabase db = TransactionDatabase.getDatabase(application);
        transactionDAO = db.transactionDAO();
        transactionList = transactionDAO.getAll();
    }

    LiveData<List<Transaction>> getTransactionList(){
        transactionList = transactionDAO.getAll();
        return transactionList;
    }

    void insert(Transaction transaction){
        TransactionDatabase.databaseWriteExecutor.execute(() -> transactionDAO.insert(transaction));
    }

    void deleteAll(){
        TransactionDatabase.databaseWriteExecutor.execute(() -> transactionDAO.deleteAll());
    }
}
