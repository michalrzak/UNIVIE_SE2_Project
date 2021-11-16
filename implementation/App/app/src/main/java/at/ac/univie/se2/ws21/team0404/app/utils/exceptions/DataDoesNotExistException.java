package at.ac.univie.se2.ws21.team0404.app.utils.exceptions;

import java.io.IOException;

/**
 * Exception class, used to signal that some data is not found within a field
 */
public class DataDoesNotExistException extends IOException {
    public DataDoesNotExistException(String fieldName){
        super(String.format("Data does not exists in field with name %s", fieldName));
    }
}
