package dev.taway.tutil.exception.event;

public class EventHandlerNotEligible extends RuntimeException {
    public EventHandlerNotEligible() {
    }

    public EventHandlerNotEligible(String message) {
        super(message);
    }
}
