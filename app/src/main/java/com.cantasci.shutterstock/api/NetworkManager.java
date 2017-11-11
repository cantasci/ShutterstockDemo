package com.cantasci.shutterstock.api;

import android.util.Base64;

import com.cantasci.shutterstock.Constants;
import com.cantasci.shutterstock.enums.RenderView;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import com.cantasci.shutterstock.pojos.BasePagedResponse;
import com.cantasci.shutterstock.pojos.BaseSearchResponse;
import com.cantasci.shutterstock.pojos.Category;
import com.cantasci.shutterstock.pojos.Image;
import com.squareup.okhttp.Request;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;



public class NetworkManager {
    private static NetworkManager sInstance;
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private NetworkService mService;

    private NetworkManager() {
        final OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(30, TimeUnit.SECONDS);
        okHttpClient.setConnectTimeout(10, TimeUnit.SECONDS);
        okHttpClient.networkInterceptors().add(new Interceptor() {
            @Override
            public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {
                Request.Builder requestBuilder = chain.request().newBuilder();
                requestBuilder.header("Content-Type", "application/json");
                return chain.proceed(requestBuilder.build());
            }
        });


        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setClient(new OkClient(okHttpClient))
                .setEndpoint(Constants.API_BASE_URL)
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        String authInfo = String.format("%s:%s", Constants.CLIENT_ID, Constants.CLIENT_SECRET);
                        String auth = "Basic "+ Base64.encodeToString(authInfo.getBytes(), Base64.NO_WRAP);
                        request.addHeader("Authorization", auth);
                    }
                })
                .build();

        mService = restAdapter.create(NetworkService.class);
    }



    public static NetworkManager get() {
        synchronized (NetworkManager.class) {
            sInstance = new NetworkManager();
            return sInstance;
        }
    }



    public void loadCategories(Callback<BasePagedResponse<Category[]>> callback) {
        mService.loadCategories(callback);
    }

    public void searchAsync(String query, int page, RenderView renderView, Callback<BaseSearchResponse<Image[]>> callback) {
        String strRenderView = renderView == null ? "minimal" : renderView.name().toLowerCase();
        mService.searchAsync(query, page, strRenderView, callback);
    }

    public BaseSearchResponse<Image[]> search(String query,  RenderView renderView, int page, Callback<BaseSearchResponse<Image[]>> callback) {
        String strRenderView = renderView == null ? "minimal" : renderView.name().toLowerCase();
        return mService.search(query, page, strRenderView);
    }

    public void searchWithCategory(String category, String query, int page, Callback<BaseSearchResponse<Image[]>> callback) {
        mService.searchWithCategory(category, query, page, callback);
    }

}
