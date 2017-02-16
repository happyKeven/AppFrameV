package com.keven.library.task.local;

import java.util.List;

/**
 * 本地Sqlite查询结果处理器
 * @author bojuzi.com
 *
 * @param <T>
 */
public interface ILocalDBResultProcessor<T> {

	void onQueryResulted(List<T> resultList);

}
