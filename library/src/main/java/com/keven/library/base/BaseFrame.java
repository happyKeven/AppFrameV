package com.keven.library.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;

import com.keven.library.R;
import com.keven.library.utils.ActivityUtils;

/**
 * <p>Title:BaseFrame</p>
 * <p>Description:</p>
 * <p>Copyright: Copyright(c)2016</p>
 * User: 刘开峰
 * Date: 2016-12-13
 * Time: 18:06
 */
public abstract class BaseFrame extends AppCompatActivity {
    protected DrawerLayout mDrawerLayout;
    protected TextView     mTopCenterTitle;

    public ViewStub mViewStub;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baseframe);
        initView();
    }


    private void initView() {
        // Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayUseLogoEnabled(false);

        mTopCenterTitle = (TextView) findViewById(R.id.tv_top_center_title);


        // Set up the navigation drawer.
        /*
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setStatusBarBackground(R.color.colorPrimary_pinkDark);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }
        */

        resetToolBar(ab);

        mViewStub = (ViewStub) findViewById(R.id.viewStub);
    }

    public void addFragmnet(Fragment fragment) {
        findViewById(R.id.contentFrame).setVisibility(View.VISIBLE);
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.contentFrame);
    }

    /**
    * 重置默认Toolbar
    *@author keven
    *created at 2016/12/16 9:42
    */
    protected abstract void resetToolBar(ActionBar ab);
    /**
     protected void setupDrawerContent(NavigationView navigationView) {
    }
    */
}

