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

import com.appframe.data.responseEntity.MainResp;
import com.keven.library.base.BasePresenter;
import com.keven.library.base.BaseView;

import java.util.List;

/**
*<p>Title:MainContract</p>
*<p>Description:${}
*<p>Copyright:Copyright(c)2016</p>
*@author keven
*created at 2016/12/15 16:15
*@version
*/
public interface MainContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void showDatas(List<MainResp.MainBean> mainDatas);

        void showAddData();

        void showLoadDataError();

        void showNoDatas();

        void showActiveFilterLabel();

        void showCompletedFilterLabel();

        void showAllFilterLabel();

        void showNoActiveDatas();

        void showNoCompletedDatas();

        boolean isActive();

        void showFilteringPopUpMenu();
    }

    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);

        void loadDatas(boolean forceUpdate);

        void addNewData();

        void setFiltering(MainFilterType requestType);

        MainFilterType getFiltering();
    }
}
