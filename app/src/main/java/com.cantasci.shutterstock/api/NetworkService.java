package com.cantasci.shutterstock.api;

import com.cantasci.shutterstock.pojos.BasePagedResponse;
import com.cantasci.shutterstock.pojos.BaseSearchResponse;
import com.cantasci.shutterstock.pojos.Category;
import com.cantasci.shutterstock.pojos.Image;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;


public interface NetworkService {

    String IMAGE_LIST = "/images";
    String IMAGE_CATEGORIES_LIST = "/images/categories";
    String IMAGE_SEARCH = "/images/search";


    @GET(IMAGE_CATEGORIES_LIST)
    public void loadCategories(Callback<BasePagedResponse<Category[]>> cb);

    @GET(IMAGE_SEARCH)
    public void searchAsync(@Query("query") String query, @Query("page") int page, @Query("view") String renderView, Callback<BaseSearchResponse<Image[]>> cb);

    @GET(IMAGE_SEARCH)
    public BaseSearchResponse<Image[]> search(@Query("query") String query, @Query("page") int page, @Query("view")  String renderView);


    @GET(IMAGE_SEARCH)
    public void searchWithCategory(@Query("category") String category, @Query("query") String query, @Query("page") int page, Callback<BaseSearchResponse<Image[]>> cb);
}

