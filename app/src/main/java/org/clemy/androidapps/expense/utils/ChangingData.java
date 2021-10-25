package org.clemy.androidapps.expense.utils;

import java.util.HashSet;
import java.util.Set;

public class ChangingData<T> {

    public interface Observer<TObserve> {
        public void changed(TObserve data);
    }

    private T data;
    private final Set<Observer<T>> observers = new HashSet<>();

    public ChangingData() {
    }

    public ChangingData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
        for (Observer<T> observer: observers) {
            observer.changed(this.data);
        }
    }

    public void observe(Observer<T> observer) {
        observers.add(observer);
        observer.changed(this.data);
    }

    public void unobserve(Observer<T> observer) {
        observers.remove(observer);
    }
}
