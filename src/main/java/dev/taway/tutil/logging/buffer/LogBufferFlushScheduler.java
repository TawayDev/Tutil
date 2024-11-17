package dev.taway.tutil.logging.buffer;

import dev.taway.tutil.RuntimeConfig;
import dev.taway.tutil.logging.Logger;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class LogBufferFlushScheduler {
    private static final Logger log = new Logger();
    private static ScheduledExecutorService scheduler;
    private static boolean initialized = false;

    static {
        try {
            scheduler = Executors.newScheduledThreadPool(1);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize scheduler", e);
        }
    }

    private static ScheduledFuture<?> scheduledFuture;
    private static final Runnable task = LogBuffer::flushBuffer;
    private static boolean shutdownHookAdded = false;

    public static void schedule() {
//        Schedule task according to RuntimeConfig
        if (scheduler != null) {
            scheduledFuture = scheduler.scheduleAtFixedRate(
                    task,
                    RuntimeConfig.LOGGING.BUFFER.FLUSH_INITIAL_DELAY,
                    RuntimeConfig.LOGGING.BUFFER.FLUSH_PERIOD,
                    RuntimeConfig.LOGGING.BUFFER.TIME_UNIT
            );
//            ENABLING THIS WILL CREATE A STACK OVERFLOW ERROR LMAOO:
//            log.trace(
//                    "Logging buffer scheduled. Initial delay: " +
//                            RuntimeConfig.LOGGING.BUFFER.FLUSH_INITIAL_DELAY +
//                            " " +
//                            RuntimeConfig.LOGGING.BUFFER.TIME_UNIT.name() +
//                            " Period: " +
//                            RuntimeConfig.LOGGING.BUFFER.FLUSH_PERIOD +
//                            " " +
//                            RuntimeConfig.LOGGING.BUFFER.TIME_UNIT.name()
//            );
        } else {
            throw new IllegalStateException("Scheduler is not initialized");
        }
    }

    public static void runPreemptively() {
        log.trace("Logging buffer ran preemptively!");
        new Thread(() -> {
            scheduledFuture.cancel(false);
//                Reschedule the task to run immediately
            scheduler.schedule(task, 0, TimeUnit.SECONDS);
        }).start();
//        Restart scheduler:
        schedule();
    }

    public static void initializeScheduler() {
        if (initialized) return;
//        Lazy initialize the scheduler if it is not initialized
        if (scheduler == null) {
            scheduler = Executors.newScheduledThreadPool(1);
        }

        addShutdownHook();
        schedule();

        initialized = true;
    }

    private static void addShutdownHook() {
        if(shutdownHookAdded) return;

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("[TUTIL] Shutting down. Flushing log buffer.");
            scheduledFuture.cancel(false);
            scheduler.schedule(task, 0, TimeUnit.SECONDS);
        }));

        shutdownHookAdded = true;
    }
}
