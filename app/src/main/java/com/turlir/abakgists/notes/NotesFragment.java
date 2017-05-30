package com.turlir.abakgists.notes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.turlir.abakgists.base.App;
import com.turlir.abakgists.gist.GistActivity;
import com.turlir.abakgists.base.OnClickListener;
import com.turlir.abakgists.R;
import com.turlir.abakgists.base.SpaceDecorator;
import com.turlir.abakgists.allgists.AllGistAdapter;
import com.turlir.abakgists.base.BaseFragment;
import com.turlir.abakgists.model.GistModel;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class NotesFragment extends BaseFragment implements OnClickListener {

    @Inject
    NotesPresenter _presenter;

    @BindView(R.id.recycler)
    RecyclerView recycler;

    private AllGistAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getComponent().inject(this);
        _presenter.attach(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle saved) {
        View root = inflater.inflate(R.layout.fragment_all_gists, container, false);
        root.setEnabled(false); // SwipeRefreshLayout
        butterKnifeBind(root);

        mAdapter = new AllGistAdapter(getContext(), this);
        recycler.setAdapter(mAdapter);
        RecyclerView.LayoutManager lm
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(lm);

        DividerItemDecoration divider =
                new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        recycler.addItemDecoration(divider);
        recycler.addItemDecoration(new SpaceDecorator(getActivity(), R.dimen.item_offset));

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        _presenter.loadNotes();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        _presenter.detach();
    }

    @Override
    public void onListItemClick(int position) {
        GistModel item = mAdapter.getItemByPosition(position);
        Intent i = GistActivity.getStartIntent(getContext(), item);
        startActivity(i);
    }

    @Override
    public String toString() {
        return "Notes";
    }

    void onNotesLoaded(List<GistModel> gistDiff) {
        mAdapter.addGist(gistDiff, mAdapter.getItemCount(), gistDiff.size());
    }

    void onNotesDeleted() {
        mAdapter.removeLastGist();
    }

}
