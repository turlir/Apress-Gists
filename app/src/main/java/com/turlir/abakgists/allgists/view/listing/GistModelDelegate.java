package com.turlir.abakgists.allgists.view.listing;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.turlir.abakgists.R;
import com.turlir.abakgists.base.GistItemClickListener;
import com.turlir.abakgists.model.GistModel;
import com.turlir.abakgists.model.InterfaceModel;

import java.util.List;

public class GistModelDelegate extends BaseAdapterDelegate {

    private final GistItemClickListener mClick;

    public GistModelDelegate(LayoutInflater inflater, GistItemClickListener click) {
        super(inflater);
        mClick = click;
    }

    @Override
    protected boolean isForViewType(@NonNull List<InterfaceModel> items, int position) {
        return items.get(position) instanceof GistModel;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, View itemView) {
        GistModelHolder holder = new GistModelHolder(itemView);
        holder.itemView.setOnClickListener(v -> {
            int position = holder.getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                mClick.onListItemClick(position, holder.ivAvatar);
            }
        });
        return holder;
    }

    @Override
    public int getLayout() {
        return R.layout.item_gist;
    }

    @Override
    protected void onBindViewHolder(@NonNull List<InterfaceModel> items, int position,
                                    @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        GistModelHolder gmh = ((GistModelHolder) holder);
        GistModel data = (GistModel) items.get(position);
        gmh.bind(data);
    }
}
