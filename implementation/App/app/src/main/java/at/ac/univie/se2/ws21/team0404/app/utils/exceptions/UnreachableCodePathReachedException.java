package at.ac.univie.se2.ws21.team0404.app.utils.exceptions;

public class UnreachableCodePathReachedException extends RuntimeException {
    public UnreachableCodePathReachedException(String field) {
        super(String.format("An unreachable code path has been reached! Error: %s", field));
    }
}
