package dev.taway.tutil.event.processor;

public enum EventProcessingPriority {
    HIGH(2),
    MEDIUM(1),
    LOW(0);

    public final int priorityValue;

    EventProcessingPriority(int priorityValue) {
        this.priorityValue = priorityValue;
    }

    public int getPriority() {
        return priorityValue;
    }
}
