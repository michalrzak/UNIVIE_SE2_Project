package at.ac.univie.se2.ws21.team0404.app.model.android;

import static org.junit.Assert.assertEquals;

import android.os.Parcel;

import org.junit.Before;
import org.junit.Test;

import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.model.account.EAccountType;

public class ParcelableAccountTest {

    private AppAccount account;
    private ParcelableAppAccount parcelableAppAccount;

    @Before
    public void setup() {
        String name = "Tom";
        final EAccountType accountType = EAccountType.BANK;
        double spendingLimit = 0;
        account = new AppAccount(name, accountType, spendingLimit);
        parcelableAppAccount = new ParcelableAppAccount(account);
    }

    @Test
    public void CompareAccountAndParcelableAccount_equal(){
        assertEquals(account, parcelableAppAccount);
    }

    @Test
    public void wrapParcelableAccountInParcel_getContent_equal(){
        Parcel parcel = Parcel.obtain();
        parcelableAppAccount.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);

        ParcelableAppAccount unpackedParcelableAccount = new ParcelableAppAccount(parcel);
        assertEquals(unpackedParcelableAccount, account);
        assertEquals(unpackedParcelableAccount, parcelableAppAccount);
    }
}
