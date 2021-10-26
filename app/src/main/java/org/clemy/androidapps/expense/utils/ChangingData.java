package org.clemy.androidapps.expense.utils;

import androidx.annotation.NonNull;

// Observer Pattern: notifies observer if data changes
public interface ChangingData<T> {
    public interface Observer<TObserve> {
        public void changed(TObserve data);
    }

    T getData();
    void setData(T data);

    void observe(@NonNull Observer<T> observer);
    void unobserve(@NonNull Observer<T> observer);
}
