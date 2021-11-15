package at.ac.univie.se2.ws21.team0404.app.model.account;

/**
 * An Enum specifying the different account types available to the user.
 * May be replaced later with something more complex, which allows to add accounts runtime.
 */


public enum EAccountType {
    CASH("Cash"), BANK("Bank"), CARD("Card"), STOCK("Stock");

    private final String type;

    EAccountType(String type){
        this.type = type;
    }

    @Override
    public String toString(){
        return type;
    }
}
