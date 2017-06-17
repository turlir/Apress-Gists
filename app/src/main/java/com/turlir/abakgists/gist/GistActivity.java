package com.turlir.abakgists.gist;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.squareup.picasso.Picasso;
import com.turlir.abakgists.R;
import com.turlir.abakgists.base.App;
import com.turlir.abakgists.model.GistModel;
import com.turlir.abakgists.model.GistModelStorIOSQLitePutResolver;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class GistActivity extends AppCompatActivity {

    private static final String EXTRA_GIST = "EXTRA_GIST";

    private static final EqualsSolver SOLVER = new EqualsSolver();

    private static final GistModelStorIOSQLitePutResolver UPDATE_RESOLVER
            = new GistModelStorIOSQLitePutResolver();

    public static Intent getStartIntent(Context cnt, GistModel data) {
        Intent i = new Intent(cnt, GistActivity.class);
        i.putExtra(EXTRA_GIST, data);
        return i;
    }

    @Inject
    StorIOSQLite _database;

    @BindView(R.id.tv_login)
    TextView login;

    @BindView(R.id.iv_avatar)
    ImageView avatar;

    @BindView(R.id.et_edsc)
    EditText desc;

    @BindView(R.id.et_note)
    EditText note;

    private GistModel mContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gist);
        App.getComponent().inject(this);
        ButterKnife.bind(this);
        mContent = getIntent().getParcelableExtra(EXTRA_GIST);

        fillControl();
    }

    @OnClick(R.id.btn_save)
    public void onClickSave() {
        String oldDesc = mContent.description;
        String oldNote = mContent.note;

        String newDesc = desc.getText().toString();
        String newNote = note.getText().toString();

        boolean isChange = SOLVER.solveDescAndNote(oldDesc, newDesc, oldNote, newNote);
        if (isChange) {
            Timber.i("Внесены изменения, обновление БД");
            mContent.description = newDesc;
            mContent.note = newNote;
            _database.put()
                    .object(mContent)
                    .withPutResolver(UPDATE_RESOLVER)
                    .prepare()
                    .executeAsBlocking();
        } else {
            Timber.i("Изменения не внесены");
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        onClickSave();
    }

    private void fillControl() {
        login.setText(mContent.ownerLogin);
        if (mContent.ownerAvatarUrl != null) {
            loadAvatar(mContent.ownerAvatarUrl);
        }
        desc.setText(mContent.description);
        note.setText(mContent.note);
    }

    private void loadAvatar(String url) {
        Picasso.with(this)
                .load(url)
                .fit()
                .into(avatar);
    }

}
