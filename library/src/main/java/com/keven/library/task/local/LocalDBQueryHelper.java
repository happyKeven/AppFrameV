package com.keven.library.task.local;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.dao.RawRowMapper;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.Where;
import com.keven.library.database.DatabaseHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * 本地Sqlite查询工具
 *
 * @author bojuzi.com
 *
 */
public class LocalDBQueryHelper {

	protected static final String TAG = LocalDBQueryHelper.class.getSimpleName();

	/**
	 *
	 * @param clazz
	 * @param processor
	 * @param whereSqlStr
	 */
	//	public static <T> void query(final Class<T> clazz,
	//			final ILocalDBResultProcessor<T> processor, final String whereSqlStr) {
	//		new AsyncTask<Void, Void, List<T>>() {
	//
	//			@Override
	//			protected List<T> doInBackground(Void... params) {
	//				return CommonDao.getInstance().findAllByWhere(clazz, whereSqlStr);
	//			}
	//
	//			protected void onPostExecute(List<T> result) {
	//				if (processor != null) {
	//					processor.onQueryResulted(result);
	//				}
	//			}
	//
	//		}.execute();
	//	}

	//	public static <T> void query(final Class<T> clazz,
	//			final ILocalDBResultProcessor<T> processor,
	//			final String whereSqlStr, final String orderBy, final boolean isAsc) {
	//		new AsyncTask<Void, Void, List<T>>() {
	//
	//			@Override
	//			protected List<T> doInBackground(Void... params) {
	//				return CommonDao.getInstance().findAllByWhere(clazz,
	//						whereSqlStr, orderBy, isAsc);
	//			}
	//
	//			protected void onPostExecute(List<T> result) {
	//				if (processor != null) {
	//					processor.onQueryResulted(result);
	//				}
	//			}
	//
	//		}.execute();
	//	}

	// 不带where条件的，估计用不到喽
	//	@Deprecated
	//	static <T> void queryByPage(final Class<T> clazz,
	//			final ILocalDBResultProcessor<T> processor, final int pageNo) {
	//		new AsyncTask<Void, Void, List<T>>() {
	//
	//			@Override
	//			protected List<T> doInBackground(Void... params) {
	//				return CommonDao.getInstance().findPage(clazz, pageNo);
	//			}
	//
	//			protected void onPostExecute(List<T> result) {
	//				if (processor != null) {
	//					processor.onQueryResulted(result);
	//				}
	//			}
	//
	//		}.execute();
	//	}

	// 不带where条件的，估计用不到喽
	//	@Deprecated
	//	static <T> void queryByPage(final Class<T> clazz,
	//			final ILocalDBResultProcessor<T> processor, final int pageNo,
	//			final String orderBy, final boolean isAsc) {
	//		new AsyncTask<Void, Void, List<T>>() {
	//
	//			@Override
	//			protected List<T> doInBackground(Void... params) {
	//				return CommonDao.getInstance().findPage(clazz, orderBy, pageNo, isAsc);
	//			}
	//
	//			protected void onPostExecute(List<T> result) {
	//				if (processor != null) {
	//					processor.onQueryResulted(result);
	//				}
	//			}
	//
	//		}.execute();
	//	}

	//	public static <T> ICancelable queryByPage(final Class<T> clazz,
	//			final ILocalDBResultProcessor<T> processor, final int pageNo,
	//			final String whereSqlStr) {
	//		final AsyncTask<Void, Void, List<T>> task = new AsyncTask<Void, Void, List<T>>() {
	//
	//			@Override
	//			protected List<T> doInBackground(Void... params) {
	//				return CommonDao.getInstance().findPageByWhere(clazz,
	//						whereSqlStr, pageNo);
	//			}
	//
	//			protected void onPostExecute(List<T> result) {
	//				if (processor != null) {
	//					processor.onQueryResulted(result);
	//				}
	//			}
	//
	//		};
	//		task.execute();
	//		return new  ICancelable() {
	//
	//			@Override
	//			public void cancel() {
	//				task.cancel(true);
	//			}
	//		};
	//	}

	public static <T> ICancelable queryByWhere(Context context, Class<T> clazz,
											   final ILocalDBResultProcessor<T> processor,
											   final Where<T,?> whereSqlStr) {
		Dao<T,?> dao = DatabaseHelper.getHelper(context).getDao(clazz);
		return queryByWhere(dao, processor, whereSqlStr);
	}

	/**
	 *
	 * @param dao
	 * @param processor
	 * @param whereSqlStr
	 * @return
	 */
	public static <T> ICancelable queryByWhere(final Dao<T,?> dao,
											   final ILocalDBResultProcessor<T> processor,
											   final Where<T,?> whereSqlStr) {
		final AsyncTask<Void, Void, List<T>> task = new AsyncTask<Void, Void, List<T>>() {

			@Override
			protected List<T> doInBackground(Void... params) {
				List<T> list = null;
				try {
					PreparedQuery<T> query = whereSqlStr.prepare();
					Log.d(TAG, query.getStatement());
					list = dao.query(query);
				} catch (SQLException e) {
					list = new ArrayList<T>();
					e.printStackTrace();
				}

				return list;
			}

			protected void onPostExecute(List<T> result) {
				if (processor != null) {
					processor.onQueryResulted(result);
				}
			}

		};
		task.execute();
		return new ICancelable() {
			public void cancel() {
				task.cancel(true);
			}
		};
	}

	public interface ICancelable {
		void cancel();
	}

	public static <T> ICancelable queryRaw(Context context, Class<?> clazz,
										   final ILocalDBResultProcessor<T> processor, final String query,
										   final RawRowMapper<T> mapper, final String... arguments) {
		final Dao dao = DatabaseHelper.getHelper(context).getDao(clazz);
		final AsyncTask<Void, Void, List<T>> task = new AsyncTask<Void, Void, List<T>>() {

			@Override
			protected List<T> doInBackground(Void... params) {
				ArrayList<T> list = new ArrayList<T>();
				GenericRawResults<T> rawResult = null;
				try {
					rawResult = dao.queryRaw(query, mapper, arguments);

					for (T t : rawResult) {
						list.add(t);
					}

				} catch (SQLException e) {
					list = new ArrayList<T>();
					e.printStackTrace();
				} finally {
					if (null != rawResult) {
						try {
							rawResult.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}

				return list;
			}

			protected void onPostExecute(List<T> result) {
				if (processor != null) {
					processor.onQueryResulted(result);
				}
			}

		};
		task.execute();
		return new ICancelable() {
			public void cancel() {
				task.cancel(true);
			}
		};
	}
}
