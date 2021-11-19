package at.ac.univie.se2.ws21.team0404.app.model.account;

/**
 * An Enum to make sure that the name of the variable stays consistent when passed through activities.
 * Should be used when calling the putExtra(...) and getStringExtra(...) methods
 */

// probably going to put it in another package. Also not sure if we should just use public static final variables instead of an enum
public enum EIntentExtra {
    ACCOUNT_NAME("AccountName"), TRANSACTION_NAME("TransactionName");

    private final String value;

    EIntentExtra(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
