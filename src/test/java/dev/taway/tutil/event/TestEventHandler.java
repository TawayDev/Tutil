package dev.taway.tutil.event;

import dev.taway.tutil.event.annotation.EventHandler;
import dev.taway.tutil.event.processor.EventProcessingPriority;
import dev.taway.tutil.event.processor.IEventHandler;
import dev.taway.tutil.logging.Logger;

@EventHandler(eventName = "TestEvent", eventProcessingPriority = EventProcessingPriority.MEDIUM)
public class TestEventHandler implements IEventHandler {
    private static final Logger logger = new Logger();

    public TestEventHandler() {
    }

    @Override
    public void onEvent() {
        logger.trace("Medium priority event broadcasted!");
    }
}
