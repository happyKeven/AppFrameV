package com.appframe.module.materialdesign.tablelayout;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.OnTabSelectedListener;
import android.support.design.widget.TabLayout.Tab;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.etisk.officalcar.R;
import com.keven.library.base.BaseFrame;
import com.pnikosis.materialishprogress.ProgressWheel;

public class TabLayoutActivity extends BaseFrame {
    private boolean IS_DEBUG = true;
    private TabLayout tabLayout;
    private String[] title = {
            "头条",
            "新闻",
            "娱乐",
            "体育",
            "科技",
            "美女",
            "财经",
            "汽车",
            "房子",
            "头条"
    };

    private Button        mBtnRefresh;
    private ProgressWheel mProgressWheel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewStub.setLayoutResource(R.layout.tablayout);
        mViewStub.inflate();
        mViewStub.setVisibility(View.VISIBLE);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.vp);
        tabLayout = (TabLayout)findViewById(R.id.tablayout);
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        //1.TabLayout和Viewpager关联
        tabLayout.setOnTabSelectedListener(new OnTabSelectedListener() {

            @Override
            public void onTabUnselected(Tab arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onTabSelected(Tab tab) {
                // 被选中的时候回调
                viewPager.setCurrentItem(tab.getPosition(),true);
            }

            @Override
            public void onTabReselected(Tab arg0) {
                // TODO Auto-generated method stub

            }
        });
        //2.ViewPager滑动关联tabLayout
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        //设置tabLayout的标签来自于PagerAdapter
        tabLayout.setTabsFromPagerAdapter(adapter);

        viewPager.setAdapter(adapter);

        mBtnRefresh = (Button) findViewById(R.id.btn_fresh);
        mBtnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int setVisibility = (mProgressWheel.getVisibility() == View.VISIBLE)?View.GONE:View.VISIBLE;
                if (setVisibility == View.VISIBLE) {
                    mProgressWheel.spin();
                } else {
                    mProgressWheel.stopSpinning();
                }

                mProgressWheel.setVisibility(setVisibility);
            }
        });

    }

    @Override
    protected void resetToolBar(ActionBar ab) {
    }

    class MyPagerAdapter extends FragmentPagerAdapter{

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }

        @Override
        public Fragment getItem(int position) {
            Fragment f = new NewsDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putString("title", title[position]);
            f.setArguments(bundle);
            return f;
        }

        @Override
        public int getCount() {
            return title.length;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem menuItem = menu.findItem(R.id.action_share);
        mProgressWheel = (ProgressWheel) MenuItemCompat.getActionView(menuItem);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(80, 80);
        mProgressWheel.setLayoutParams(layoutParams);
        mProgressWheel.spin();

        if (IS_DEBUG) {
            return true;
        }

        //SearchView在Menu里面
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        //设置一出来就直接呈现搜索框---SearchView
        // searchView.setIconified(false);
        // searchView.setSubmitButtonEnabled(true);
        //进来就呈现搜索框并且不能被隐藏
        //		searchView.setIconifiedByDefault(false);
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
                Toast.makeText(TabLayoutActivity.this, "提交", Toast.LENGTH_SHORT).show();
            }
        });

        // 监听文本变化，调用查询
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {
                //提交文本
                Toast.makeText(TabLayoutActivity.this, "提交文本:"+text, Toast.LENGTH_SHORT).show();
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
