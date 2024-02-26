package dev.taway.tutil.event;

import dev.taway.tutil.event.annotation.EventHandler;
import dev.taway.tutil.event.annotation.EventProcessingPriority;
import dev.taway.tutil.logging.Logger;

@EventHandler(eventName = "TestEvent", eventProcessingPriority = EventProcessingPriority.MEDIUM)
public class TestEventHandler implements IEventHandler {
    private static final Logger logger = new Logger();

    public TestEventHandler() {
    }

    @Override
    public void onEvent() {
        logger.info("Medium priority event broadcasted!");
    }
}
