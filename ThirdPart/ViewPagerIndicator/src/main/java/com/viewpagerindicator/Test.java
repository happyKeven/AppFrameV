package com.viewpagerindicator;

import android.content.Context;
import android.widget.Button;

/**
 * <p>Title:Test</p>
 * <p>Description:</p>
 * <p>Copyright: Copyright(c)2016</p>
 * User: 刘开峰
 * Date: 2017-02-09
 * Time: 15:11
 */
public class Test extends Button {

    public Test(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }
}
