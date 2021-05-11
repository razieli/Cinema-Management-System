package il.ac.haifa.cs.sweng.cms.util;

/**
 * Allows logging messages with level and class tags.
 *
 * @author Yuval Razieli
 */
public class Log {

    // Log level tags.
    private static final String TAG_DEBUG = "DEBUG";
    private static final String TAG_INFO = "INFO";
    private static final String TAG_WARNING = "WARNING";
    private static final String TAG_ERROR = "ERROR";

    /**
     * Prints a log message with the given level and class tags.
     * @param LEVEL_TAG Log level tag.
     * @param CLASS_TAG Tag of the calling class.
     * @param msg Log message.
     */
    private static void log(final String LEVEL_TAG, final String CLASS_TAG, String msg) {
        System.out.println("["+LEVEL_TAG+"] " + "["+CLASS_TAG+"] " + msg);
    }

    /**
     * Logs a debug message.
     * @param TAG Tag of the calling class.
     * @param msg Log message.
     */
    public static void d(final String TAG, String msg) {
        log(TAG_DEBUG, TAG, msg);
    }

    /**
     * Logs an info message.
     * @param TAG Tag of the calling class.
     * @param msg Log message.
     */
    public static void i(final String TAG, String msg) {
        log(TAG_INFO, TAG, msg);
    }

    /**
     * Logs a warning message.
     * @param TAG Tag of the calling class.
     * @param msg Log message.
     */
    public static void w(final String TAG, String msg) {
        log(TAG_WARNING, TAG, msg);
    }

    /**
     * Logs an error message.
     * @param TAG Tag of the calling class.
     * @param msg Log message.
     */
    public static void e(final String TAG, String msg) {
        log(TAG_ERROR, TAG, msg);
    }

}
