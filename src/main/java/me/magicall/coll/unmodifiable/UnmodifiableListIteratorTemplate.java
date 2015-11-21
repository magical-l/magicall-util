package me.magicall.coll.unmodifiable;

import me.magicall.mark.Unmodifiable;

import java.util.Iterator;
import java.util.ListIterator;


public abstract class UnmodifiableListIteratorTemplate<E> extends UnmodifiableIteratorTemplate<E>//
		implements ListIterator<E>, Iterator<E>, Unmodifiable {

	public UnmodifiableListIteratorTemplate() {
		super();
	}

	//-------------------------------------------------
	@Override
	public final void set(final E e) {
		throw new UnsupportedOperationException();
	}

	@Override
	public final void add(final E e) {
		throw new UnsupportedOperationException();
	}
}
