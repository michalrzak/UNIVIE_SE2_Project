package at.ac.univie.se2.ws21.team0404.app.model.account;

public enum ESpendingLevel {
    NONE(0xFFFFFFFF), WARNING(0xFFEBCC34), OVER(0xFFEB4034);

    private final int colour;

    ESpendingLevel(int colour) {
        this.colour = colour;
    }

    public int getColour() {
        return colour;
    }
}
