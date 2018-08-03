package com.turlir.abakgists.api;

import com.turlir.abakgists.api.data.GistLocal;
import com.turlir.abakgists.api.data.GistLocalDao;
import com.turlir.abakgists.api.data.GistMapper;
import com.turlir.abakgists.api.data.ListGistMapper;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

public class Repository {

    private final ApiClient mClient;
    private final GistLocalDao mDao;
    private final ListGistMapper.Json mTransformer;

    public Repository(ApiClient client, GistLocalDao dao) {
        mClient = client;
        mDao = dao;
        mTransformer = new ListGistMapper.Json(new GistMapper.Json());
    }

    /**
     * Скачивает очередную страницу с сервера и сохраняет ее в БД
     *
     * @param page номер загружаемой страницы, больше 1
     * @return сохраненные в БД элементы
     */
    public Single<Integer> server(int page, int count) {
        return loadFromServer(page, count)
                .map(this::putToCache);
    }

    public Flowable<List<GistLocal>> database(int limit, int offset) {
        return mDao.partial(limit, offset);
    }

    ///
    /// private
    ///

    private Single<List<GistLocal>> loadFromServer(int page, int perPage) {
        if (page < 1) throw new IllegalArgumentException();
        return mClient.publicGist(page, perPage)
                .map(mTransformer);
    }

    private Integer putToCache(List<GistLocal> gists) {
        mDao.insertAll(gists);
        return gists.size();
    }

}
