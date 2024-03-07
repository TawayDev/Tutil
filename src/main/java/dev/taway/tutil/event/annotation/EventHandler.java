package dev.taway.tutil.event.annotation;

import dev.taway.tutil.event.processor.EventProcessingPriority;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventHandler {
    String eventName() default "";

    EventProcessingPriority eventProcessingPriority() default EventProcessingPriority.LOW;
}
