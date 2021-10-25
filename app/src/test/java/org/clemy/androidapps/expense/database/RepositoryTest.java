package org.clemy.androidapps.expense.database;

import junit.framework.TestCase;

import org.clemy.androidapps.expense.model.AccountList;

public class RepositoryTest extends TestCase {

    public void testGetAccounts() {
        Repository repository = new Repository();
        AccountList accounts = repository.getAccounts();
        assertEquals(2, accounts.getAccountList().size());
    }
}