package com.cantasci.shutterstock.presenters;

import android.content.Context;

import com.cantasci.shutterstock.pojos.Image;
import com.cantasci.shutterstock.pojos.ListItem;

import java.util.List;

public interface LauncherPresenter {
    void loadCategories();
    void searchImages(String query, int page, final  boolean isAppend);
    void searchImagesWithCategory(String category, String query);
    List<ListItem> convertToMainList(List<ListItem> imageGroupedList,  Image[] images, boolean force);

    void onStart();
    void onStop();


    interface View extends com.cantasci.shutterstock.presenters.BaseView {
        void onError(String message);
        Context getContext();
        void showProgressBar(boolean show);
        void onImagesLoaded(Image[] images, boolean force);
        void showMessage(String message);
        void setQuery(String query);
        String getQuery();
    }
}
