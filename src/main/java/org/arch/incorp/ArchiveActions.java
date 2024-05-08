package org.arch.incorp;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.arch.incorp.PathActions.*;
import static org.arch.incorp.PathActions.ZIP_FILE_NAME;
import static org.arch.incorp.PrintActions.*;

public class ArchiveActions {

    /**
     * Unzip file
     *
     * @throws IOException
     */
    public static void unzipLogsFromArchive() throws IOException {
        try {
            print("Waiting for archive downloading...");
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        String zipPath = USER_DIR + getSlash() + ZIP_FILE_NAME;
        File destinationDir = new File(getLogsDirectory());
        ZipInputStream zis = null;
        byte[] buffer = new byte[1024];

        try {
            zis = new ZipInputStream(new FileInputStream(zipPath));
        } catch (FileNotFoundException e) {
            print("File not found by path " + zipPath);
            print("Checking root folder...");
            zipPath = PROJECT_DIR + getSlash() + ZIP_FILE_NAME;

            try {
                zis = new ZipInputStream(new FileInputStream(zipPath));
                print("Extracting...");
            } catch (FileNotFoundException f) {
                print("File not fount by path " + zipPath);
                print("===== Please, copy zip file into project root! =====");
            }
        }
        ZipEntry zipEntry = zis.getNextEntry();

        while (zipEntry != null) {
            if (zipEntry.getName().contains("LogFiles")) {
                File newFile = FileActions.newFile(destinationDir, zipEntry);

                if (zipEntry.isDirectory()) {
                    if (!newFile.isDirectory() && !newFile.mkdirs()) {
                        throw new IOException("Failed to create directory " + newFile);
                    }
                } else {
                    File parent = newFile.getParentFile();
                    if (!parent.isDirectory() && !parent.mkdirs()) {
                        throw new IOException("Failed to create directory " + parent);
                    }
                    // write file content
                    FileOutputStream fos = new FileOutputStream(newFile);
                    int bufferLength;
                    while ((bufferLength = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, bufferLength);
                    }
                    fos.close();
                }
            }
            zipEntry = zis.getNextEntry();
        }
        zis.closeEntry();
        zis.close();
    }
}
