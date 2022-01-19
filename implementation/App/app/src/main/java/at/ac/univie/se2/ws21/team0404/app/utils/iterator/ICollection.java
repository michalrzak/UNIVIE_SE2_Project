package at.ac.univie.se2.ws21.team0404.app.utils.iterator;

/**
 * Wraps the data in a collection
 * @param <T> type of object that is stored
 */
public interface ICollection<T> {
    /**
     *
     * @return {@link IIterator} of type {@link T} that can iterates of the collection data
     */
    IIterator<T> createIterator();
}
