package il.ac.haifa.cs.sweng.cms;

public class Log {

    private static final String TAG_DEBUG = "DEBUG";
    private static final String TAG_INFO = "INFO";
    private static final String TAG_WARNING = "WARNING";
    private static final String TAG_ERROR = "ERROR";

    private static void log(final String LEVEL_TAG, final String CLASS_TAG, String msg) {
        System.out.println("["+LEVEL_TAG+"] " + "["+CLASS_TAG+"] " + msg);
    }

    protected static void d(final String TAG, String msg) {
        log(TAG_DEBUG, TAG, msg);
    }

    protected static void i(final String TAG, String msg) {
        log(TAG_INFO, TAG, msg);
    }

    protected static void w(final String TAG, String msg) {
        log(TAG_WARNING, TAG, msg);
    }

    protected static void e(final String TAG, String msg) {
        log(TAG_ERROR, TAG, msg);
    }

}
