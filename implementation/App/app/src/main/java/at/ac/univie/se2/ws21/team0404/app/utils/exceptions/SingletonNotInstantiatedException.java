package at.ac.univie.se2.ws21.team0404.app.utils.exceptions;

public class SingletonNotInstantiatedException extends RuntimeException {

  public SingletonNotInstantiatedException(String singletonName) {
    super(String
        .format("The singleton %s attempted to be accessed is not instantiated!", singletonName));
  }
}
