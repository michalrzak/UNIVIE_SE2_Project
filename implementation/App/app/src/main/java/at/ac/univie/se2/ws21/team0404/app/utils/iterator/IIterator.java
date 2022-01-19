package at.ac.univie.se2.ws21.team0404.app.utils.iterator;

/**
 * Iterator to be used in conjunction with {@link ICollection} to easily iterate of it's data
 * @param <T> object type to be iterated over
 */
public interface IIterator<T> {

    /**
     * @return if there is another object in the {@link ICollection} that the iterator is iterating over
     */
    boolean hasNext();

    /**
     * @return the next object in the iterator of type {@link T}
     */
    T next();
}
