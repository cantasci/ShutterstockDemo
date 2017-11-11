package com.cantasci.shutterstock.utils;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.WindowManager;

import com.cantasci.shutterstock.customviews.ProgressHUD;

import java.util.concurrent.atomic.AtomicInteger;

public class ProgressUtils {
    private static final String        TAG                   = ProgressUtils.class.getSimpleName();
    private final static AtomicInteger activeRequestsCounter = new AtomicInteger();
    private static ProgressHUD progressHUD;

    private static void incrementRequestsCounter() {
        activeRequestsCounter.incrementAndGet();
    }

    private static void decrementRequestsCounter() {
        if(activeRequestsCounter.intValue() > 0)
            activeRequestsCounter.decrementAndGet();
    }

    public static void showProgressIfNeeded(Context context){
        try{
            incrementRequestsCounter();
            Log.i(TAG,"showProgress "+activeRequestsCounter.get());
            if(progressHUD == null || !progressHUD.isShowing()){
                progressHUD = ProgressHUD.create(context);
            }
        }catch (WindowManager.BadTokenException ex) {

        }

    }
    public static void hideProgressIfNeeded(){
        decrementRequestsCounter();
        Log.i(TAG, "hideProgress " + activeRequestsCounter.get());
       if(activeRequestsCounter.get() <= 0){
            dissmiss(progressHUD);
        }
    }

    public static void dissmiss(Dialog dialog) {
        //hack for closing dialog
        try {
            if ((dialog != null) && dialog.isShowing()) {
                dialog.dismiss();
            }
        } catch (final IllegalArgumentException e) {
            //ignore
        } catch (final Exception e) {
            //ignore
        } finally {
            dialog = null;
        }
    }
}
