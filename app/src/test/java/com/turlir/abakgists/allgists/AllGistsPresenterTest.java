package com.turlir.abakgists.allgists;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

import com.google.common.collect.Lists;
import com.turlir.abakgists.BuildConfig;
import com.turlir.abakgists.DatabaseMocking;
import com.turlir.abakgists.allgists.view.AllGistsFragment;
import com.turlir.abakgists.api.ApiClient;
import com.turlir.abakgists.api.Repository;
import com.turlir.abakgists.api.data.GistJson;
import com.turlir.abakgists.api.data.GistLocal;
import com.turlir.abakgists.di.AppComponent;
import com.turlir.abakgists.model.GistModel;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.cosenonjaviste.daggermock.DaggerMockRule;
import it.cosenonjaviste.daggermock.InjectFromComponent;
import rx.Observable;
import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.functions.Func1;
import rx.plugins.RxJavaHooks;
import rx.schedulers.Schedulers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP, packageName = "com.turlir.abakgists")
public class AllGistsPresenterTest {

    @Rule
    public final DaggerMockRule<AppComponent> rule = new DatabaseMocking();

    @InjectFromComponent
    private Repository _repo;

    @InjectFromComponent
    private AllGistsPresenter _presenter;

    @Mock
    private ApiClient _mockClient;

    @Captor
    private ArgumentCaptor<List<GistModel>> mListCaptor;

    private AllGistsFragment mView;

    @BeforeClass
    public static void setup() throws Throwable {
        RxJavaHooks.reset();
        RxAndroidPlugins.getInstance().reset();

        RxJavaHooks.setOnIOScheduler(new Func1<Scheduler, Scheduler>() {
            @Override
            public Scheduler call(Scheduler scheduler) {
                return Schedulers.immediate();
            }
        });
        RxAndroidPlugins.getInstance().registerSchedulersHook(new RxAndroidSchedulersHook() {
            @Override
            public Scheduler getMainThreadScheduler() {
                return Schedulers.immediate();
            }
        });
    }

    @Before
    public void checkInject() {
        assertNotNull(_repo);
        assertNotNull(_mockClient);
        assertNotNull(_presenter);

        mView = mock(AllGistsFragment.class);
        _presenter.attach(mView);
    }

    @Test
    public void successFirstLoadFromCacheTest() {
        _presenter.loadPublicGists(0);
        GistModel model = new GistModel(
                "85547e4878dd9a573215cd905650f284",
                "https://api.github.com/gists/85547e4878dd9a573215cd905650f284",
                "2017-04-27T21:54:24Z",
                "Part of setTextByParts",
                "iljaosintsev",
                "https://avatars1.githubusercontent.com/u/3526847?v=3",
                "note",
                true
        );
        verify(mView).onGistLoaded(Lists.newArrayList(model));
    }

    @Test
    public void successLoadFromServerTest() {
        mockServerRequest();

        _presenter.loadPublicGists(1);

        verify(_mockClient).publicGist(eq(1));

        _presenter.loadPublicGists(0); // what ?

        verify(mView, atLeastOnce()).onGistLoaded(mListCaptor.capture());
        assertEquals(2, mListCaptor.getValue().size());
    }

    @Test
    public void failureLoadFromServerTest() {
        Observable<List<GistJson>> obs = Observable.error(new IllegalStateException("message"));

        when(_mockClient.publicGist(1)).thenReturn(obs);

        Context mockContext = mock(ContextWrapper.class);
        when(mockContext.getString(anyInt())).thenReturn("string res message");
        when(mView.getContext()).thenReturn(mockContext);

        _presenter.loadPublicGists(1);

        verify(mView).alertError("Произошла ошибка\nIllegalStateException");
    }

    @Test
    public void resetGistTest() {
        mockServerRequest();

        _presenter.updateGist();
        _presenter.loadPublicGists(1);

        verify(mView, atLeastOnce()).onGistLoaded(mListCaptor.capture());
        assertEquals("id", mListCaptor.getValue().get(0).id);
    }

    @Test
    public void successFromCache() {
        Repository mock = mock(Repository.class);

        List<GistLocal> list = new ArrayList<>();
        list.add(new GistLocal("id", "url", "created", "desc"));
        Observable<List<GistLocal>> resultObs = Observable.just(list);
        Mockito.when(mock.load()).thenReturn(resultObs);

        //AllGistsPresenter presenter = new AllGistsPresenter(null);

        _presenter.loadPublicGists(0);

        verify(mock).load();

        GistModel mocked = new GistModel("id", "url", "created", "desc", null, null, "", false);
        List<GistModel> value = Collections.singletonList(mocked);
        verify(mView).onGistLoaded(value);
    }

    private void mockServerRequest() {
        GistJson stub = new GistJson("id", "url", "created", "desc");
        List<GistJson> serverList = Collections.singletonList(stub);
        Observable<List<GistJson>> serverObs = Observable.just(serverList);
        when(_mockClient.publicGist(eq(1))).thenReturn(serverObs);
    }

}