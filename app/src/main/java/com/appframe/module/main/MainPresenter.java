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

package com.appframe.module.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.appframe.data.constant.HttpUrls;
import com.appframe.data.responseEntity.MainResp;
import com.appframe.data.responseEntity.VersionInfoResp;
import com.keven.library.downloadmanager.manager.UpdateManager;
import com.keven.library.downloadmanager.manager.bean.VersionInfo;
import com.keven.library.downloadmanager.manager.config.UploadConfig;
import com.keven.library.net.okhttp.JsonGenericsSerializator;
import com.keven.library.net.okhttp.OkHttpRequest;
import com.keven.library.utils.ApplicationUtils;
import com.zhy.http.okhttp.callback.GenericsCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Listens to user actions from the UI ({@link MainFragment}), retrieves the data and updates the
 * UI as required.
 */
public class MainPresenter implements MainContract.Presenter {
    private Context mContext;

    private final MainContract.View mMainView;

    private MainFilterType mCurrentFiltering = MainFilterType.ALL_TASKS;

    private boolean mFirstLoad = true;

    public MainPresenter(Context context, @NonNull MainContract.View mainView) {
        mContext = context;
        mMainView = checkNotNull(mainView, "tasksView cannot be null!");
        mMainView.setPresenter(this);
    }

    @Override
    public void start() {
        loadDatas(false);
    }

    @Override
    public void result(int requestCode, int resultCode) {
        // If a task was successfully added, show snackbar
        /*
        if (AddEditTaskActivity.REQUEST_ADD_TASK == requestCode && Activity.RESULT_OK == resultCode) {
            mTasksView.showSuccessfullySavedMessage();
        }
        */
    }

    @Override
    public void loadDatas(boolean forceUpdate) {
        // Simplification for sample: a network reload will be forced on first load.
        loadDatas(forceUpdate || mFirstLoad, true);

        if (forceUpdate || mFirstLoad) {
            checkUpdate();
        }
        mFirstLoad = false;
    }


    private void loadDatas(boolean forceUpdate, final boolean showLoadingUI) {
        /*if (!forceUpdate) {
            return;
        }*/

        if (showLoadingUI) {
            mMainView.setLoadingIndicator(true);
        }

        OkHttpRequest.postJsonRequest(HttpUrls.MAIN_DATA_URL, null, new GenericsCallback<MainResp>(new JsonGenericsSerializator() {
            @Override
            public <T> T transform(String response, Class<T> classOfT) {
                return super.transform(response, classOfT);
            }
        }) {
            @Override
            public void onError(Call call, Exception e, int i) {
                // The view may not be able to handle UI updates anymore
                if (!mMainView.isActive()) {
                    return;
                }
                mMainView.showLoadDataError();
            }

            @Override
            public void onResponse(MainResp o, int i) {
                MainResp.MainBean mainBean = (MainResp.MainBean) o.getData();
                /*
               switch (mCurrentFiltering) {
                    case ALL_TASKS:
                    break;
                    case ACTIVE_TASKS:
                        break;
                    case COMPLETED_TASKS:
                        break;
                    default:
                        break;
                }
                */
                // The view may not be able to handle UI updates anymore
                if (!mMainView.isActive()) {
                    return;
                }

                if (showLoadingUI) {
                    mMainView.setLoadingIndicator(false);
                }

                List<MainResp.MainBean> datas = new ArrayList<MainResp.MainBean>();
                datas.add(mainBean);
                processTasks(datas);
            }
        });
    }

    private void processTasks(List<MainResp.MainBean> datas) {
        if (datas.isEmpty()) {
            // Show a message indicating there are no tasks for that filter type.
            processEmptyTasks();
        } else {
            // Show the list of tasks
            mMainView.showDatas(datas);
            // Set the filter label's text.
            showFilterLabel();
        }
    }

    private void showFilterLabel() {
        /*switch (mCurrentFiltering) {
            case ACTIVE_TASKS:
                mMainView.showActiveFilterLabel();
                break;
            case COMPLETED_TASKS:
                mMainView.showCompletedFilterLabel();
                break;
            default:
                mMainView.showAllFilterLabel();
                break;
        }*/
    }

    private void processEmptyTasks() {
        switch (mCurrentFiltering) {
            case ACTIVE_TASKS:
                mMainView.showNoActiveDatas();
                break;
            case COMPLETED_TASKS:
                mMainView.showNoCompletedDatas();
                break;
            default:
                mMainView.showNoDatas();
                break;
        }
    }

    @Override
    public void addNewData() {
        mMainView.showAddData();
    }

    /**
     * Sets the current task filtering type.
     *
     * @param requestType Can be {@link MainFilterType#ALL_TASKS},
     *                    {@link MainFilterType#COMPLETED_TASKS}, or
     *                    {@link MainFilterType#ACTIVE_TASKS}
     */
    @Override
    public void setFiltering(MainFilterType requestType) {
        mCurrentFiltering = requestType;
    }

    @Override
    public MainFilterType getFiltering() {
        return mCurrentFiltering;
    }

    /**
    * 检测更新
    * @author keven
    * created at 2016/12/5 15:49
    */
    public void checkUpdate() {
        OkHttpRequest.postJsonRequest(HttpUrls.VERSION_INFO_URL, null, new GenericsCallback<VersionInfoResp>(new JsonGenericsSerializator() {
        }) {
            @Override
            public void onError(Call call, Exception e, int i) {
            }

            @Override
            public void onResponse(VersionInfoResp o, int i) {
                VersionInfoResp versionInfoResp = (VersionInfoResp) o;

                VersionInfo versionInfo = null;

                if (null != versionInfoResp) {
                    versionInfo = (VersionInfo) versionInfoResp.getData();
                    if (null == versionInfo) {
                        return;
                    }
                }

                // 如果获取的版本信息大于应用版本号，则启动下载服务，否则进行下载升级操作
                if (ApplicationUtils.getVersionCode(mContext).compareTo(versionInfo.getVersionCode()) < 0) {
                    new UpdateManager(mContext).showNoticeDialog(new UploadConfig.Builder(mContext).config(), versionInfo);
                } else {
                    Toast.makeText(mContext, "已经是最新版本", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
