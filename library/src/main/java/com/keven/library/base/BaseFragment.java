package com.keven.library.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public abstract class BaseFragment extends Fragment {

    protected static String TAG_LOG = BaseFragment.class.getSimpleName();

    /**
    * activity context of fragment
    * @author keven
    * created at 2016/12/18 23:12
    */
    protected Context               mContext;
    protected Activity              mActivity;


    @Override
    public void onAttach(Context context) {
        //set a context from current activity
        mActivity = (Activity) context;
        mContext = context;
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TAG_LOG = this.getClass().getSimpleName();

        loadData();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getLayoutView() != null) {
            return getLayoutView();
        }
        if (getLayoutId() != 0) {
            return inflater.inflate(getLayoutId(), null);
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // 设置状态栏透明
        // setStatusBarColor();
        super.onViewCreated(view, savedInstanceState);
        initViews(view, savedInstanceState);

    }

    protected abstract int getLayoutId();

    public View getLayoutView() {
        return null;
    }


    /**
     * override this method to do operation in the fragment
     */
    protected abstract void initViews(View rootView, Bundle savedInstanceState);

    protected abstract void loadData();// load data in onCreate method

}

