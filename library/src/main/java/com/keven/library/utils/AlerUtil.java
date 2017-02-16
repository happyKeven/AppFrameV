package com.keven.library.utils;


import android.app.ProgressDialog;
import android.content.Context;


/**
 * <p>Title:AlerUtil</p>
 * <p>Description:${}
 * <p>Copyright:Copyright(c)2016</p>
 *
 * @author keven
 *         created at 2016/12/5 14:36
 */
public class AlerUtil {
    private static final String         TEL             = "18565893595";
    private static       ProgressDialog mProgressDialog = null;

    public static ProgressDialog progressDialogShow(Context context, String title, String message) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.setIndeterminate(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(100);
        progressDialog.incrementProgressBy(30);
        progressDialog.incrementSecondaryProgressBy(70);
        progressDialog.setCancelable(false);
        progressDialog.show();

        mProgressDialog = progressDialog;
        return progressDialog;
    }

    public static void progressDialogDismiss() {
        if (null != mProgressDialog) {
            mProgressDialog.dismiss();
            ;
            mProgressDialog = null;
        }
    }
}
