package at.ac.univie.se2.ws21.team0404.app.utils.exceptions;

import java.io.IOException;

/**
 * A checked exception that is used to signal that the data (with the same e.g. hashCode or primary
 * key) already exists
 */
public class DataExistsException extends IOException {

  public DataExistsException(String dataFieldName) {
    super(String.format("The data tried to save in %s already exists!", dataFieldName));
  }
}
