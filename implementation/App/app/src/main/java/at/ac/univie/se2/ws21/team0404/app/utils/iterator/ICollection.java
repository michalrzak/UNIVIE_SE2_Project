package at.ac.univie.se2.ws21.team0404.app.utils.iterator;

public interface ICollection<T> {
    IIterator<T> createIterator();
}
