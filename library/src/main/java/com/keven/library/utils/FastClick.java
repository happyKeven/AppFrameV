package com.keven.library.utils;/**
 * Created by keven on 2016/11/4.
 */

/**
 * User: keven(1986061846@qq.com)
 * Date: 2016-11-04
 * Time: 16:14
 */
public class FastClick {
    private static long lastClickTime;
    private static long lastClickPosition;

    // 避免ListView上，在同一个地方，同一item上的事件被触发多次 事件间隔，在这里我定义的是1000毫秒
    public static boolean isListViewItemFastClick(int postion) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (timeD >= 0 && timeD <= 500 && lastClickPosition == postion) {
            return true;
        } else {
            lastClickTime = time;
            lastClickPosition = postion;
            return false;
        }
    }
}
