package me.magicall.coll.unmodifiable;

import me.magicall.mark.Unmodifiable;
import me.magicall.mark.Wrapper;

import java.util.Map.Entry;

public abstract class UnmodifiableEntryTemplate<K, V> implements Entry<K, V>, Unmodifiable, Wrapper<Entry<K, V>> {

    @Override
    public final V setValue(final V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Entry<K, V> unwrap() {
        return this;
    }
}
