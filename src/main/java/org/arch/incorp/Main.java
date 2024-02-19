package org.arch.incorp;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {

    /*
    * Add logic for different OS [Win, macOS]
    */

    private static final String SLASH = "\\";
    private static final String SEARCH_LINES_FILE = "searchLines";
    private static final String PROJECT_DIR = System.getProperty("user.dir");
    private static final String LOGS_DIRECTORY = PROJECT_DIR + SLASH + "LOGS" + SLASH;

    public static void main(String[] args) {
        findErrorsInLogs();
        afterMethod();
    }

    /**
     * Print error and file name
     */
    private static void findErrorsInLogs() {
        List<String> fileNames = getLogFilesNames();
        String today = getCurrentDate();

        // In LOG Files
        for (String fileName : fileNames) {
            try {
                Scanner scanner = new Scanner(new File(LOGS_DIRECTORY + fileName));

                // If file has next line
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine().toLowerCase();

                    // Get line for search
                    for(String searchLine : getDataToSearch()) {

                        // TODO add time validation
                        // Check if the lines start with current date.
                        // Print file name and error
                        if (line.startsWith(today) && line.contains(searchLine.toLowerCase())) {
                            System.err.println();
                            System.err.println();
                            printErrorName(searchLine);
                            printFileName(fileName);
                            printLineWhereErrorWasFound(line);
                            System.err.println();
                            System.err.println();
                        }
                    }
                }
                scanner.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /* PRINTING */

    /**
     *
     * @param fileName
     */
    private static void printFileName(String fileName) {
        System.err.println("File contains an error: " + fileName);
        System.err.println("-----------------------------------------------");
    }

    /**
     *
     * @param error
     */
    private static void printErrorName(String error) {
        System.err.println("-----------------------------------------------");
        System.err.println("Error name: " + error);
        System.err.println("-----------------------------------------------");
    }

    /**
     *
     * @param error
     */
    private static void printLineWhereErrorWasFound(String error) {
        System.err.println("Line with error: " + error);
        System.err.println("-----------------------------------------------");
    }

    /* ACTIONS */

    /**
     * Read 'searchLines' file line by line
     * @return list of lines to search
     */
    private static List<String> getDataToSearch() {
        List<String> lines = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(new File(SEARCH_LINES_FILE));

            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return lines;
    }

    /**
     * Reminder
     */
    private static void afterMethod() {
        System.err.println("-----------------------------------------------");
        System.err.println("-----------------------------------------------");
        System.err.println("==== DO NOT FORGET TO CLEAN UP LOGS FOLDER ====");
        System.err.println("-----------------------------------------------");
        System.err.println("-----------------------------------------------");
    }

    /**
     *
     * @return list of files in LOGS directory
     */
    private static List<String> getLogFilesNames() {
        List<String> results = new ArrayList<>();
        File[] files = new File(LOGS_DIRECTORY).listFiles();

        for (File file : files) {
            if (file.isFile()) {
                results.add(file.getName());
            }
        }
        return results;
    }

    /**
     *
     * @return
     */
    private static String getCurrentDate() {
        // 2024-02-12
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        df.setTimeZone(tz);
        return df.format(new Date());
    }
}
