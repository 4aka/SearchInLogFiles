package org.arch.incorp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;

public class FileActions {

    /**
     * Read 'searchLines' file line by line
     *
     * @return list of lines to search
     */
    public static List<String> getDataToSearch() {
        List<String> lines = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File(PathActions.SEARCH_LINES_FILE_NAME));
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
     * @return list of files in LOGS directory
     */
    public static List<String> getLogFilesNames() {
        List<String> results = new ArrayList<>();
        File[] files = new File(PathActions.LOGS_DIRECTORY_FOR_SEARCH).listFiles();
        for (File file : files) {
            if (file.isFile()) {
                results.add(file.getName());
            }
        }
        return results;
    }

    /**
     * @param destinationDir
     * @param zipEntry
     * @return
     * @throws IOException
     */
    public static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destinationFile = new File(destinationDir, zipEntry.getName());

        String destinationDirPath = destinationDir.getCanonicalPath();
        String destinationFilePath = destinationFile.getCanonicalPath();

        if (!destinationFilePath.startsWith(destinationDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }
        return destinationFile;
    }

    /**
     * @return
     */
    public static String getCurrentDate() {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        df.setTimeZone(tz);
        return df.format(new Date()) + "";
    }

}
