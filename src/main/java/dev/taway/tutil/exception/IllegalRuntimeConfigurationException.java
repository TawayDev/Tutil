package dev.taway.tutil.exception;

public class IllegalRuntimeConfigurationException extends RuntimeException{
    public IllegalRuntimeConfigurationException(){}
    public IllegalRuntimeConfigurationException(String message) {
        super(message);
    }
}
