package com.keven.library.utils.Logger;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>Title:LoggerPrint</p>
 * <p>Description:Logger第三方库的使用</p>
 * <p>Copyright: Copyright(c)2016</p>
 * User: 刘开峰
 * Date: 2017-02-08
 * Time: 11:15
 */
public class LoggerPrint {
    public static final boolean IS_DEBUG = false;

    public static void log(int logLevel, String tag, String message, Throwable throwable) {
        if (IS_DEBUG) {
            Logger.log(logLevel, tag, message, throwable);
        }
    }

    public static void debugLog(String message) {
        if (IS_DEBUG) {
            Logger.d(message);
        }
    }

    public static void verboseLog(String message) {
        if (IS_DEBUG) {
            Logger.v(message);
        }
    }

    public static void warningLog(String message) {
        if (IS_DEBUG) {
            Logger.w(message);
        }
    }

    public static void errorLog(String message) {
        if (IS_DEBUG) {
            Logger.e(message);
        }
    }

    public static void errorLogWithThrowable(Throwable throwable, String message) {
        if (IS_DEBUG) {
            Logger.e(throwable, message);
        }
    }

    public static void errorLogWithThrowableWithoutMessage(Throwable throwable) {
        if (IS_DEBUG) {
            Logger.e(throwable, null);
        }
    }

    public static void errorLogNoThrowableNoMessage() {
        if (IS_DEBUG) {
            Logger.e(null, null);
        }
    }

    public static void infoLog(String message) {
        if (IS_DEBUG) {
            Logger.i(message);
        }
    }

    public static void wtfLog(String message) {
        if (IS_DEBUG) {
            Logger.wtf(message);
        }
    }

    /**
     *
     * @param array 示例：
     * double[][] doubles = {{1.2, 1.6, 1.7, 30, 33},
     * {1.2, 1.6, 1.7, 30, 33},
     * {1.2, 1.6, 1.7, 30, 33},
     * {1.2, 1.6, 1.7, 30, 33}};
     */
    public static void logArray(Object array) {
        if (IS_DEBUG) {
            Logger.d(array);
        }
    }

    /**
     * @param list 示例：
     *  List<String> list = Arrays.asList("foo", "bar");
     */
    public static void logList(List<String> list) {
        if (IS_DEBUG) {
            Logger.d(list);
        }
    }

    /**
     * @param map 示例:
     * Map<String, String> map = new HashMap<>();
     * map.put("key", "value");
     * map.put("key2", "value2");
     */
    public static void logMap(Map<String, String> map) {
        if (IS_DEBUG) {
            Logger.d(map);
        }
    }

    /**
     * @param set 示例:
     * Set<String> set = new HashSet<>();
     * set.add("key");
     * set.add("key1");
     */
    public static void logSet(Set<String> set) {
        if (IS_DEBUG) {
            Logger.d(set);
        }
    }

    /**
     * @param jsonStr 示例:
     * "  {\"key\":3}"
     */
    public static void jsonLObjectLog(String jsonStr) {
        if (IS_DEBUG) {
            Logger.json(jsonStr);
        }
    }

    /**
     * @param jsonArrayStr 示例:
     * "[{\"key\":3}]"
     */
    public static void jsonArrayLog(String jsonArrayStr) {
        if (IS_DEBUG) {
            Logger.json(jsonArrayStr);
        }
    }

    /**
     * @param xmlStr 示例:
     * "<xml>Test</xml>"
     */
    public static void xmlLog(String xmlStr) {
        if (IS_DEBUG) {
            Logger.xml(xmlStr);
        }
    }


    public static void logWithoutThread(String message) {
        if (IS_DEBUG) {
            Logger.init().hideThreadInfo();
            Logger.i(message);
        }
    }

    public static void logWithCustomTag(String customTag, String message) {
        if (IS_DEBUG) {
            Logger.init(customTag);
            Logger.i(message);
        }
    }

    public static void logWithOneMethodInfo(String message) {
        if (IS_DEBUG) {
            Logger.init().methodCount(1);
            Logger.i(message);
        }
    }

    public static void logWithNoMethodInfo(String message) {
        if (IS_DEBUG) {
            Logger.init().methodCount(0);
            Logger.i(message);
        }
    }

    public static void logWithNoMethodInfoAndNoThreadInfo(String message) {
        if (IS_DEBUG) {
            Logger.init().methodCount(0).hideThreadInfo();
            Logger.i(message);
        }
    }

    public static void logWithOnlyOnceCustomTag(String customTag, String message) {
       if (IS_DEBUG) {
           Logger.init().hideThreadInfo().methodCount(0);
           Logger.t(customTag).i(message);
           Logger.i(message);
       }
    }

    public static void logWithOnlyOnceMethodInfo(String message) {
        if (IS_DEBUG) {
            Logger.init().hideThreadInfo().methodCount(0);
            Logger.t(1).i(message);
            Logger.i(message);
        }
    }

    public static void logWithOnlyOnceMethodInfoAndCustomTag(String customTag, String message) {
        if (IS_DEBUG) {
            Logger.init().hideThreadInfo().methodCount(0);
            Logger.t(customTag, 1).i(message);
            Logger.i(message);
        }
    }

    public static void logNone(String message) {
        if (IS_DEBUG) {
            Logger.init().logLevel(LogLevel.NONE);
            Logger.i(message);
        }
    }

    public static void useDefaultSettingsIfInitNotCalled(String message) {
        if (IS_DEBUG) {
            Logger.i(message);
        }
    }
}
