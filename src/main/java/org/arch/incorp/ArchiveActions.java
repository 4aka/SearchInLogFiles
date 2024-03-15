package org.arch.incorp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.arch.incorp.PathActions.ZIP_FILE_NAME;

public class ArchiveActions {

    /**
     * Unzip file
     *
     * @throws IOException
     */
    public static void unzipLogsFromArchive() throws IOException {
        String zipPath = PathActions.USER_DIR + PathActions.getSlash() + ZIP_FILE_NAME;
        File destinationDir = new File(PathActions.getLogsDirectory());

        byte[] buffer = new byte[1024];
        ZipInputStream zis = new ZipInputStream(new FileInputStream(zipPath));
        ZipEntry zipEntry = zis.getNextEntry();

        while (zipEntry != null) {
            if (zipEntry.getName().contains("LogFiles")) {
                File newFile = FileActions.newFile(destinationDir, zipEntry);

                if (zipEntry.isDirectory()) {
                    if (!newFile.isDirectory() && !newFile.mkdirs()) {
                        throw new IOException("Failed to create directory " + newFile);
                    }
                } else {
                    // fix for Windows-created archives
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
