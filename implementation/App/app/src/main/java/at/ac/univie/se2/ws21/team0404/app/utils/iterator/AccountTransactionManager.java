package at.ac.univie.se2.ws21.team0404.app.utils.iterator;

import java.util.List;

import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.model.common.ETransactionType;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;
import at.ac.univie.se2.ws21.team0404.app.utils.NonNull;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataDoesNotExistException;

// will move this to another package later
// name will probably also be changed
public class AccountTransactionManager {
    private final TransactionCollection transactionCollection = new TransactionCollection();

    public AccountTransactionManager(@NonNull AccountCollection accountCollection) {
        IIterator<AppAccount> iterator = accountCollection.createIterator();
        Repository repository = Repository.getInstance();

        while (iterator.hasNext()) {
            AppAccount appAccount = iterator.next();
            try {
                List<Transaction> transactionList = repository.getTransactionList(appAccount).getData();
                transactionCollection.addTransactions(transactionList);
            } catch (DataDoesNotExistException e) {
                throw new RuntimeException("Error with the Repository");
            }
        }
    }

    public double calculateBalance() {
        double balance = 0;
        IIterator<Transaction> iterator = transactionCollection.createIterator();
        while (iterator.hasNext()) {
            Transaction transaction = iterator.next();
            if (transaction.getType() == ETransactionType.INCOME)
                balance += transaction.getAmount();
            else
                balance -= transaction.getAmount();
        }
        return balance;
    }
}
