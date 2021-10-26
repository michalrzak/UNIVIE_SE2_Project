package org.clemy.androidapps.expense.utils;

import androidx.annotation.NonNull;

// Decorator Pattern: it is possible that there are multiple
// features which can be added to this class
abstract class ChangingDataDecorator<T> implements ChangingData<T> {
    @NonNull
    protected final ChangingData<T> decoratedChangingData;

    protected ChangingDataDecorator(@NonNull ChangingData<T> changingData) {
        this.decoratedChangingData = changingData;
    }

    @Override
    public T getData() {
        return decoratedChangingData.getData();
    }

    @Override
    public void setData(T data) {
        decoratedChangingData.setData(data);
    }

    @Override
    public void observe(@NonNull Observer<T> observer) {
        decoratedChangingData.observe(observer);
    }

    @Override
    public void unobserve(@NonNull Observer<T> observer) {
        decoratedChangingData.unobserve(observer);
    }
}
