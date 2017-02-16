package com.appframe.data.dao;

import android.content.Context;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.keven.library.data.dao.BaseDao;
import com.keven.library.database.DatabaseHelper;
import com.keven.library.database.UUIDUtil;

/**
*<p>Title:HistoryCountData</p>
*<p>Description:${}
*<p>Copyright:Copyright(c)2016</p>
*@author keven
*created at 2016/12/19 10:43
*@version 
*/
@DatabaseTable(tableName = HistoryCountDao.TABLE_NAME)
public class HistoryCountDao extends BaseDao<HistoryCountDao>{
    public static final String TABLE_NAME = "T_Histroy_Count_Dao";

    @DatabaseField(columnName = "id",id = true)
    private String id = UUIDUtil.getGid();
    public static final String MAIN_URL = "Main_url";
    @DatabaseField(columnName = MAIN_URL)
    public String Main_url;
    public static final String URL_TITLE = "Url_title";
    @DatabaseField(columnName = URL_TITLE)
    public String Url_title;
    public static final String COUNT = "Count";
    @DatabaseField(columnName = COUNT)
    public int Count = 0;

    public HistoryCountDao(Context context) {
        super(DatabaseHelper.getHelper(context));
    }
}
