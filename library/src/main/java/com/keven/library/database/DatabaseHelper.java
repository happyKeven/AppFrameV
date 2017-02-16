package com.keven.library.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.support.ConnectionSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.concurrent.Callable;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * <p>Title:DatabaseHelper</p>
 * <p>Description:${}
 * <p>Copyright:Copyright(c)2016</p>
 *
 * @author keven
 *         created at 2016/12/5 15:19
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
	private static volatile DatabaseHelper mInstance;
	private static final String DB_NAME    = "xxx.db";
	private static final int    DB_VERSION = 1;
	private Context             mContext;
	private HashMap<Class, Dao> mDaoMap;

	public DatabaseHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		mContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase database,
						 ConnectionSource connectionSource) {
		/**
		 try {
		 TableUtils.createTableIfNotExists(connectionSource, DownloadItem.class);
		 } catch (SQLException e) {
		 e.printStackTrace();
		 }
		 */
	}

	@Override
	public void onUpgrade(SQLiteDatabase database,
						  ConnectionSource connectionSource, int oldVersion, int newVersion) {
	}

	@SuppressWarnings("unchecked")
	public <D extends Dao<T, ?>, T> D getDao(Class<T> clazz) {
		D dao = (D) mDaoMap.get(clazz);
		try {
			if (dao == null) {
				dao = super.getDao(clazz);
				mDaoMap.put(clazz, dao);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			return (D) super.getDao(clazz);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public <V> V callInTransaction(Callable<V> callable) throws SQLException {
		return TransactionManager.callInTransaction(getConnectionSource(), callable);
	}

	public void close() {
		super.close();
		mContext = null;
		if (null != mDaoMap) {
			mDaoMap.clear();
		}
	}

	/**
	 * 导出数据库
	 */
	public void exportDb() {
		Observable.just(DB_NAME)
				.map(new Func1<String, File>() {
					@Override
					public File call(String dbName) {
						File dbFile = mContext.getDatabasePath(dbName);
						if (!dbFile.exists()) {
							throw new RuntimeException("no file");
						} else
							return dbFile;
					}
				})
				.subscribeOn(Schedulers.io())
				.observeOn(Schedulers.io())
				.subscribe(new Subscriber<File>() {
					@Override
					public void onCompleted() {
					}

					@Override
					public void onError(Throwable e) {
						Log.e("Export DB", "Export DB Error: " + e.getMessage());
						//                        Toast.makeText(mContext, "导出数据库失败 " + e.getMessage(), Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onNext(File file) {
						Log.e("Export DB", "Export DB Success: " + file.getPath());
						//                        Toast.makeText(mContext, "导出数据成功 " + file.getPath(), Toast.LENGTH_SHORT).show();
						InputStream fis = null;
						OutputStream fos = null;
						try {
							fis = new FileInputStream(file);
							fos = new FileOutputStream(new File(mContext.getCacheDir().getAbsolutePath() + "/" + file.getName()));
							byte[] buf = new byte[1024];
							int length;
							while ((length = fis.read(buf)) != -1) {
								fos.write(buf, 0, length);
							}
						} catch (IOException e) {
							e.printStackTrace();
						} finally {
							try {
								if (fis != null)
									fis.close();
								if (fos != null)
									fos.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				});
	}

	public static DatabaseHelper getHelper(Context context) {
			context = context.getApplicationContext();
			if (mInstance == null) {
				synchronized (DatabaseHelper.class) {
					if (mInstance == null)
						mInstance = new DatabaseHelper(context);
				}
			}
			return mInstance;
	}
}
