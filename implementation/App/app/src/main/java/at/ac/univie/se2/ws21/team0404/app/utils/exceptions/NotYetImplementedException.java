package at.ac.univie.se2.ws21.team0404.app.utils.exceptions;

public class NotYetImplementedException extends RuntimeException {
    public NotYetImplementedException(String field) {
        super(String.format("The field %s is not implemented yet!", field));
    }
}
