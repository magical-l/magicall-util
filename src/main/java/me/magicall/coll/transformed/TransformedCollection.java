package me.magicall.coll.transformed;

import me.magicall.coll.ElementTransformer;
import me.magicall.coll.unmodifiable.UnmodifiableCollectionTemplate;
import me.magicall.coll.unmodifiable.UnmodifiableIteratorTemplate;
import me.magicall.mark.Unmodifiable;
import me.magicall.mark.Wrapper;
import me.magicall.util.kit.Kits;

import java.util.Collection;
import java.util.Iterator;

public class TransformedCollection<F, T> extends UnmodifiableCollectionTemplate<T>//
        implements Collection<T>, Unmodifiable, Wrapper<Collection<T>> {

    protected final Collection<? extends F> collection;
    protected final ElementTransformer<? super F, ? extends T> transformer;

    public TransformedCollection(final Collection<? extends F> collection,
                                 final ElementTransformer<? super F, ? extends T> transformer) {
        super();
        this.collection = Kits.COLL.checkToEmptyValue(collection);
        this.transformer = transformer;
    }

    protected T transform(final int index, final F from) {
        return transformer.transform(index, from);
    }

    @Override
    protected Iterator<T> iterator0() {
        return new UnmodifiableIteratorTemplate<T>() {

            int index;
            Iterator<? extends F> iter = collection.iterator();

            @Override
            public boolean hasNext() {
                return iter.hasNext();
            }

            @Override
            public T next() {
                final T result = transform(index, iter.next());
                index++;
                return result;
            }
        };
    }

    @Override
    public int size() {
        return collection.size();
    }

    @Override
    public Collection<T> unwrap() {
        return this;
    }
}
