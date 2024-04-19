package org.arch.incorp;

public class PathActions {

    public static final String SEARCH_LINES_FILE_NAME = "searchLines";
    public static final String PROJECT_DIR = System.getProperty("user.dir");
    public static final String USER_DIR = System.getProperty("user.home");
    public static final String INNER_LOGS_DIRECTORY_NAME = "LogFiles";
    public static final String LOGS_DIRECTORY_FOR_SEARCH = getLogsDirectory() + getSlash() + INNER_LOGS_DIRECTORY_NAME + getSlash();
    public static final String ZIP_FILE_NAME = "webapp_logs.zip";
    public static final String ERRORS_DIRECTORY_FOR_INVESTIGATION = PROJECT_DIR + getSlash() + "ERRORS" + getSlash();
    public static final String ERRORS_DIRECTORY = PROJECT_DIR + getSlash() + "ERRORS";
    public static final String LOGS_DIRECTORY = PROJECT_DIR + getSlash() + "LOGS";

    /**
     * @return correct side slash based on the OS
     */
    public static String getSlash() {
        if (getOsName().contains("win")) return "\\";
        else return "//";
    }

    /**
     * @return LOGS directory path
     */
    public static String getLogsDirectory() {
        String slash = getSlash();
        return PROJECT_DIR + slash + "LOGS" + slash;
    }

    /**
     * @return OS name
     */
    public static String getOsName() {
        return System.getProperty("os.name").toLowerCase();
    }
}
