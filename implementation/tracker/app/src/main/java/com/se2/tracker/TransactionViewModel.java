package com.se2.tracker;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TransactionViewModel extends AndroidViewModel {

    private TransactionRepository transactionRepository;

    private LiveData<List<Transaction>> transactionList;

    public TransactionViewModel(Application application){
        super(application);
        transactionRepository = new TransactionRepository(application);
        transactionList = transactionRepository.getTransactionList();
    }

    LiveData<List<Transaction>> getTransactionList(){
        return transactionList;
    }

    public void insert(Transaction transaction) {transactionRepository.insert(transaction);}

    public void deleteAll() {transactionRepository.deleteAll();}

}
