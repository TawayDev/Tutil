package dev.taway.tutil.logging.buffer;

import dev.taway.tutil.RuntimeConfig;
import dev.taway.tutil.io.file.File;
import dev.taway.tutil.logging.Logger;
import dev.taway.tutil.time.Stopwatch;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LogBuffer {
    private static final Logger log = new Logger();
    @Getter private static List<LogObject> logBuffer = Collections.synchronizedList(new ArrayList<>());

    public static synchronized void addLog(LogObject logObject) {
        logBuffer.add(logObject);

        if(RuntimeConfig.LOGGING.BUFFER.MAX_LOG_QUEUE_BEFORE_FORCE_FLUSH == logBuffer.size()) {
            log.trace("Maximum log queue [" + RuntimeConfig.LOGGING.BUFFER.MAX_LOG_QUEUE_BEFORE_FORCE_FLUSH + " logs] reached!");
            LogBufferFlushScheduler.runPreemptively();
        }
    }

    public static synchronized void flushBuffer() {
//        Measure flush time because why not yknow
        Stopwatch stopwatch = new Stopwatch();
        stopwatch.start();

        log.trace("Flushing log buffer.");

        String previousLogPath = "";
        File file = null;
        int logsFlushed = 0;

        try {
            for(LogObject logObject : logBuffer) {
//                If null create new one. This is executed during the very first iteration
                if(file == null) {
                    file = new File(logObject.getFilePath());
                    previousLogPath = logObject.getFilePath();
                }
//                If the output path changes
                if(!file.getPath().equals(previousLogPath)) {
                    file.close();
                    file = new File(logObject.getFilePath());
                    file.setKeepOpen(true);
                    previousLogPath = logObject.getFilePath();
                }
//                FIXME: Add file open, close and a way to not auto-close file stream.
                file.append(logObject.getMessage(), false,true);

                logsFlushed++;
            }
        } catch (Exception exception) {
//            Very bad thing... idc. This needs to be known!!
            boolean prevLogToConsole = RuntimeConfig.LOGGING.DISABLE_LOG_CONSOLE;
            boolean prevLogToFile = RuntimeConfig.LOGGING.DISABLE_LOG_FILE;
            RuntimeConfig.LOGGING.DISABLE_LOG_CONSOLE = false;
            RuntimeConfig.LOGGING.DISABLE_LOG_FILE = true;
            log.error("Flushed " + logsFlushed + "/" + logBuffer.size() + " logs, but an unexpected error has occurred! " + exception);
            RuntimeConfig.LOGGING.DISABLE_LOG_CONSOLE = prevLogToConsole;
            RuntimeConfig.LOGGING.DISABLE_LOG_FILE = prevLogToFile;
//            Remove log objects that have been flushed already.
            if (logsFlushed > 0) {
                logBuffer.subList(0, logsFlushed).clear();
            }
        } finally {
            if(file != null)
                file.close();
            logBuffer.clear();
        }

        stopwatch.stop();
        log.trace("Flushed " + logsFlushed + " logs in " + stopwatch.getElapsedMillis() + "ms");
    }
}
