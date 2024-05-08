package org.arch.incorp;

import java.io.IOException;

public class CmdActions {

    public static void downloadArchiveWithLogs() throws IOException {
        Runtime
                .getRuntime()
                        .exec("cmd /c start \"\" download.bat");

        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
