package com.cantasci.shutterstock.ui.core.grid;

import android.view.View;

public interface GridFragmentDefaultAdapterCallbacks<M, VH> {

    void onBindView(M m, VH vh);

    int getLayoutId(int viewType);

    VH createViewHolder(View v);

    int getItemViewType(M m);

}
