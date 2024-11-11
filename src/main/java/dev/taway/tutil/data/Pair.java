package dev.taway.tutil.data;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Pair<T, S> {
    private T x;
    private S y;

    public Pair(T first, S second) {
        this.x = first;
        this.y = second;
    }

    public Class<?> getTypeX() {
        return x != null ? x.getClass() : null;
    }

    public Class<?> getTypeY() {
        return y != null ? y.getClass() : null;
    }

}