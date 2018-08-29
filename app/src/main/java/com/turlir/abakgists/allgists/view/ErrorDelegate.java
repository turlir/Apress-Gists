package com.turlir.abakgists.allgists.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.turlir.abakgists.R;
import com.turlir.abakgists.model.ErrorModel;

import java.util.List;

class ErrorDelegate extends BaseAdapterDelegate {

    ErrorDelegate(LayoutInflater inflater) {
        super(inflater);
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, View itemView) {
        return new ErrorModelHolder(itemView);
    }

    @Override
    protected int getLayout() {
        return R.layout.network_error;
    }

    @Override
    protected boolean isForViewType(@NonNull List<ViewModel> items, int position) {
        return items.get(position) instanceof ErrorModel;
    }

    @Override
    protected void onBindViewHolder(@NonNull List<ViewModel> items, int position,
                                    @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        ((ErrorModelHolder) holder).bind((ErrorModel) items.get(position));
    }
}
