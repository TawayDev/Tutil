package dev.taway.tutil.data;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Trio<T, S, I> extends Pair {
    private I third;

    public Trio(T first, S second, I third) {
        super(first, second);
        this.third = third;
    }

    public Class<?> getThirdType() {
        return third != null ? third.getClass() : null;
    }
}
