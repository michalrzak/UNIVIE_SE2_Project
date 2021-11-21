package at.ac.univie.se2.ws21.team0404.app.database;

// TODO: do we even remove log from here
import android.util.Log;
import at.ac.univie.se2.ws21.team0404.app.utils.NonNull;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataDoesNotExistException;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataExistsException;

public class MemoryDatabase implements IDatabase{

    private final Set<AppAccount> accounts = new HashSet<>();
    private final Map<String, Category> categories = new HashMap<>();
    private final Map<AppAccount, Set<Transaction>> transactions = new HashMap<>();

    /**
     * This function validates the account. It introduces a side affect as when an account is not yet added in the transactions hash map it will add it.
     *
     * TODO: Rework this. The entire account add/delete concept of the db should be rethought
     *
     * @param account AppAccount which is getting validated
     * @throws DataDoesNotExistException when account does not exist in database
     */
    private void validateAccount(AppAccount account) throws DataDoesNotExistException {
        if (!accounts.contains(account)) {
            throw new DataDoesNotExistException("accounts");
        }

        if (!transactions.containsKey(account)) {
            transactions.put(account, new HashSet<>());
        }
    }


    @NonNull
    @Override
    public Collection<AppAccount> getAccounts(){
        return accounts;
    }

    @NonNull
    @Override
    public Collection<Category> getCategories() {
        return categories.values();
    }

    @NonNull
    @Override
    public Collection<Transaction> getTransactions(@NonNull AppAccount account) throws DataDoesNotExistException {
        validateAccount(account);

        Set<Transaction> ret = transactions.get(account);
        assert (ret != null);
        return ret;
    }

    @Override
    public void addAccount(@NonNull AppAccount newAccount) throws DataExistsException {
        if (newAccount.getName().isEmpty())
            throw new IllegalArgumentException("Account has to have a name");
        if (!accounts.add(newAccount)) {
            throw new DataExistsException("accounts");
        }
    }

    @Override
    public void deleteAccount(AppAccount newAccount) throws DataDoesNotExistException {
        if(!accounts.remove(newAccount))
            throw new DataDoesNotExistException("account");
    }

    @Override
    public void addCategory(@NonNull Category newCategory) throws DataExistsException {
        if(categories.containsKey(newCategory.getName())) {
            throw new DataExistsException("categories");
        }
        categories.put(newCategory.getName(), newCategory);
    }

    @Override
    public void addTransaction(@NonNull AppAccount owner, @NonNull Transaction newTransaction)
            throws DataExistsException, DataDoesNotExistException {

        validateAccount(owner);

        if (!transactions.get(owner).add(newTransaction)){
            throw new DataExistsException("transactions");
        }
    }


    @Override
    public void updateCategory(@NonNull String categoryName, @NonNull Category newCategory) throws DataDoesNotExistException {
        if (!categories.containsKey(categoryName)) {
            throw new DataDoesNotExistException("categories");
        }

        categories.replace(categoryName, newCategory);
    }

    @Override
    public void updateTransaction(AppAccount owner, int oldId, Transaction updatedTransaction) throws DataDoesNotExistException {

        Log.d("MemDB_updateTx", "started update of transaction with id: " + oldId);
        validateAccount(owner);
        Log.d("MemDB_updateTx", "account valid");


        Set<Transaction> transactionList = transactions.get(owner);
        assert (transactionList != null);

        Optional<Transaction> oldTransaction = transactionList.stream().filter(transactionItem -> transactionItem.getId() == oldId).findFirst();


        if (!oldTransaction.isPresent()) {
            throw new DataDoesNotExistException("transaction");
        }

        transactionList.remove(oldTransaction.get());
        transactionList.add(updatedTransaction);
        Log.d("MemDB_updateTx", "successfully updated transaction in database");

    }
}
