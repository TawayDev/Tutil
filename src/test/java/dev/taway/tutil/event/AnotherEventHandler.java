package dev.taway.tutil.event;

import dev.taway.tutil.event.annotation.EventHandler;
import dev.taway.tutil.event.annotation.EventProcessingPriority;
import dev.taway.tutil.logging.Logger;

@EventHandler(eventName = "TestEvent", eventProcessingPriority = EventProcessingPriority.HIGH)
public class AnotherEventHandler implements IEventHandler {
    private static final Logger logger = new Logger();

    @Override
    public void onEvent() {
        logger.info("High priority event broadcasted!");
    }
}
