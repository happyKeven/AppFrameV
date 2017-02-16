package com.keven.library.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.text.DecimalFormat;

/**
 * handy class for fetch resource and do version compatible operation to view.
 *
 * @author bojuzi.com
 * @date 2013-11-16
 */
public class ResUtil {

	/** 用来格式化double数值的整数部分每千位逗号并且保留两位小数的格式化参数 */
	public static final DecimalFormat CURRENCY_TEXT_FORMAT = new DecimalFormat("#,##0.00");

	/** 用来格式化double数值保留两位小数的格式化参数 */
	public static final DecimalFormat CURRENCY_NUMBER_FORMAT = new DecimalFormat("#0.00");

	private ResUtil() {
	}

	public static String getString(Context context, int resId) {
		return context.getString(resId);
	}

	public static String getString(Context context, int resId, Object... formatArgs) {
		return context.getString(resId, formatArgs);
	}

	/**
	 * 方便的转换字符为html格式显示
	 * @param stringResId
	 * @param formatArgs
	 * @return
	 */
	public static Spanned fromHtml(int stringResId, Object... formatArgs) {
		String textNeed2Format = String.valueOf(stringResId);

		Object[] tmpArgs = new Object[formatArgs.length];
		for (int i = 0; i < formatArgs.length; i++) {
			Object arg = formatArgs[i];
			if (arg instanceof CharSequence) {
				// tmpArgs[i] = Html.escapeHtml(arg.toString());
			} else {
				tmpArgs[i] = arg;
			}
		}

		String formattedString = String.format(textNeed2Format, tmpArgs);
		Spanned sp = Html.fromHtml(formattedString);
		return sp;
	}

	public static Drawable getDrawable(Context context, int resId) {
		return context.getResources().getDrawable(resId);
	}

	public static int getColor(Context context, int resId) {
		return context.getResources().getColor(resId);
	}

	/**
	 * 四舍五入保留两位小数
	 * @param money
	 * @return
	 */
	public static double getRoundingMoney(String money) {
		double moneyNum = Double.parseDouble(money);
		DecimalFormat numberFormat = CURRENCY_NUMBER_FORMAT;
		return Double.parseDouble(numberFormat.format(moneyNum));
	}

	/**
	 * 四舍五入保留两位小数
	 * @param money
	 * @return
	 */
	public static double getRoundingMoney(double money) {
		DecimalFormat numberFormat = CURRENCY_NUMBER_FORMAT;
		return Double.parseDouble(numberFormat.format(money));
	}

	public static void setCompoundDrawables(TextView view, Drawable[] drawables) {
		if (drawables.length != 4) {
			throw new IllegalArgumentException("drawables not leng 4");
		}
		for (Drawable draw : drawables) {
			if (draw != null) {
				draw.setBounds(0, 0, draw.getIntrinsicWidth(),
						draw.getIntrinsicHeight());
			}
		}
		view.setCompoundDrawables(drawables[0], drawables[1], drawables[2],
				drawables[3]);
	}

	/**
	public static void setDrawableRight(TextView view, int drawableResId) {
		Drawable[] drawables = new Drawable[] {null, null, getDrawable(drawableResId), null};
		setCompoundDrawables(view, drawables);
	} */

	public static void setDrawableRight(TextView view, Drawable drawable) {
		Drawable[] drawables = new Drawable[] {null, null, drawable, null};
		setCompoundDrawables(view, drawables);
	}

	@TargetApi(11)
	public static <V> void scrollToListItem(ListView list, ArrayAdapter<V> adapter, V item) {
		int position = adapter.getPosition(item);
		int firstVisiblePosition = list.getFirstVisiblePosition();
		if (Build.VERSION.SDK_INT > 10) {
			// 这个方法是API-11定义的，smoothScrollToPosition 是API-8定义的
			list.smoothScrollByOffset(position - firstVisiblePosition);
		} else {
			list.setSelection(position);
		}
	}

	public static int getColor(Drawable drawable, int defaultColor) {
		int color = defaultColor;
		try {
			Class<?> cls = drawable.getConstantState().getClass();
			Field field = cls.getDeclaredField("mUseColor");
			field.setAccessible(true);
			color = field.getInt(drawable.getConstantState());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return color;
	}

}
