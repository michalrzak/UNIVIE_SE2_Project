package at.ac.univie.se2.ws21.team0404.app.database;

import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.SingletonAlreadyInstantiatedException;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.SingletonNotInstantiatedException;

public class Repository {
    private IDatabase databaseStrategy;
    private static Repository instance;

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

    public IDatabase getDb() {
        return this.databaseStrategy;
    }
}
