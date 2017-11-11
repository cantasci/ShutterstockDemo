package com.cantasci.shutterstock.ui;


import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.erasys.shutterstock.R;
import com.cantasci.shutterstock.pojos.eventbus.EventBusEvents;
import com.cantasci.shutterstock.ui.core.BaseActivity;
import com.cantasci.shutterstock.ui.core.BaseFragment;
import com.cantasci.shutterstock.ui.launcher.LauncherFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends BaseActivity implements SearchView.OnQueryTextListener {

    private Context                 mContext;
    private SearchView              searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = getApplicationContext();

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }


    @Override
    protected BaseFragment getInitFragment() {
        BaseFragment fragment = LauncherFragment.newInstance();
        return fragment;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        SearchManager searchManager = (SearchManager)
                getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchMenuItem = menu.findItem(R.id.search);
        searchView =  (SearchView) MenuItemCompat.getActionView(searchMenuItem);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(new ComponentName(getApplicationContext(), MainActivity.class)));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);
        searchView.setIconified(false);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().postSticky(new EventBusEvents.ActivityQueryDefault());
            }
        });
        searchView.clearFocus();

        return true;
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        EventBus.getDefault().postSticky(new EventBusEvents.ActivityQueryChanged(query));
        searchView.clearFocus();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Subscribe(sticky = true)
    public void getQueryDefaultText(EventBusEvents.ActivityQueryDefault activityQueryDefault) {
        searchView.setQuery(activityQueryDefault.query, false);
    }
}
