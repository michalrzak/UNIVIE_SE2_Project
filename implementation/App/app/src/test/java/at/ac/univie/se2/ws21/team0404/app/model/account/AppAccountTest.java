package at.ac.univie.se2.ws21.team0404.app.model.account;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

public class AppAccountTest {

    private AppAccount account;

    private final String name = "Tom";
    private final EAccountType accountType = EAccountType.BANK;
    private final double spendingLimit = 0;

    @Before
    public void setup(){
        account = new AppAccount(name, accountType, spendingLimit);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createNewAccount_invalidNameParameter(){
        new AppAccount("", accountType, spendingLimit);
    }

    @Test
    public void createNewAccount_notEqualId(){
        AppAccount newAccount = new AppAccount(name, accountType, spendingLimit);
        assertNotEquals(account.getId(), newAccount.getId());
    }

    @Test
    public void copyAccount_equal(){
        AppAccount newAccount = new AppAccount(account);
        assertEquals(account, newAccount);
    }

    @Test
    public void createNewAccount_sameNameAndType_equal(){
        AppAccount newAccount = new AppAccount(name, accountType, 9999);
        assertEquals(account, newAccount);
    }

    @Test
    public void setSpendingLimitAndBalance_checkESpendingLevel(){
        assert(account.getBalance() == 0.0);
        assertEquals(account.getSpendingLimitStatus(), ESpendingLevel.NONE);

        double newSpendingLimit = 10;
        account.setSpendingLimit(newSpendingLimit);
        assertEquals(account.getSpendingLimitStatus(), ESpendingLevel.OVER);

        double newBalance = 12;
        account.setBalance(newBalance);
        assertEquals(account.getSpendingLimitStatus(), ESpendingLevel.WARNING);
        newBalance = 10;
        account.setBalance(newBalance);
        assertEquals(account.getSpendingLimitStatus(), ESpendingLevel.WARNING);

    }

    @Test
    public void createNewAccount_sameValuesAsOriginalAccount_equal(){
        AppAccount newAccount = new AppAccount(account.getName(), account.getType(), account.getId(), account.getSpendingLimit(), account.getBalance());
        assertEquals(account, newAccount);
        assertEquals(account.getId(), newAccount.getId());
    }
}
