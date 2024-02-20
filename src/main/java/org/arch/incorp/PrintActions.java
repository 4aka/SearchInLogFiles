package org.arch.incorp;

public class PrintActions {

    public static void printDataAboutError(String errorName, String fileName, String lineWithError) {
        printDivider();
        printErrorName(errorName);
        printFileName(fileName);
        printLineWhereErrorWasFound(lineWithError);
        printDivider();
    }

    /**
     * STEP 1
     * @param fileName
     */
    public static void printFileName(String fileName) {
        System.err.println("File contains an error: " + fileName);
        System.err.println("-----------------------------------------------");
    }

    /**
     * STEP 2
     * @param errorName
     */
    public static void printErrorName(String errorName) {
        System.err.println("-----------------------------------------------");
        System.err.println("Error name: " + errorName);
        System.err.println("-----------------------------------------------");
    }

    /**
     * STEP 3
     * @param lineWithError
     */
    public static void printLineWhereErrorWasFound(String lineWithError) {
        System.err.println("Line with error: " + lineWithError);
        System.err.println("-----------------------------------------------");
    }

    /**
     * STEP 0
     * ...
     * STEP 4
     */
    public static void printDivider() {
        System.err.println();
        System.err.println();
    }
}
