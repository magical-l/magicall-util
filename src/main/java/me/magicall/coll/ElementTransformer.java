/**
 *
 */
package me.magicall.coll;

import java.util.function.Function;

@FunctionalInterface
public interface ElementTransformer<F, T> {

    T transform(int index, F element);

    static <F, T> ElementTransformer<F, T> fromFunction(final Function<F, T> function) {
        return (index, element) -> function.apply(element);
    }
}
