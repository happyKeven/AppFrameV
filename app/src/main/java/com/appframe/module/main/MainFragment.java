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

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.etisk.officalcar.R;
import com.appframe.data.responseEntity.MainResp;
import com.keven.library.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
* Display a grid of {@link com.appframe.data.MainData}s. User can choose to view all, active or completed tasks.
* @author keven
* created at 2016/12/5 15:43
*/
public class MainFragment extends BaseFragment implements MainContract.View {

    private MainContract.Presenter mPresenter;

    private MainAdapter mListAdapter;

    private View mNoTasksView;

    private ImageView mNoTaskIcon;

    private TextView mNoTaskMainView;

    private TextView mNoTaskAddView;

    private LinearLayout mTasksView;

    private TextView mFilteringLabelView;

    public MainFragment() {
        // Requires empty public constructor
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListAdapter = new MainAdapter(new ArrayList<MainResp.MainBean>(0), mItemListener);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(@NonNull MainContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.result(requestCode, resultCode);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.tasks_frag;
    }

    @Override
    protected void initViews(View rootView, Bundle savedInstanceState) {
        // Set up tasks view
        ListView listView = (ListView) rootView.findViewById(R.id.tasks_list);
        listView.setAdapter(mListAdapter);
        mFilteringLabelView = (TextView) rootView.findViewById(R.id.filteringLabel);
        mTasksView = (LinearLayout) rootView.findViewById(R.id.tasksLL);

        // Set up  no tasks view
        mNoTasksView = rootView.findViewById(R.id.noTasks);
        mNoTaskIcon = (ImageView) rootView.findViewById(R.id.noTasksIcon);
        mNoTaskMainView = (TextView) rootView.findViewById(R.id.noTasksMain);
        mNoTaskAddView = (TextView) rootView.findViewById(R.id.noTasksAdd);
        mNoTaskAddView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddData();
            }
        });

        // Set up progress indicator
        final ScrollChildSwipeRefreshLayout swipeRefreshLayout =
                (ScrollChildSwipeRefreshLayout) rootView.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimary_pinkDark)
        );
        // Set the scrolling view in the custom SwipeRefreshLayout.
        swipeRefreshLayout.setScrollUpChild(listView);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadDatas(false);
            }
        });

        setHasOptionsMenu(true);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_clear:
                break;
            case R.id.menu_filter:
                showFilteringPopUpMenu();
                break;
            case R.id.menu_refresh:
                mPresenter.loadDatas(true);
                break;
        }
        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // inflater.inflate(R.menu.tasks_fragment_menu, menu);
    }

    @Override
    public void showFilteringPopUpMenu() {
        PopupMenu popup = new PopupMenu(getContext(), getActivity().findViewById(R.id.menu_filter));
        popup.getMenuInflater().inflate(R.menu.filter_tasks, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.active:
                        mPresenter.setFiltering(MainFilterType.ACTIVE_TASKS);
                        break;
                    case R.id.completed:
                        mPresenter.setFiltering(MainFilterType.COMPLETED_TASKS);
                        break;
                    default:
                        mPresenter.setFiltering(MainFilterType.ALL_TASKS);
                        break;
                }
                mPresenter.loadDatas(false);
                return true;
            }
        });

        popup.show();
    }

    /**
    * Listener for clicks on tasks in the ListView.
    * @author keven
    * created at 2016/12/5 15:42
    */
    MainItemListener mItemListener = new MainItemListener() {
        @Override
        public void onClick(MainResp.MainBean clickedTask) {
        }

        @Override
        public void onCompleteClick(MainResp.MainBean completedTask) {
        }

        @Override
        public void onActivateClick(MainResp.MainBean activatedTask) {
        }
    };

    @Override
    public void setLoadingIndicator(final boolean active) {

        if (getView() == null) {
            return;
        }

        final SwipeRefreshLayout srl =
                (SwipeRefreshLayout) getView().findViewById(R.id.refresh_layout);

        // Make sure setRefreshing() is called after the layout is done with everything else.
        srl.post(new Runnable() {
            @Override
            public void run() {
                srl.setRefreshing(active);
            }
        });
    }

    @Override
    public void showDatas(List<MainResp.MainBean> datas) {
        mListAdapter.replaceData(datas);

        mTasksView.setVisibility(View.VISIBLE);
        mNoTasksView.setVisibility(View.GONE);
    }

    @Override
    public void showNoActiveDatas() {
        showNoTasksViews(
                getResources().getString(R.string.no_tasks_active),
                R.drawable.ic_check_circle_24dp,
                false
        );
    }

    @Override
    public void showNoDatas() {
        showNoTasksViews(
                getResources().getString(R.string.no_tasks_all),
                R.drawable.ic_assignment_turned_in_24dp,
                false
        );
    }

    @Override
    public void showNoCompletedDatas() {
        showNoTasksViews(
                getResources().getString(R.string.no_tasks_completed),
                R.drawable.ic_verified_user_24dp,
                false
        );
    }

    private void showNoTasksViews(String mainText, int iconRes, boolean showAddView) {
        mTasksView.setVisibility(View.GONE);
        mNoTasksView.setVisibility(View.VISIBLE);

        mNoTaskMainView.setText(mainText);
        mNoTaskIcon.setImageDrawable(getResources().getDrawable(iconRes));
        mNoTaskAddView.setVisibility(showAddView ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showActiveFilterLabel() {
        mFilteringLabelView.setText(getResources().getString(R.string.label_active));
    }

    @Override
    public void showCompletedFilterLabel() {
        mFilteringLabelView.setText(getResources().getString(R.string.label_completed));
    }

    @Override
    public void showAllFilterLabel() {
        mFilteringLabelView.setText(getResources().getString(R.string.label_all));
    }

    @Override
    public void showAddData() {
        /*
        Intent intent = new Intent(getContext(), AddEditTaskActivity.class);
        startActivityForResult(intent, AddEditTaskActivity.REQUEST_ADD_TASK);
        */
        Toast.makeText(getContext(), "showAddTask", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void showLoadDataError() {
        showMessage(getString(R.string.loading_tasks_error));
    }

    private void showMessage(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    private static class MainAdapter extends BaseAdapter {

        private List<MainResp.MainBean> mMainDatas;
        private MainItemListener mItemListener;

        public MainAdapter(List<MainResp.MainBean> mainDatas, MainItemListener itemListener) {
            setList(mainDatas);
            mItemListener = itemListener;
        }

        public void replaceData(List<MainResp.MainBean> mainDatas) {
            setList(mainDatas);
            notifyDataSetChanged();
        }

        private void setList(List<MainResp.MainBean> mainDatas) {
            mMainDatas = checkNotNull(mainDatas);
        }

        @Override
        public int getCount() {
            return mMainDatas.size();
        }

        @Override
        public MainResp.MainBean getItem(int i) {
            return mMainDatas.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View rowView = view;
            if (rowView == null) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                rowView = inflater.inflate(R.layout.task_item, viewGroup, false);
            }

            final MainResp.MainBean mainData = getItem(i);

            TextView titleTV = (TextView) rowView.findViewById(R.id.title);
            // titleTV.setText(mainData.getTitleForList());

            CheckBox completeCB = (CheckBox) rowView.findViewById(R.id.complete);

            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemListener.onClick(mainData);
                }
            });

            return rowView;
        }
    }

    public interface MainItemListener {

        void onClick(MainResp.MainBean clickedTask);

        void onCompleteClick(MainResp.MainBean completedTask);

        void onActivateClick(MainResp.MainBean activatedTask);
    }

}
