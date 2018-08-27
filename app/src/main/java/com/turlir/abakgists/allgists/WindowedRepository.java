package com.turlir.abakgists.allgists;

import android.support.annotation.NonNull;

import com.turlir.abakgists.model.Identifiable;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

abstract class WindowedRepository<T extends Identifiable<T>> {

    @NonNull
    Window range;

    WindowedRepository(@NonNull Window start) {
        range = start;
    }

    final LoadablePage requiredPage() {
        int inList = computeApproximateSize();
        Window already = range.cut(inList);
        Window required = range.diff(already);
        return required.page();
    }

    abstract int computeApproximateSize();

    public abstract Flowable<List<T>> firstPage();

    public abstract Flowable<List<T>> nextPage();

    public abstract Single<Integer> server(LoadablePage page);

    public abstract Flowable<List<T>> prevPage();

    public abstract Single<Integer> loadAndReplace(Window start);
}
