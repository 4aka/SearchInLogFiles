package org.arch.incorp;

import java.io.*;

import static org.arch.incorp.ArchiveActions.*;
import static org.arch.incorp.CmdActions.downloadArchiveWithLogs;
import static org.arch.incorp.FileActions.deleteTempFiles;
import static org.arch.incorp.SearchActions.*;

public class Main {

    public static void main(String[] args) throws IOException {

        // Delete temp files
         deleteTempFiles();

        // Download archive
         downloadArchiveWithLogs();

        // Extract files
        unzipLogsFromArchive();

        // Search errors
        searchErrorsInLogFiles();
    }
}
