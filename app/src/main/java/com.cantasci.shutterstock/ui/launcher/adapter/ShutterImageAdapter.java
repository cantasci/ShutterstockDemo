package com.cantasci.shutterstock.ui.launcher.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.cantasci.shutterstock.Constants;
import com.erasys.shutterstock.R;
import com.cantasci.shutterstock.pojos.CategoryItem;
import com.cantasci.shutterstock.pojos.GeneralItem;
import com.cantasci.shutterstock.pojos.ListItem;
import com.cantasci.shutterstock.ui.core.grid.GridFragmentDefaultAdapterCallbacks;
import com.cantasci.shutterstock.utils.CommonUtils;

public class ShutterImageAdapter implements GridFragmentDefaultAdapterCallbacks<ListItem, ImageViewHolder> {


    @Override
    public void onBindView(ListItem listItem, ImageViewHolder viewHolder) {

        if(listItem.getType() == ListItem.TYPE_CATEGORY) {
            viewHolder.mShutterCategory.setText(((CategoryItem)listItem).getCategory());

        } else {
            Glide.with(viewHolder.mShutterImage.getContext())
                    .load(((GeneralItem)listItem).getImage().getLargeThumbnail())
                    .into(viewHolder.mShutterImage);

            viewHolder.mShutterDescription.setText(((GeneralItem)listItem).getImage().getDescription());
            viewHolder.mShutterImage.setLayoutParams(getGridItemLayoutParams(viewHolder.mShutterImage));
        }
    }



    @Override
    public int getLayoutId(int viewType)
    {
        if(viewType == ListItem.TYPE_CATEGORY )
            return R.layout.recycler_item_header;
        else
            return R.layout.recycler_item;
    }

    @Override
    public ImageViewHolder createViewHolder(View v) {
        return new ImageViewHolder(v);
    }

    @Override
    public int getItemViewType(ListItem listItem) {
        return listItem.getType();
    }

    /***
     * Get the grid layout params based on the orientation
     *
     * @param view
     * @return
     */
    private ViewGroup.LayoutParams getGridItemLayoutParams(View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if(view.getContext() instanceof Activity) {
            int screenWidth = CommonUtils.getScreenWidth(view.getContext());
            int numOfColumns;
            if (CommonUtils.isInLandscapeMode(view.getContext())) {
                numOfColumns = Constants.COLUMN_LANDSCAPE;
            } else {
                numOfColumns = Constants.COLUMN_PORTRAIT;
            }

            int mGridItemWidth = screenWidth / numOfColumns;
            int mGridItemHeight = screenWidth / numOfColumns;

            layoutParams.width = mGridItemWidth;
            layoutParams.height = mGridItemHeight;
        }


        return layoutParams;
    }
}