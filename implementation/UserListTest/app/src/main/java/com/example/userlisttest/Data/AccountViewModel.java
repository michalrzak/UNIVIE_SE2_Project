package com.example.userlisttest.Data;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class AccountViewModel extends AndroidViewModel {

    private AccountRepository accountRepository;
    private LiveData<List<Account>> allAccounts;

    // the tutorial says never to pass context into a View class
    // is this not a context?
    public AccountViewModel (Application application) {
        super(application);
        accountRepository = new AccountRepository(application); // this uses a singleton on the inside so it is fine to create a new one
        allAccounts = accountRepository.getAllAccounts();
    }

    public LiveData<List<Account>> getAllAccounts() {
        return allAccounts;
    }

    public void insert(Account account) {
        accountRepository.insert(account);
    }

}
