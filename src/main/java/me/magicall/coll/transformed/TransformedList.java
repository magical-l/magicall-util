package me.magicall.coll.transformed;

import me.magicall.coll.ElementTransformer;
import me.magicall.coll.unmodifiable.UnmodifiableListTemplate;

import java.util.List;
import java.util.RandomAccess;

/**
 * 把一个list包装成另一种元素的list,作为源list的一种特殊的视图
 *
 * @param <F>
 * @param <T>
 * @author MaGiCalL
 */
public class TransformedList<F, T> extends UnmodifiableListTemplate<T> {

    private final List<? extends F> list;
    private final ElementTransformer<? super F, ? extends T> transformer;

    public TransformedList(final List<? extends F> list, final ElementTransformer<? super F, ? extends T> transformer) {
        super();
        this.list = list;
        this.transformer = transformer;
    }

    @Override
    protected T get0(final int index) {
        return transformer.transform(index, list.get(index));
    }

    @Override
    public int size() {
        return list.size();
    }

    //----------------------------------------------------------

    public static class RandomAccessTransformedList<F, T> extends TransformedList<F, T>//
            implements RandomAccess {

        public RandomAccessTransformedList(final List<? extends F> list,
                                           final ElementTransformer<? super F, ? extends T> transformer) {
            super(list, transformer);
        }
    }
}
