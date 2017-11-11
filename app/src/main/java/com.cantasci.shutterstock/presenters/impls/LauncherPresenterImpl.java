package com.cantasci.shutterstock.presenters.impls;

import android.text.TextUtils;
import android.util.Log;

import com.cantasci.shutterstock.api.NetworkManager;
import com.cantasci.shutterstock.enums.RenderView;
import com.cantasci.shutterstock.pojos.BaseSearchResponse;
import com.cantasci.shutterstock.pojos.Category;
import com.cantasci.shutterstock.pojos.CategoryItem;
import com.cantasci.shutterstock.pojos.GeneralItem;
import com.cantasci.shutterstock.pojos.Image;
import com.cantasci.shutterstock.pojos.ListItem;
import com.cantasci.shutterstock.pojos.eventbus.EventBusEvents;
import com.cantasci.shutterstock.presenters.LauncherPresenter;
import com.cantasci.shutterstock.utils.CommonUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by cantasci on 11/11/2017.
 */

public class LauncherPresenterImpl implements LauncherPresenter {

    public static final String TAG = LauncherPresenterImpl.class.getSimpleName();

    private View mView;
    public LauncherPresenterImpl(View v) {
        mView = v;
    }

    @Override
    public void loadCategories() {

    }

    @Override
    public void searchImages(String query, int page, final boolean isAppend) {
        mView.showProgressBar(true);
        NetworkManager.get().searchAsync(query, page, RenderView.Full, new retrofit.Callback<BaseSearchResponse<Image[]>>() {
            @Override
            public void success(BaseSearchResponse<Image[]> basePagedResponse, Response response) {

                if(!TextUtils.isEmpty(basePagedResponse.getMessage()) && basePagedResponse.getData() == null)
                    mView.onError(basePagedResponse.getMessage());
                else
                    mView.onImagesLoaded(basePagedResponse.getData(), !isAppend);


            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, CommonUtils.parseRetrofitError(error));
                mView.showProgressBar(false);
                mView.onError(error.getMessage());
            }
        });

    }

    @Override
    public void searchImagesWithCategory(String category, String query) {

    }


    private HashMap<String, List<Image>> groupDataIntoHashMap(Image[] images) {

        HashMap<String, List<Image>> groupedHashMap = new HashMap<>();

        for(Image img : images) {

            if(img.getCategories() != null && img.getCategories().length>0) {
                for(Category category : img.getCategories()){
                    addToHashMap(groupedHashMap, category.getName(), img);
                }
            } else {
                addToHashMap(groupedHashMap, "", img);
            }
        }

        return groupedHashMap;
    }

    private void addToHashMap(HashMap<String, List<Image>> groupedHashMap, String hashMapKey, Image image) {
        if(groupedHashMap.containsKey(hashMapKey)) {
            // The key is already in the HashMap; add the pojo object
            // against the existing key.
            groupedHashMap.get(hashMapKey).add(image);
        } else {
            // The key is not there in the HashMap; create a new key-value pair
            List<Image> list = new ArrayList<>();
            list.add(image);
            groupedHashMap.put(hashMapKey, list);
        }
    }

    @Override
    public List<ListItem> convertToMainList(List<ListItem> imageGroupedList, Image[] images, boolean force) {

        HashMap<String, List<Image>>  groupedHashMap = groupDataIntoHashMap(images);
        if(force)
            imageGroupedList = new ArrayList<>();

        for (String category : groupedHashMap.keySet()) {
            CategoryItem categoryItem = new CategoryItem();
            categoryItem.setCategory(category);
            imageGroupedList.add(categoryItem);

            for (Image image : groupedHashMap.get(category)) {
                GeneralItem generalItem = new GeneralItem();
                generalItem.setImages(image);
                imageGroupedList.add(generalItem);
            }
        }

        return imageGroupedList;
    }



    @Override
    public void onStart() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(sticky = true)
    public void onQueryChanged(EventBusEvents.ActivityQueryChanged activityQueryChanged) {
        mView.setQuery(activityQueryChanged.query);
    }

    @Subscribe(sticky = true)
    public void receiveGetDefaultQuery(EventBusEvents.ActivityQueryDefault activityQueryDefault) {
       EventBus.getDefault().postSticky(new EventBusEvents.ActivityQueryDefault(mView.getQuery()));
    }
}
