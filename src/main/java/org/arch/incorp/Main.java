package org.arch.incorp;

import java.io.*;
import java.util.*;

import static org.arch.incorp.ArchiveActions.*;
import static org.arch.incorp.FileActions.*;
import static org.arch.incorp.PrintActions.*;

public class Main {

    public static void main(String[] args) throws IOException {
        // TODO download logs automatically
        unzipLogsFromArchive();
        findErrorsInLogFiles();
    }

    /**
     * Find error. Print error name, error message, file name.
     */
    private static void findErrorsInLogFiles() {
        List<String> fileNames = getLogFilesNames();
        List<String> errorNameListForSearch = getDataToSearch();
        String today = getCurrentDate();

        // In LOG Files
        for (String fileName : fileNames) {
            try {
                Scanner scanner = new Scanner(new File(PathActions.LOGS_DIRECTORY + fileName));

                // In file lines
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine().toLowerCase();

                    // Search errorName
                    for (String errorName : errorNameListForSearch) {

                        // Print all about the error
                        if (line.contains(errorName.toLowerCase()) && line.startsWith(today)) {
                            // TODO add time validation. add parser for ISO 2024-02-12T08:04:05.661244379Z
                            printDataAboutError(errorName, fileName, line);
                        }
                    }
                }
                scanner.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            // TODO delete files without errors after search.
        }
    }
}
