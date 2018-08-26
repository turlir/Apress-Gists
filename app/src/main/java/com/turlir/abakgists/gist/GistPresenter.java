package com.turlir.abakgists.gist;

import com.turlir.abakgists.api.data.GistLocalDao;
import com.turlir.abakgists.base.BasePresenter;
import com.turlir.abakgists.model.GistModel;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.ResourceCompletableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class GistPresenter extends BasePresenter<GistActivity> {

    private final EqualsSolver mSolver;
    private final GistLocalDao mDao;

    public GistModel content;

    public GistPresenter(EqualsSolver solver, GistLocalDao dao) {
        mSolver = solver;
        mDao = dao;
    }

    void attach(GistActivity view, GistModel model) {
        super.attach(view);
        content = model;
    }

    boolean isChange(String desc, String note) {
        GistModel now = new GistModel(content, desc, note);
        return mSolver.solveModel(content, now);
    }

    void transact(String desc, String note) {
        Completable.fromRunnable(() -> mDao.update(content.id, desc, note))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResourceCompletableObserver() {
                    @Override
                    public void onComplete() {
                        dispose();
                        Timber.v("update gist in Presenter success");
                        content = new GistModel(content, desc, note);
                        if (getView() != null) {
                            getView().updateSuccess();
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        dispose();
                        Timber.d(e, "update gist in GistPresenter failure");
                    }
                });
    }

    public void delete() {
        Completable.fromRunnable(() -> mDao.deleteById(content.id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResourceCompletableObserver() {
                    @Override
                    public void onComplete() {
                        dispose();
                        Timber.v("gist successfully deleted");
                        if (getView() != null) {
                            getView().deleteSuccess();
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        dispose();
                        Timber.d(e, "failure delete gist");
                        if (getView() != null) {
                            getView().deleteFailure();
                        }
                    }
                });
    }
}
