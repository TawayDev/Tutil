package dev.taway.tutil.event;

import dev.taway.tutil.data.Trio;
import dev.taway.tutil.event.annotation.Event;
import dev.taway.tutil.event.processor.EventProcessingPriority;
import dev.taway.tutil.event.processor.EventProcessor;
import dev.taway.tutil.event.processor.IEventHandler;
import dev.taway.tutil.exception.event.EventNotBroadcastedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class EventProcessorTest {

    static boolean addedEventHandlers = false;

    /**
     * Registers event handlers and verifies their registration process.
     * Checks if event handlers are registered with the correct priority value.
     */
    @Test
    public void testEventPrioritySorting() {
        if (!addedEventHandlers) {
            EventProcessor.registerEventHandler(new TestEventHandler());
            EventProcessor.registerEventHandler(new AnotherEventHandler());
            addedEventHandlers = true;
        }

        ArrayList<Trio<EventProcessingPriority, String, IEventHandler>> eventHandlers = EventProcessor.getEventHandlers();
        EventProcessingPriority first = (EventProcessingPriority) eventHandlers.get(0).getX();
        EventProcessingPriority second = (EventProcessingPriority) eventHandlers.get(1).getX();

        Assertions.assertEquals(2, first.priorityValue);
        Assertions.assertEquals(1, second.priorityValue);
    }

    /**
     * Test the broadcast of a specific event.
     * This method marks the test with the {@link Event} annotation and specifies the event name as "TestEvent".
     * It then calls the {@link EventProcessor#broadcastEvent(String)} method to broadcast the event with the specified name.
     * If no event handler is found for the event name, an {@link EventNotBroadcastedException} is thrown.
     */
    @Test
    @Event(eventName = "TestEvent")
    public void testEventBroadcast() {
        if (!addedEventHandlers) {
            EventProcessor.registerEventHandler(new TestEventHandler());
            EventProcessor.registerEventHandler(new AnotherEventHandler());
            addedEventHandlers = true;
        }
        EventProcessor.broadcastEvent();
    }

    @Test
    public void testEventBroadcastWithoutAnnotation() {
        EventProcessor.broadcastEvent("TestEvent");
    }
}