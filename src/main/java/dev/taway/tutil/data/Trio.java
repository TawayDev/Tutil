package dev.taway.tutil.data;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Trio<T, S, I> {
    private T x;
    private S y;
    private I z;

    public Trio(T x, S y, I z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Class<?> getTypeX() {
        return x != null ? x.getClass() : null;
    }

    public Class<?> getTypeY() {
        return y != null ? y.getClass() : null;
    }

    public Class<?> getTypeZ() {
        return z != null ? z.getClass() : null;
    }
}
