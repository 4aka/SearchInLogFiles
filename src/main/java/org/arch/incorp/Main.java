package org.arch.incorp;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

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

        // In LOG Files
        for (String fileName : fileNames) {
            try {
                Scanner scanner = new Scanner(new File(LOGS_DIRECTORY + fileName));

                // If file has next line
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine().toLowerCase();

                    // Get line for search
                    for(String searchLine : getDataToSearch()) {

                        // Print file name and error
                        if (line.contains(searchLine.toLowerCase())) {
                            printFileName(fileName);
                            printErrorName(searchLine);
                        }
                    }
                }
                scanner.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @param fileName
     */
    private static void printFileName(String fileName) {
        System.err.println("-----------------------------------------------");
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
}