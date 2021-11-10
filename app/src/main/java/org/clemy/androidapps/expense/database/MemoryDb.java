package org.clemy.androidapps.expense.database;

import androidx.annotation.NonNull;

import org.clemy.androidapps.expense.model.Account;
import org.clemy.androidapps.expense.model.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public class MemoryDb implements Db {
    private final Map<Integer, Account> accounts = new TreeMap<>();
    private final Map<Integer, List<Transaction>> transactions = new HashMap<>();
    private Integer idGenerator = 1;

    public MemoryDb() {
    }

    @NonNull
    @Override
    public synchronized List<Account> getAccounts() {
        return new ArrayList<>(accounts.values());
    }

    @Override
    public synchronized Optional<Account> getAccount(@NonNull Integer accountId) {
        return Optional.ofNullable(accounts.get(accountId));
    }

    @Override
    public synchronized void addAccount(@NonNull Account account) {
        Integer accountId = idGenerator++;
        accounts.put(accountId,
                new Account(
                        accountId,
                        account.getName(),
                        account.getType(),
                        account.getOverdueLimitOptional().orElse(null)));
    }

    @Override
    public synchronized List<Transaction> getTransactionsForAccount(@NonNull Integer accountId) {
        List<Transaction> list = transactions.get(accountId);
        if (list == null) {
            return new ArrayList<>();
        }
        return list;
    }

    @Override
    public synchronized void addTransaction(@NonNull Transaction transaction) {
        List<Transaction> list = transactions.get(transaction.getAccountId());
        if (list == null) {
            list = new ArrayList<>();
        } else {
            // copy old list
            list = new ArrayList<>(list);
        }
        list.add(new Transaction(idGenerator++, transaction.getAccountId(), transaction.getName(), transaction.getAmount()));
        transactions.put(transaction.getAccountId(), list);
    }
}
