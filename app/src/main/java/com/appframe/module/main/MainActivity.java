/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.appframe.module.main;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.etisk.officalcar.R;
import com.keven.library.base.BaseFrame;

public class MainActivity extends BaseFrame {

    private static final String CURRENT_FILTERING_KEY = "CURRENT_FILTERING_KEY";
    private MainPresenter mMainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MainFragment mainFragment =
                (MainFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (mainFragment == null) {
            // Create the fragment
            mainFragment = MainFragment.newInstance();
            addFragmnet(mainFragment);
            // ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mainFragment, R.id.contentFrame);
        }

        // Create the presenter
        mMainPresenter = new MainPresenter(this, mainFragment);

        // Load previously saved state, if available.
        if (savedInstanceState != null) {
            MainFilterType currentFiltering =
                    (MainFilterType) savedInstanceState.getSerializable(CURRENT_FILTERING_KEY);
            mMainPresenter.setFiltering(currentFiltering);
        }
    }

    @Override
    protected void resetToolBar(ActionBar ab) {
        // ab.setDisplayShowTitleEnabled(true);
        // ab.setTitle("你若安好便是晴天");
        ab.setDefaultDisplayHomeAsUpEnabled(true);
        mTopCenterTitle.setVisibility(View.GONE);
        // mTopCenterTitle.setText("你的牛逼，我是懂的");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // outState.putSerializable(CURRENT_FILTERING_KEY, mMainPresenter.getFiltering());
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        // SearchView在Menu里面
        MenuItem item = menu.findItem(R.id.action_search);
        if (null == item) {
            return true;
        }

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        //设置一出来就直接呈现搜索框---SearchView
        // searchView.setIconified(false);
        // searchView.setSubmitButtonEnabled(true);
        //进来就呈现搜索框并且不能被隐藏
        //	searchView.setIconifiedByDefault(false);
        //有时候我们需要实现自定义扩展效果
        //通过猜想，searchView用到了一个布局，去appcompat里面找到abc_search_view.xml,该里面的控件的属性
        ImageView icon = (ImageView) searchView.findViewById(R.id.search_go_btn);
        icon.setImageResource(R.drawable.abc_ic_voice_search_api_material);

        icon.setVisibility(View.VISIBLE);
        // searchView.setMaxWidth(200);

        SearchView.SearchAutoComplete et = (SearchView.SearchAutoComplete) searchView.findViewById(R.id.search_src_text);
        et.setHint("输入商品名或首字母");
        et.setHintTextColor(Color.WHITE);

        //设置提交按钮是否可用(可见)
        // searchView.setSubmitButtonEnabled(true);

        //		icon.setOnClickListener(new OnClickListener() {
        //			@Override
        //			public void onClick(View v) {
        //				Toast.makeText(TestActivity.this, "提交", 1).show();
        //			}
        //		});

        // 像AutoCompleteTextView一样使用提示
        //	searchView.setSuggestionsAdapter(adapter)
        // 监听焦点改变
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
            }
        });

        // searchView的关闭监听
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                return false;
            }
        });

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "提交", Toast.LENGTH_SHORT).show();
            }
        });

        // 监听文本变化，调用查询
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {
                //提交文本
                Toast.makeText(MainActivity.this, "提交文本:"+text, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                // 文本改变的时候回调
                System.out.println("文本变化~~~~~"+text);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Open the navigation drawer when the home icon is selected from the toolbar.
                // mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
