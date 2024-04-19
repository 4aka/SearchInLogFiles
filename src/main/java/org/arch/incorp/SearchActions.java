package org.arch.incorp;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import static org.arch.incorp.FileActions.*;
import static org.arch.incorp.PrintActions.printDataAboutTheError;

public class SearchActions {


    /**
     * Method searches errors in log files
     */
    public static void searchErrorsInLogFiles() {
        List<String> fileNames = getLogFilesNames();
        String today = getCurrentDate();

        // In LOG Files
        for (String fileName : fileNames) {
            try {
                File file = new File(PathActions.LOGS_DIRECTORY_FOR_SEARCH + fileName);
                Scanner scanner = new Scanner(file);

                // In file lines
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine().toLowerCase();

                    // Search errorName
                    for (String errorName : linesToSearch()) {

                        // Print all about the error
                        if (line.contains(errorName.toLowerCase()) && line.startsWith(today)) {
                            printDataAboutTheError(errorName, fileName, line);
                            copyFileWithErrorsToErrorsDirectory(file);
                        }
                    }
                }
                scanner.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
