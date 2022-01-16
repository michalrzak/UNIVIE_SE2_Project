package at.ac.univie.se2.ws21.team0404.app.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;

import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.utils.iterator.AccountCollection;
import at.ac.univie.se2.ws21.team0404.app.utils.iterator.IIterator;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class IteratorTest {

    IIterator<AppAccount> iterator;
    List<AppAccount> accounts;
    AccountCollection accountCollection;

    @Mock
    AppAccount mockedAccount1;
    @Mock
    AppAccount mockedAccount2;

    @Before
    public void setup(){
        accounts = Arrays.asList(mockedAccount1, mockedAccount2);
        accountCollection = new AccountCollection(accounts);
        iterator = spy(accountCollection.createIterator());
    }

    @Test
    public void Iterator_iterateUntilTheEnd_verifyEveryElementWasIterated(){
        while (iterator.hasNext()){
            iterator.next().getId();
        }
        for (AppAccount account : accounts){
            verify(account, times(1)).getId();
        }
    }

    @Test
    public void Iterator_iterateUntilTheEnd_verifyIteratorMethodCallCount(){
        while (iterator.hasNext()){
            iterator.next();
        }
        verify(iterator, times(accounts.size())).next();
        verify(iterator, times(accounts.size() + 1)).hasNext();
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void Iterator_continueIteratingAfterLastElement_throwException(){
        while (iterator.hasNext()){
            iterator.next();
        }
        iterator.next();
    }
    @Test
    public void Iterator_createIterator_assertNotNull(){
        assertNotNull(accountCollection.createIterator());
    }

    @Test
    public void Iterator_iterateUntilTheEnd_assertHasNext(){
        while (iterator.hasNext()){
            iterator.next();
        }
        assertFalse(iterator.hasNext());
    }
}