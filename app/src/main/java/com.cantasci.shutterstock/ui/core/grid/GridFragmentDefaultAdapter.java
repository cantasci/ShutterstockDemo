package com.cantasci.shutterstock.ui.core.grid;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class GridFragmentDefaultAdapter<M, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    Class<VH> vhType;
    private List<M> dataset;
    private GridFragmentDefaultAdapterCallbacks<M, VH> callback;

    public GridFragmentDefaultAdapter(Class<VH> vhType, List<M> dataset, GridFragmentDefaultAdapterCallbacks<M, VH> callback) {
        this.dataset = dataset;
        this.callback = callback;
        this.vhType = vhType;
    }


    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(callback.getLayoutId(viewType), parent, false);
        return createViewHolder(v);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        M model = dataset.get(position);
        callback.onBindView(model, holder);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    protected VH createViewHolder(View v) {
        VH viewHolderInstance = callback.createViewHolder(v);

        if (viewHolderInstance == null) {
            try {
                return vhType.getConstructor(View.class).newInstance(v);
            } catch (Throwable e) {
                throw new IllegalAccessError("override createViewHolder(View) method = [" + vhType.getSimpleName() + "]");
            }
        } else {
            return viewHolderInstance;
        }
    }

    @Override
    public int getItemViewType(final int position) {
        if(callback == null)
            return  super.getItemViewType(position);
        else
            return callback.getItemViewType(dataset.get(position));
    }
}
