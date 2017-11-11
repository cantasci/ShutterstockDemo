package com.cantasci.shutterstock.ui.core.grid;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.erasys.shutterstock.R;

import com.cantasci.shutterstock.ui.core.AutofitRecyclerView;
import com.cantasci.shutterstock.ui.core.BaseFragment;
import com.cantasci.shutterstock.utils.CommonUtils;
import com.cantasci.shutterstock.utils.ProgressUtils;

import java.util.List;

public abstract class GridAutofitRecycleViewFragment<M> extends BaseFragment {

    public static final int HEADER_VIEW_ID = 1010101;
    public static final int FOOTER_VIEW_ID = 1010102;

    FrameLayout mFrameLayout;
    AutofitRecyclerView mRecyclerView;
    Context mContext;
    RecyclerView.Adapter mAdapter;
    TextView mEmptyText;
    private GridLayoutManager.SpanSizeLookup spanSizeLookup;

    private boolean loadDatasetInBackground = false;
    private Boolean useDefaultAdapter = false;

    private int widthFixedSize  = 320;
    private int empty_text_string_id = R.string.there_is_no_data;
    private int spanCount = 0;
    private OnLoadMoreListener onLoadMoreListener;
    private OnLoadDataExternalLister onLoadDataExternalLister;
    private boolean isShowProgress = true;


    public GridAutofitRecycleViewFragment() {
    }




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = inflater.getContext();
        mFrameLayout = new FrameLayout(mContext);
        mRecyclerView = new AutofitRecyclerView(mContext);
        mEmptyText = new TextView(mContext);
        spanCount = spanCount == 0 ? mContext.getResources().getInteger(R.integer.screen_column_count) : spanCount;

        initRecyclerView(mContext);
        initEmptyTextView(mContext);

        View headerView = onCreateHeader(inflater, mFrameLayout, savedInstanceState);
        View footerView = onCreateFooter(inflater, mFrameLayout, savedInstanceState);
        FrameLayout.LayoutParams recyclerViewParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams emptyTextViewParasm = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        if(headerView != null || footerView != null) {
            RelativeLayout ll = new RelativeLayout(mContext);
            ll.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT));
            boolean hasHeaderView = headerView != null;
            boolean hasFooterView = footerView != null;
            if (headerView != null) {
                headerView.setId(1000+1);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                headerView.setLayoutParams(params);
                ll.addView(headerView);

            }
            RelativeLayout.LayoutParams paramsRecyclerView = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);

            if(hasHeaderView) {
                paramsRecyclerView.addRule(RelativeLayout.BELOW,1000+1);
            }
            if(hasFooterView) {
                paramsRecyclerView.addRule(RelativeLayout.ABOVE,1000+2);
            }
            ll.addView(mRecyclerView, paramsRecyclerView);
            ll.addView(mEmptyText, paramsRecyclerView);


            if (footerView != null) {
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                footerView.setLayoutParams(params);
                ll.addView(footerView);
            }

            mFrameLayout.addView(ll);
        }

         else {
            mFrameLayout.addView(mRecyclerView, recyclerViewParams);
            mFrameLayout.addView(mEmptyText);
        }


        View foreground = onCreateForegroundView(inflater, mFrameLayout, savedInstanceState);
        if (foreground != null) {
            mFrameLayout.addView(foreground);
        }
        return mFrameLayout;
    }

    public View onCreateHeader(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return null;
    }

    public View onCreateFooter(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return null;
    }


    public View onCreateForegroundView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return null;
    }


    protected int getSpanCount() {
        //return context.getResources().getInteger(R.integer.screen_column_count);
        return spanCount;
    }

    public void setSpanCount(int spanCount) {
        this.spanCount = spanCount;
    }

    private void initRecyclerView(Context context) {
        int spaceSize = context.getResources().getDimensionPixelSize(R.dimen.life_view);
        int spanCount = getSpanCount();

        //mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spaceSize, true));
        GridLayoutManager glm = new GridLayoutManager(mContext, getSpanCount());
        if(getSpanSizeLookup() != null)
            glm.setSpanSizeLookup(getSpanSizeLookup());

        RecyclerView.LayoutManager mLayoutManager = glm;
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addOnScrollListener(recyclerViewOnScrollListener);

        getAndBindDataset();
    }

    protected  void initEmptyTextView(Context mContext){
        mEmptyText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mEmptyText.setText(getEmptyTextResourceId());
        mEmptyText.setTextColor(ContextCompat.getColor(mContext, R.color.black));
        mEmptyText.setVisibility(View.GONE);
        int padding = (int) CommonUtils.dipToPixels(mContext,10f);
        mEmptyText.setPadding(2*padding,padding,padding,padding);
    }

    protected void getAndBindDataset() {

        setVisibility(0);
        setEmptyTextLoading(true);
        if(isShowProgress()) ProgressUtils.showProgressIfNeeded(mContext);

        if (loadDatasetInBackground) {
            loadDatasetInBackground();
        }  else
        {
            bindAdapter(getDataset(), false);
        }
    }

    protected void bindAdapter(List<M> data, boolean force) {
        mAdapter = getAdapter(data, force);
        if(force)
            mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        setVisibility(mAdapter.getItemCount());
        setEmptyTextLoading(false);
        ProgressUtils.hideProgressIfNeeded();
        recyclerViewOnScrollListener.setLoading(false);
    }


    private void loadDatasetInBackground() {
        if(getOnLoadDataExternalLister() == null) {
            new AsyncTask<Void, Void, List<M>>() {
                @Override
                protected List<M> doInBackground(Void... params) {
                    return getDataset();
                }

                @Override
                protected void onPostExecute(List<M> datas) {
                    super.onPostExecute(datas);
                    mAdapter = getAdapter(datas);
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                    setVisibility(mAdapter.getItemCount());
                    setEmptyTextLoading(false);
                    recyclerViewOnScrollListener.setLoading(false);

                    ProgressUtils.hideProgressIfNeeded();
                }

            }.execute();
        }

    }

    public void setLoadDatasetInBackground(boolean loadDatasetInBackground) {
        this.loadDatasetInBackground = loadDatasetInBackground;
    }

    protected void notifyDataSetChanged() {
        mAdapter.notifyDataSetChanged();
    }


    private final RecyclerView.Adapter getAdapter(List<M> dataset, boolean force) {
        if (mAdapter == null) {
            if (useDefaultAdapter == null) {
                throw new IllegalArgumentException("configure adapter (custom or default)");
            }

            if (useDefaultAdapter) {
                if (getDefaultVhType() == null)
                    throw new IllegalArgumentException("override getDefaultVhType() method");
                if (getDefaultAdapterCallback() == null)
                    throw new IllegalArgumentException("override getDefaultAdapterCallback() method");

                mAdapter = new GridFragmentDefaultAdapter(getDefaultVhType(), dataset, getDefaultAdapterCallback());
            } else {
                RecyclerView.Adapter adapter = getCustomAdapter(dataset);

                if (adapter == null)
                    throw new IllegalArgumentException("override getCustomAdapter(List<M>) method");
                mAdapter = adapter;
            }
        } else if(force) {
            mAdapter = new GridFragmentDefaultAdapter(getDefaultVhType(), dataset, getDefaultAdapterCallback());
        }

        return mAdapter;
    }

    private final RecyclerView.Adapter getAdapter(List<M> dataset) {
        return getAdapter(dataset);
    }

    public int getItemCount(){
        return mAdapter == null ? 0 : mAdapter.getItemCount();
    }


    protected void useDefaultAdapter(boolean useDefaultAdapter) {
        this.useDefaultAdapter = useDefaultAdapter;
    }


    protected RecyclerView.Adapter getCustomAdapter(List<M> dataset) {
        return null;
    }

    protected Class<? extends RecyclerView.ViewHolder> getDefaultVhType() {
        return null;
    }

    protected GridFragmentDefaultAdapterCallbacks<? extends Object, ? extends RecyclerView.ViewHolder> getDefaultAdapterCallback() {
        return null;
    }


    protected abstract List<M> getDataset();


    public int getWidthFixedSize() {
        return widthFixedSize;
    }

    public GridAutofitRecycleViewFragment setWidthFixedSize(int widthFixedSize) {
        this.widthFixedSize = widthFixedSize;
        return this;
    }

    private void setVisibility(int size){
        if(size == 0){
            mRecyclerView.setVisibility(View.GONE);
            mEmptyText.setVisibility(View.VISIBLE);
        } else {
            mEmptyText.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void setEmptyTextLoading(boolean isLoading){
        if(isLoading){
            mEmptyText.setText(mContext.getString(R.string.loading_data));
        } else {
            mEmptyText.setText(getEmptyTextResourceId());
        }
    }

    public int getEmptyTextResourceId() {
        return empty_text_string_id;
    }

    public void setEmptyTextResourceId(int empty_text_string_id) {
        this.empty_text_string_id = empty_text_string_id;
    }

    public boolean isShowProgress() {
        return isShowProgress;
    }

    public void setShowProgress(boolean showProgress) {
        isShowProgress = showProgress;
    }

    public OnLoadDataExternalLister getOnLoadDataExternalLister() {
        return onLoadDataExternalLister;
    }

    public void setOnLoadDataExternalLister(OnLoadDataExternalLister onLoadDataExternalLister) {
        this.onLoadDataExternalLister = onLoadDataExternalLister;
    }

    public GridLayoutManager.SpanSizeLookup getSpanSizeLookup() {
        return spanSizeLookup;
    }

    public void setSpanSizeLookup(GridLayoutManager.SpanSizeLookup spanSizeLookup) {
        this.spanSizeLookup = spanSizeLookup;
    }


    public static class  CustomOnScrollListener extends RecyclerView.OnScrollListener {
        public void setLoading(boolean loading) {
            this.loading = loading;
        }

        public boolean isLoading() {
            return loading;
        }

        private boolean loading = false;
        int visibleThreshold = 5;
    }

    private CustomOnScrollListener recyclerViewOnScrollListener = new CustomOnScrollListener( ) {


        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (!recyclerView.canScrollVertically(20) && newState == 1)  {
                if (!isLoading()) {
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore();
                    }
                    setLoading(true);
                }

            }

        }

    };

    public OnLoadMoreListener getOnLoadMoreListener() {
        return onLoadMoreListener;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public interface  OnLoadMoreListener {
        void onLoadMore();
    }

    public interface OnLoadDataExternalLister<M> {
        void onLoadedData(List<M> datas, boolean force);
    }
}
