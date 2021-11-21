package at.ac.univie.se2.ws21.team0404.app.utils.exceptions;

public class SingletonAlreadyInstantiatedException extends RuntimeException {

  public SingletonAlreadyInstantiatedException(String singletonName) {
    super(String.format("The singleton %s attempted to be created is already instantiated!",
        singletonName));
  }
}
