package com.keven.library.utils;


import android.os.Environment;

import java.io.File;

/**
*<p>Title:IOUtils</p>
*<p>Description:${}
*<p>Copyright:Copyright(c)2016</p>
*@author keven
*created at 2016/12/15 15:49
*@version
*/
public class IOUtils {
    private static final String APPLICATION_FOLDER = "Browser";
    private static final String DOWNLOAD_FOLDER = "MiDownloads";

    /**
     * Get the application folder on the SD Card. Create it if not present.
     *
     * @return The application folder.
     */
    public static File getApplicationFolder() {
        if (isRootCanWrite()) {
            File folder = new File(getRoot(), APPLICATION_FOLDER);
            if (!folder.exists()) {
                folder.mkdir();
            }
            return folder;
        } else {
            return null;
        }
    }

    public static File getRoot() {
        return Environment.getExternalStorageDirectory();
    }

    public static boolean isRootCanWrite() {
        return getRoot().canWrite();
    }

    /**
     * Get the application download folder on the SD Card. Create it if not
     * present.
     *
     * @return The application download folder.
     */
    public static File getDownloadFolder(int tag) {
        File root = getApplicationFolder();

        if (root != null) {
            File folder = null;
            if (tag == 0) {
                folder = new File(root, DOWNLOAD_FOLDER);
            }

            if (folder != null && !folder.exists()) {
                folder.mkdir();
            }

            return folder;

        } else {
            return Environment.getExternalStorageDirectory();
        }
    }
}
