package at.ac.univie.se2.ws21.team0404.app.utils.iterator;

import java.util.ArrayList;
import java.util.List;

import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;

public class TransactionCollection implements ICollection<Transaction> {

    private List<Transaction> transactions = new ArrayList<>();

    public TransactionCollection() {
    }

    public TransactionCollection(List<Transaction> transactionList) {
        this.transactions = transactionList;
    }

    public void addTransactions(List<Transaction> transactions) {
        this.transactions.addAll(transactions);
    }

    @Override
    public IIterator<Transaction> createIterator() {
        return new TransactionIterator(transactions);
    }
}
