package dev.taway.tutil.exception.event;

public class EventNotBroadcastedException extends RuntimeException {
    public EventNotBroadcastedException() {
    }

    public EventNotBroadcastedException(String message) {
        super(message);
    }
}
