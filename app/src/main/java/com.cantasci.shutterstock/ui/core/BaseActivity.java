package com.cantasci.shutterstock.ui.core;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;

import com.erasys.shutterstock.R;
import com.cantasci.shutterstock.enums.AnimationDirection;
import com.cantasci.shutterstock.utils.Prefs;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public abstract class BaseActivity extends AppCompatActivity {
    private FragmentManager mFm = getSupportFragmentManager();
    private InputMethodManager mInputMethodManager;



    protected abstract BaseFragment getInitFragment();

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        changeFragment(getInitFragment(), false, false, null);
    }

    @Override
    protected void onStart() {
        super.onStart();
        String login = Prefs.getLogin(this);
        String password = Prefs.getPassword(this);

    }

    public void changeFragment(Fragment f, boolean addToBackStack, boolean animate, AnimationDirection direction) {
        if (f == null) {
            return;
        }

        FragmentTransaction ft = mFm.beginTransaction();

        // Animations
        if (animate) {
            switch (direction) {
                case FROM_RIGHT_TO_LEFT:
                    ft.setCustomAnimations(R.anim.in_from_left, R.anim.out_to_left,
                            R.anim.in_from_right, R.anim.out_to_right);
                    break;
                case FROM_LEFT_TO_RIGHT:
                    ft.setCustomAnimations(R.anim.in_from_right, R.anim.out_to_right,
                            R.anim.in_from_left, R.anim.out_to_left);
                    break;
                case FROM_BOTTOM_TO_TOP:
                    ft.setCustomAnimations(R.anim.in_from_down, R.anim.out_to_down,
                            R.anim.in_from_up, R.anim.out_to_up);
                    break;
                case FROM_TOP_TO_BOTTOM:
                    ft.setCustomAnimations(R.anim.in_from_up, R.anim.out_to_up,
                            R.anim.in_from_down, R.anim.out_to_down);
                    break;
            }
        }

        // BackStack
        if (addToBackStack) {
            ft.addToBackStack(null);
        }

        // Adding fragment
        Fragment oldFragment = mFm.findFragmentById(R.id.fragmentContainer);
        if (oldFragment != null) {
            ft.remove(oldFragment);
        }
        ft.add(R.id.main_frame, f);

        // Commit transaction
        ft.commit();
    }

    public void hideKeyboard() {
        if (getCurrentFocus() != null) {
            mInputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}

