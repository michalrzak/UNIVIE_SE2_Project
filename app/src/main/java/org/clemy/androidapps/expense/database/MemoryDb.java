package org.clemy.androidapps.expense.database;

import androidx.annotation.NonNull;

import org.clemy.androidapps.expense.model.Account;
import org.clemy.androidapps.expense.model.AccountList;
import org.clemy.androidapps.expense.model.Transaction;
import org.clemy.androidapps.expense.model.TransactionList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemoryDb implements Db {
    private Integer idGenerator = 1;
    private List<Account> accounts = new ArrayList<>();
    private Map<Integer, List<Transaction>> transactions = new HashMap<>();

    public MemoryDb() {
    }

    @NonNull
    @Override
    public synchronized AccountList getAccounts() {
        return new AccountList(accounts);
    }

    @Override
    public synchronized void addAccount(@NonNull Account account) {
        // do not change the old list!
        accounts = new ArrayList<>(accounts);
        accounts.add(new Account(idGenerator++, account.getName(), account.getType(), account.getOverdueLimit()));
    }

    @Override
    public synchronized TransactionList getTransactionsForAccount(Integer accountId) {
        List<Transaction> list = transactions.get(accountId);
        if (list == null) {
            return new TransactionList(new ArrayList<>());
        }
        return new TransactionList(list);
    }

    @Override
    public synchronized void addTransaction(@NonNull Transaction transaction) {
        List<Transaction> list = transactions.get(transaction.getAccountId());
        if (list == null) {
            list = new ArrayList<>();
        } else {
            list = new ArrayList<>(list);
        }
        list.add(new Transaction(idGenerator++, transaction.getAccountId(), transaction.getName(), transaction.getAmount()));
        transactions.put(transaction.getAccountId(), list);
    }
}
