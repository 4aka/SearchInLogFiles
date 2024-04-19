package org.arch.incorp;

import java.io.IOException;

public class CmdActions {

    public static void downloadArchiveWithLogs() throws IOException {
        // TODO Delete file if exists

        Runtime
                .getRuntime()
                        .exec("cmd /c start \"\" download.bat");

        // TODO waiting for archive
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
