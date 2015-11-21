package me.magicall.coll.unmodifiable;

import me.magicall.mark.Unmodifiable;

import java.util.Iterator;

public abstract class UnmodifiableIteratorTemplate<E> implements Iterator<E>, Unmodifiable {

	@Override
	public final void remove() {
		throw new UnsupportedOperationException();
	}
}
