package at.ac.univie.se2.ws21.team0404.app.utils.iterator;

import java.util.List;

import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;

public class TransactionIterator implements IIterator<Transaction> {

    private final List<Transaction> transactions;
    private int pos = 0;

    public TransactionIterator(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public boolean hasNext() {
        return pos < transactions.size();
    }

    @Override
    public Transaction next() {
        return transactions.get(pos++);
    }
}