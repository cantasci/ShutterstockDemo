package com.cantasci.shutterstock.ui.launcher.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.erasys.shutterstock.R;

import butterknife.Bind;
import butterknife.ButterKnife;


public class ImageViewHolder extends RecyclerView.ViewHolder    {

    @Bind(R.id.shutterImageView)    @Nullable ImageView mShutterImage;
    @Bind(R.id.shutterCategory)     @Nullable TextView  mShutterCategory;
    @Bind(R.id.shutterDescription)  @Nullable TextView  mShutterDescription;

    public ImageViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }



}
