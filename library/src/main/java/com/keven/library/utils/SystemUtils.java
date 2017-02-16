/*
 * Copyright 2016, The Android Open Source Project
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

package com.keven.library.utils;

import android.app.Activity;
import android.content.Context;
import android.provider.Settings;
import android.view.Window;
import android.view.WindowManager;

/**
*<p>Title:SystemUtils</p>
*<p>Description:${}
*<p>Copyright:Copyright(c)2016</p>
*@author keven
*created at 2016/12/5 14:39
*@version
*/
public class SystemUtils {

    /**
    * 根据亮度值修改当前window亮度
    *@author keven
    *created at 2016/12/5 14:39
    */
    public static void changeAppBrightness(Context context, float brightness) {
        Window window = ((Activity) context).getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        if (brightness == -1) {
            lp.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE;
        } else {
            lp.screenBrightness = brightness;
        }
        window.setAttributes(lp);
    }

    /**
    * 获取系统的屏幕亮度
    *@author keven
    *created at 2016/12/5 14:40
    */
    public static float getSystemBrightness(Context context) {
        // 获取系统亮度模式
        try {
            return Settings.System.getFloat(context.getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }

        return 0f;
    }


    /**
     * 设置当前屏幕亮度的模式
     * SCREEN_BRIGHTNESS_MODE_AUTOMATIC=1 为自动调节屏幕亮度
     * SCREEN_BRIGHTNESS_MODE_MANUAL=0 为手动调节屏幕亮度
     */
    public static void setScreenMode(Context context, int paramInt) {
        try {
            Settings.System
                    .putInt(context.getContentResolver(),
                            Settings.System.SCREEN_BRIGHTNESS_MODE, paramInt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得当前屏幕亮度的模式
     * SCREEN_BRIGHTNESS_MODE_AUTOMATIC=1 为自动调节屏幕亮度
     * SCREEN_BRIGHTNESS_MODE_MANUAL=0 为手动调节屏幕亮度
    */
    public static int getScreenLightnessMode(Context context) {
        int screenMode = 0;
        try {
            screenMode = Settings.System
                    .getInt(context.getContentResolver(),
                            Settings.System.SCREEN_BRIGHTNESS_MODE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return screenMode;
    }

}
