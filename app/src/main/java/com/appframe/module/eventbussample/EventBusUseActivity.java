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
package com.appframe.module.eventbussample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.etisk.officalcar.R;
import com.appframe.data.MessageEvent;
import com.keven.library.base.BaseFrame;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class EventBusUseActivity extends BaseFrame {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EventBusUseFragment eventBusUseFragment =
                (EventBusUseFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (eventBusUseFragment == null) {
            // Create the fragment
            eventBusUseFragment = EventBusUseFragment.newInstance();
            addFragmnet(eventBusUseFragment);
        }

        // 把当前类注册为订阅者
        EventBus.getDefault().register(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    protected void resetToolBar(ActionBar ab) {
        ab.setDefaultDisplayHomeAsUpEnabled(true);
        mTopCenterTitle.setVisibility(View.GONE);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 解除注册当前类
        EventBus.getDefault().unregister(this);
    }

    // This method will be called when a MessageEvent is posted (in the UI thread for Toast)
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        Toast.makeText(this, "神奇的国度------>" + event.message, Toast.LENGTH_SHORT).show();
    }


    // 备注：添加点击发送EventBus.getDefault().post(new MessageEvent("Hello everyone!"));，在注册的地方就可以接收到
}
