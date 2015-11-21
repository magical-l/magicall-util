package me.magicall.coll.transformed;

import me.magicall.coll.ElementTransformer;
import me.magicall.mark.Wrapper;

import java.util.Iterator;

public class TransformedIterator<F, T> implements Iterator<T>, Wrapper<Iterator<F>> {

    private final Iterator<F> source;
    private final ElementTransformer<F, T> tf;
    private int index;

    public TransformedIterator(final Iterator<F> source, final ElementTransformer<F, T> tf, final int index) {
        super();
        this.source = source;
        this.tf = tf;
        this.index = index;
    }

    public TransformedIterator(final Iterator<F> source, final ElementTransformer<F, T> tf) {
        this(source, tf, 0);
    }

    @Override
    public boolean hasNext() {
        return source.hasNext();
    }

    @Override
    public T next() {
        final T result = tf.transform(index, source.next());
        index++;
        return result;
    }

    @Override
    public void remove() {
        source.remove();
    }

    @Override
    public Iterator<F> unwrap() {
        return source;
    }
}
