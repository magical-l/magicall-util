package me.magicall.coll.transformed;

import me.magicall.coll.ElementTransformer;

import java.util.Set;


public class TransformedSet<F, T> extends TransformedCollection<F, T> implements Set<T> {

	public TransformedSet(final Set<? extends F> set, final ElementTransformer<? super F,? extends  T> transformer) {
		super(set, transformer);
	}

}
