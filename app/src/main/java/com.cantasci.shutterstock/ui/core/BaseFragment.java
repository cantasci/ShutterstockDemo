package com.cantasci.shutterstock.ui.core;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.cantasci.shutterstock.utils.AlertUtils;
import com.cantasci.shutterstock.utils.Prefs;

public abstract class BaseFragment extends Fragment {
    protected BaseActivity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof BaseActivity){
            mActivity=(BaseActivity) context;
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        mActivity.hideKeyboard();
    }

    protected void showToast(String message) {
        showToast(message, Toast.LENGTH_SHORT);
    }

    protected void showToast(String string, int length) {
        Toast.makeText(mActivity, string, length).show();
    }

    protected void showServerError(){
        AlertUtils.showAlert(mActivity, "Error", "Server error");
    }

    public void logout() {
        Prefs.clear(mActivity);
        mActivity.finish();
    }

    public String getToken() {
        return Prefs.getToken(mActivity);
    }

}
