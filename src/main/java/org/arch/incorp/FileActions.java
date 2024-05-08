package org.arch.incorp;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;

import static org.arch.incorp.PathActions.*;
import static org.arch.incorp.PrintActions.*;

public class FileActions {

    /**
     * Read 'searchLines' file line by line
     *
     * @return list of lines to search
     */
    public static List<String> linesToSearch() {
        List<String> lines = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File(SEARCH_LINES_FILE_NAME));
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
        File[] files = new File(LOGS_DIRECTORY_FOR_SEARCH).listFiles();
        for (File file : files) {
            if (file.isFile()) {
                results.add(file.getName());
            }
        }
        return results;
    }

    public static List<String> getLogFilesNames(String path) {
        List<String> results = new ArrayList<>();
        File[] files = new File(path).listFiles();
        for (File file : files) {
            if (file.isFile()) {
                results.add(file.getName());
            } else {
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

    public static void copyFileWithErrorsToErrorsDirectory(File file) {
        File newFile = new File(ERRORS_DIRECTORY_FOR_INVESTIGATION + file.getName());
        InputStream in;
        OutputStream out;

        try {
            in = new BufferedInputStream(new FileInputStream(file));
            out = new BufferedOutputStream(new FileOutputStream(newFile.getPath()));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            byte[] buffer = new byte[1024];
            int lengthRead;

            while ((lengthRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, lengthRead);
                out.flush();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete all template files and folders in LOGS, ERRORS folders
     */
    public static void deleteTempFiles() {
        // Delete temp files for ERRORS folder
        List<String> fileNames = getLogFilesNames(ERRORS_DIRECTORY);
        if(fileNames.size() > 0) {
            for(String fileName : fileNames) {
                deleteFile(ERRORS_DIRECTORY + getSlash() + fileName);
                print("files deleted from ERRORS dir");
            }
        }

        // Delete temp files for LOGS directory
        fileNames = getLogFilesNames(LOGS_DIRECTORY);
        if(fileNames.size() > 0) {
            for (String fileName : fileNames) {
                deleteFile(LOGS_DIRECTORY + getSlash() + fileName);
                print("files deleted from LOGS dir");
            }
        }

        // Delete webapp_logs.zip
        deleteFile(PROJECT_DIR + getSlash() + ZIP_FILE_NAME);
        print("Zip archive deleted from project root");
    }

    /**
     *
     * @param filePath
     */
    private static void deleteFile(String filePath) {
        File f = new File(filePath);
        try {
            Files.walk(f.toPath())
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        } catch (IOException e) {
            print("No such file: " + filePath);
        }
    }
}
