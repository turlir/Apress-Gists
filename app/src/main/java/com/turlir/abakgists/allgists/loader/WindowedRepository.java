package com.turlir.abakgists.allgists.loader;

import android.support.annotation.NonNull;

import com.turlir.abakgists.model.Identifiable;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

public abstract class WindowedRepository<T extends Identifiable<T>> {

    @NonNull
    protected Window range;
    private final WindowDiffer differ;

    public WindowedRepository(@NonNull Window start) {
        range = start;
        differ = new WindowDiffer();
    }

    @NonNull
    public Window getRange() {
        return range;
    }

    public abstract Single<Integer> loadAnPut(LoadablePage page);

    public abstract Single<Integer> loadAndReplace(Window start);

    protected abstract int computeApproximateSize();

    protected abstract Flowable<List<T>> subscribe(Window w);

    protected LoadablePage page(Window w) {
        return differ.page(w);
    }

    public final LoadablePage requiredPage() {
        int inList = computeApproximateSize();
        Window already = differ.cut(range, inList);
        Window required = differ.diff(range, already);
        return differ.page(required);
    }

    public LoadablePage page() {
        return differ.page(range);
    }

    public final Flowable<List<T>> firstPage() {
        return subscribe(range);
    }

    public final Flowable<List<T>> nextPage() {
        range = range.next();
        return subscribe(range);
    }

    public final Flowable<List<T>> prevPage() {
        range = range.prev();
        return subscribe(range);
    }
}