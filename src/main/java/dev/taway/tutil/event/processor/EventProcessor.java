package dev.taway.tutil.event.processor;

import dev.taway.tutil.data.Trio;
import dev.taway.tutil.event.annotation.Event;
import dev.taway.tutil.event.annotation.EventHandler;
import dev.taway.tutil.exception.event.EventAnnotationNotEligibleException;
import dev.taway.tutil.exception.event.EventHandlerNotEligible;
import dev.taway.tutil.exception.event.EventNotBroadcastedException;
import dev.taway.tutil.logging.Logger;
import lombok.Getter;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * The EventProcessor class is responsible for managing and processing event handlers.
 */
public class EventProcessor {
    private static final Logger logger = new Logger();
    @Getter
    private static ArrayList<Trio<EventProcessingPriority, String, IEventHandler>> eventHandlers = new ArrayList<>();
    /**
     * Registers an event handler and adds it to the list of event handlers.
     *
     * @param clazz The event handler to register.
     * @throws EventHandlerNotEligible if the event handler class does not have the {@link EventHandler} annotation or if it is a duplicate.
     */
    public static void registerEventHandler(IEventHandler clazz) {
        try {
//            Check validity
            if (!clazz.getClass().isAnnotationPresent(EventHandler.class))
                throw new EventHandlerNotEligible("EventHandler annotation is not present on class!");
//            Search for duplicates
            for (Trio<EventProcessingPriority, String, IEventHandler> handler : eventHandlers) {
                if (handler.getZ().getClass().equals(clazz.getClass())) {
                    throw new EventHandlerNotEligible("Duplicate event handler registration is not allowed!");
                }
            }
//            Register
            EventHandler eventAnnotation = clazz.getClass().getAnnotation(EventHandler.class);

            eventHandlers.add(new Trio<>(
                    eventAnnotation.eventProcessingPriority(),
                    eventAnnotation.eventName(),
                    clazz
            ));

            logger.trace("Registered event handler: " + eventAnnotation.eventProcessingPriority().name() + " " + eventAnnotation.eventName());
            sortEventHandlers();
        } catch (EventHandlerNotEligible e) {
            logger.error("Error registering event handler: " + e);
        }
    }

    private static void sortEventHandlers() {
//        FIXME: Bubble-sort will be slow on large amounts of data BUT Arrays.sort or Collections.sort does not work well (aka not at all lmao)
        int n = eventHandlers.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                Trio<EventProcessingPriority, String, IEventHandler> temp = eventHandlers.get(j);
                EventProcessingPriority priority = (EventProcessingPriority) temp.getX();
                EventProcessingPriority priority2 = (EventProcessingPriority) eventHandlers.get(j + 1).getX();

                if (priority.getPriority() < priority2.getPriority()) {
                    eventHandlers.set(j, eventHandlers.get(j + 1));
                    eventHandlers.set(j + 1, temp);
                }
            }
        }
    }

    /**
     * Broadcasts an event based on the Event annotation of the calling method.
     * If the calling method is not annotated with Event or the event name is empty, an exception is thrown.
     * The event name is extracted from the Event annotation and passed to the overloaded method broadcastEvent(String).
     * If the event name does not have any event handlers registered, an exception is thrown.
     *
     * @throws EventAnnotationNotEligibleException if the calling method is not annotated with Event or the event name is empty.
     * @throws EventNotBroadcastedException if no event handlers are registered for the event name.
     */
    public static void broadcastEvent() {
        try {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            StackTraceElement caller = stackTrace[2];
            Method method = Class.forName(caller.getClassName()).getMethod(caller.getMethodName());
            Event eventAnnotation = method.getAnnotation(Event.class);

            if (eventAnnotation == null)
                throw new EventAnnotationNotEligibleException("Method calling event broadcast is not annotated with Event annotation.");
            if (eventAnnotation.eventName().isEmpty() || eventAnnotation.eventName().isBlank())
                throw new EventAnnotationNotEligibleException("Method calling event broadcast has empty event name.");

            broadcastEvent(eventAnnotation.eventName());
        } catch (NoSuchMethodException | ClassNotFoundException | EventNotBroadcastedException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * Broadcasts an event with the specified event name.
     * Calls the onEvent method of the registered event handlers
     * that are associated with the event name.
     * If no event handlers are registered for the event name
     * or if the event name is null, an exception is thrown.
     *
     * @param eventName The name of the event to broadcast.
     * @throws IllegalArgumentException if the eventName parameter is null.
     * @throws EventNotBroadcastedException if no event handlers are registered for the event name.
     */
    public static void broadcastEvent(String eventName) {
        try {
            if (eventName == null) throw new IllegalArgumentException("Parameter eventName must not be null");
            if (eventHandlers.isEmpty())
                throw new EventNotBroadcastedException("No event handlers registered!");
            boolean eventBroadcasted = false;

            for (Trio<EventProcessingPriority, String, IEventHandler> handler : eventHandlers) {
                if (handler.getY().equals(eventName)) {
                    handler.getZ().onEvent();
                    eventBroadcasted = true;
                }
            }

            if (!eventBroadcasted)
                throw new EventNotBroadcastedException("Event " + eventName + " does not have a event handler!");
        } catch (EventNotBroadcastedException e) {
            logger.error(e.getMessage());
        }
    }
}