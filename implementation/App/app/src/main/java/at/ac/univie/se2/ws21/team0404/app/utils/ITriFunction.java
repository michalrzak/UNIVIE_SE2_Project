package at.ac.univie.se2.ws21.team0404.app.utils;

@FunctionalInterface
public interface ITriFunction<Input1, Input2, Input3, Output> {
  Output apply(Input1 input1, Input2 input2, Input3 input3);

}
