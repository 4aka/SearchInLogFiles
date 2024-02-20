package org.arch.incorp;

public class PathActions {

    public static final String SEARCH_LINES_FILE_NAME = "searchLines";
    public static final String PROJECT_DIR = System.getProperty("user.dir");
    public static final String USER_DIR = System.getProperty("user.home");
    public static final String LOGS_DIRECTORY = PathActions.getLogsDirectory();
    public static final String INNER_LOGS_DIRECTORY_NAME = "LogFiles";

    /**
     * @return correct side slash based on the OS
     */
    public static String getSlash() {
        String osName = getOsName();
        String slash = "";
        if (osName.contains("win")) slash = "\\";
        else if (osName.contains("mac")) slash = "//";
        return slash;
    }

    /**
     * @return LOGS directory path
     */
    public static String getLogsDirectory() {
        String slash = getSlash();
        return PROJECT_DIR + slash + "LOGS" + slash + INNER_LOGS_DIRECTORY_NAME + slash;
    }

    /**
     * @return OS name
     */
    public static String getOsName() {
        return System.getProperty("os.name").toLowerCase();
    }
}
