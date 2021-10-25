package org.clemy.androidapps.expense.database;

import junit.framework.TestCase;

import org.clemy.androidapps.expense.model.AccountList;

public class RepositoryTest extends TestCase {

    public void testGetAccounts() {
        Repository repository = Repository.getInstance();
        AccountList accounts = repository.getAccounts().getData();
        assertEquals(2, accounts.getAccountList().size());
    }
}