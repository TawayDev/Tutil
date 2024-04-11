package dev.taway.tutil.data;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Trio<T, S, I> {
    private T first;
    private S second;
    private I third;

    public Trio(T first, S second, I third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public Class<?> getFirstType() {
        return first != null ? first.getClass() : null;
    }

    public Class<?> getSecondType() {
        return second != null ? second.getClass() : null;
    }

    public Class<?> getThirdType() {
        return third != null ? third.getClass() : null;
    }
}
