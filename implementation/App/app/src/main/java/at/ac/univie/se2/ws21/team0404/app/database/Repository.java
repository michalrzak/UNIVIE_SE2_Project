package at.ac.univie.se2.ws21.team0404.app.database;

import java.util.ArrayList;
import java.util.List;

import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.utils.ChangingData;
import at.ac.univie.se2.ws21.team0404.app.utils.ChangingDataImpl;
import at.ac.univie.se2.ws21.team0404.app.utils.NonNull;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataDoesNotExistException;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataExistsException;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.SingletonAlreadyInstantiatedException;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.SingletonNotInstantiatedException;

public class Repository {
    private IDatabase databaseStrategy;
    private static Repository instance;

    private final ChangingData<List<AppAccount>> accountList =
            new ChangingDataImpl<>(new ArrayList<>());

    private Repository(IDatabase databaseStrategy) {
        this.databaseStrategy = databaseStrategy;
    }

    public static void create(IDatabase databaseStrategy) {
        if (instance != null) {
            throw new SingletonAlreadyInstantiatedException("Repository");
        }
        instance = new Repository(databaseStrategy);
    }

    public static Repository getInstance() {
        if (instance == null) {
            throw new SingletonNotInstantiatedException("Repository");
        }
        return instance;
    }

    public void setDatabaseStraregy(IDatabase databaseStrategy) {
        this.databaseStrategy = databaseStrategy;
    }

    public IDatabase getDatabase() {
        return this.databaseStrategy;
    }

    public ChangingData<List<AppAccount>> getAccountList(){
        reloadAccounts();
        return accountList;
    }

    public void createAppAccount(@NonNull AppAccount appAccount) throws DataExistsException {
        databaseStrategy.addAccount(appAccount);
        reloadAccounts();
    }

    public void deleteAppAccount(@NonNull AppAccount appAccount) throws DataDoesNotExistException {
        databaseStrategy.deleteAccount(appAccount);
        reloadAccounts();
    }

    private void reloadAccounts(){
        accountList.setData(new ArrayList<AppAccount>(databaseStrategy.getAccounts()));
    }
}
