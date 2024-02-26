package dev.taway.tutil.data;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Pair<T, S> {
    private T first;
    private S second;

    public Pair(T first, S second) {
        this.first = first;
        this.second = second;
    }

    public Class<?> getFirstType() {
        return first != null ? first.getClass() : null;
    }

    public Class<?> getSecondType() {
        return second != null ? second.getClass() : null;
    }

}