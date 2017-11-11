package com.cantasci.shutterstock.ui.launcher;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cantasci.shutterstock.pojos.Image;
import com.cantasci.shutterstock.pojos.ListItem;
import com.cantasci.shutterstock.presenters.LauncherPresenter;
import com.cantasci.shutterstock.presenters.impls.LauncherPresenterImpl;
import com.cantasci.shutterstock.ui.core.grid.GridAutofitRecycleViewFragment;
import com.cantasci.shutterstock.ui.core.grid.GridFragmentDefaultAdapterCallbacks;
import com.cantasci.shutterstock.ui.launcher.adapter.ShutterImageAdapter;
import com.cantasci.shutterstock.utils.ProgressUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class LauncherFragment extends GridAutofitRecycleViewFragment<ListItem>
            implements GridAutofitRecycleViewFragment.OnLoadDataExternalLister<ListItem>,
                        LauncherPresenter.View{
    public static final String TAG = LauncherFragment.class.getSimpleName();

    List<ListItem> imageGroupedList;
    HashMap<String, Image> imageGroupedHashMap;
    int page = 1;
    String query;
    Date dateNow;

    LauncherPresenter launcherPresenter;

    public LauncherFragment() {
        useDefaultAdapter(true);
    }

    public static LauncherFragment newInstance() {
        Bundle args = new Bundle();
        LauncherFragment fragment = new LauncherFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLoadDatasetInBackground(true);
        setOnLoadDataExternalLister(this);
        setShowProgress(false);
        launcherPresenter = new LauncherPresenterImpl(this);
        imageGroupedList = new ArrayList<>();
        imageGroupedHashMap = new HashMap<>();
        dateNow = new Date();
        query= null;

        setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if(getDataset().size() == 0 || position>=getDataset().size()) return 1;

                if(getDataset().get(position).getType() == ListItem.TYPE_CATEGORY )
                    return getSpanCount();
                else
                    return 1;
            }
        });

        getImageList(false);
        setOnLoadMoreListener(onLoadMoreListener);
    }


    private OnLoadMoreListener onLoadMoreListener = new OnLoadMoreListener() {
        @Override
        public void onLoadMore() {
            page++;
            getImageList(true);
        }
    };



    @Override
    public void onStart() {
        super.onStart();
        launcherPresenter.onStart();
    }

    @Override
    public void onStop() {
        launcherPresenter.onStop();
        super.onStop();
    }



    @Override
    public List<ListItem> getDataset() {
        return imageGroupedList;
    }

    @Override
    protected Class<? extends RecyclerView.ViewHolder> getDefaultVhType() {
        return RecyclerView.ViewHolder.class;
    }

    @Override
    protected GridFragmentDefaultAdapterCallbacks<?, ? extends RecyclerView.ViewHolder> getDefaultAdapterCallback() {
        return new ShutterImageAdapter();
    }


    private void getImageList(final boolean isAppend) {
        if(!isAppend) {
            page = 1;
            imageGroupedList = new ArrayList<>();
            imageGroupedHashMap = new HashMap<>();
        }

        launcherPresenter.searchImages(query, page, isAppend);
    }

    @Override
    public void onLoadedData(List<ListItem> datas, boolean force) {

    }



    @Override
    public void onError(String message) {
        showToast(message);
    }

    @Override
    public void showProgressBar(boolean show) {
        if(show)
            ProgressUtils.showProgressIfNeeded(mActivity);
        else
            ProgressUtils.hideProgressIfNeeded();
    }

    @Override
    public void onImagesLoaded(Image[] images, boolean force) {
        imageGroupedList = launcherPresenter.convertToMainList(imageGroupedList, images, force);
        bindAdapter(imageGroupedList, force);
        showProgressBar(false);
    }

    @Override
    public void showMessage(String message) {
        showToast(message);
    }

    @Override
    public void setQuery(String query) {
        this.query = query;
        getImageList(false);
    }

    @Override
    public String getQuery() {
        return this.query;
    }


}
