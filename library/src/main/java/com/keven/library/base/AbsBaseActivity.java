package com.keven.library.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.Toolbar;

import com.anthony.statuslayout.StatusLayout;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.keven.library.R;
import com.keven.library.widgets.statusbar.StatusBarUtil;

import java.util.List;

/**
*<p>Title:AbsBaseActivity</p>
*<p>Description:${}
*<p>Copyright:Copyright(c)2016</p>
*@author keven
*created at 2016/12/16 15:56
*@version
*/
public abstract class AbsBaseActivity extends AppCompatActivity {

    protected static String TAG_LOG = null;
    // context
    protected Context mContext = null;

    private StatusLayout mStatusLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);
    }

    private void init(Bundle savedInstanceState) {
        mContext = this;

        if (getStatusLayoutView() != null) {
            setContentView(getStatusLayoutView());
        }
        //设置状态栏颜色
        setStatusBarColor(getResources().getColor(R.color.app_primary));
        initViewsAndEvents(savedInstanceState);
    }

    /**
     * use {@link StatusLayout} to  global status of progress/error/content/empty
     *
     * @return
     */
    protected View getStatusLayoutView() {
        mStatusLayout = (StatusLayout) LayoutInflater.from(this).inflate(R.layout.lib_activity_show_status, null);
        if (getContentViewID() != 0) {
            View contentView = LayoutInflater.from(this).inflate(getContentViewID(), null);
            ViewGroup.LayoutParams params =
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            mStatusLayout.addView(contentView, params);
        }
        return mStatusLayout;
    }

    /**
    *@author keven
    *created at 2016/12/16 15:57
    */
    protected abstract void initViewsAndEvents(Bundle savedInstanceState);

   /**
   * bind layout resource file
   *@author keven
   *created at 2016/12/16 15:57
   */
    protected abstract int getContentViewID();

    protected void showContent() {
        showContent(null);
    }

    /**
    * Hide all other states and show content
    *@author keven
    *created at 2016/12/16 15:58
    */
    protected void showContent(List<Integer> skipIds) {
        if (skipIds == null || skipIds.size() == 0) {
            mStatusLayout.showContent();
        } else {
            mStatusLayout.showContent(skipIds);
        }

    }

    /**
    * Hide content and show the progress bar
    * @author keven
    * created at 2016/12/16 15:58
    */
    protected void showLoading(List<Integer> skipIds) {
        if (skipIds == null || skipIds.size() == 0) {
            mStatusLayout.showLoading();
        } else {
            mStatusLayout.showLoading(skipIds);
        }

    }

    /**
    * Show empty view when there are not data to show
    * @author keven
    * created at 2016/12/16 15:58
    */
    protected void showEmpty(Drawable emptyDrawable, String emptyTitle, String emptyMessage, List<Integer> skipIds) {
        if (emptyDrawable == null) {
            emptyDrawable = new IconDrawable(this, FontAwesomeIcons.fa_share)
                    .colorRes(android.R.color.white);
        }
        if (TextUtils.isEmpty(emptyTitle)) {
            emptyMessage = "oops";
        }
        if (TextUtils.isEmpty(emptyMessage)) {
            emptyMessage = "nothing to show";
        }
        if (skipIds == null || skipIds.size() == 0) {
            mStatusLayout.showEmpty(emptyDrawable, emptyTitle, emptyMessage);
        } else {
            mStatusLayout.showEmpty(emptyDrawable, emptyTitle, emptyMessage, skipIds);
        }
    }

    /**
     * Show error view with a button when something
     * goes wrong and prompting the user to try again
     */
    protected void showError(Drawable errorDrawable, String errorTitle,
                             String errorMessage, String errorBtnTxt,
                             List<Integer> skipIds, View.OnClickListener errorClickListener) {
        if (errorDrawable == null) {
            errorDrawable = new IconDrawable(this, FontAwesomeIcons.fa_share)
                    .colorRes(android.R.color.white);
        }
        if (errorClickListener == null) {
            errorClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplication(), "Try again button clicked", Toast.LENGTH_LONG).show();
                }
            };
        }
        if (errorTitle == null) {
            errorTitle = "No Connection";
        }
        if (errorMessage == null) {
            errorMessage = "We could not establish a connection with our servers." +
                    " Please try again when you are connected to the internet.";
        }
        if (errorBtnTxt == null) {
            errorBtnTxt = "Try Again";
        }
        if (skipIds == null || skipIds.size() == 0) {
            mStatusLayout.showError(errorDrawable, errorTitle, errorMessage, errorBtnTxt, errorClickListener);
        } else {
            mStatusLayout.showError(errorDrawable, errorTitle, errorMessage, errorBtnTxt, errorClickListener, skipIds);
        }
    }

    public void startActivity(Class<? extends Activity> tarActivity, Bundle options) {
        Intent intent = new Intent(this, tarActivity);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            startActivity(intent, options);
        } else {
            startActivity(intent);
        }
    }

    public void startActivity(Class<? extends Activity> tarActivity) {
        Intent intent = new Intent(this, tarActivity);
        startActivity(intent);
    }

    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    protected void setStatusBarColor(int color) {
        StatusBarUtil.setColor(this, color);
    }

    protected void setToolBar(Toolbar toolbar, String title) {
        /*
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        */
    }
}
