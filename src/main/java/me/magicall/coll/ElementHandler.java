/**
 *
 */
package me.magicall.coll;

import java.util.function.Consumer;

/**
 * 本接口定义了对集合元素的操作.
 *
 * @author Administrator
 */
@FunctionalInterface
public interface ElementHandler<E> {

    /**
     * 对集合中的元素进行的操作
     *
     * @param index 此元素在集合中的下标
     * @param element 此元素本身
     */
    boolean execute(int index, E element);

    static <E> ElementHandler<E> fromConsumer(final Consumer<E> consumer) {
        return (index, element) -> {
            consumer.accept(element);
            return true;
        };
    }
}
