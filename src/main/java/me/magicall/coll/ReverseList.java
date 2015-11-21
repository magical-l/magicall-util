package me.magicall.coll;

import me.magicall.coll.unmodifiable.UnmodifiableListTemplate;
import me.magicall.mark.Sorted;
import me.magicall.mark.Unmodifiable;
import me.magicall.mark.Wrapper;

import java.util.List;


public class ReverseList<E> extends UnmodifiableListTemplate<E>//
		implements List<E>, Unmodifiable, Wrapper<List<E>>, Sorted {

	private final List<E> source;

	public ReverseList(final List<E> source) {
		super();
		this.source = source;
	}

	@Override
	protected E get0(final int index) {
		return source.get(size() - index - 1);
	}

	@Override
	public int size() {
		return source.size();
	}

	public List<E> getSource() {
		return source;
	}

	@Override
	public List<E> unwrap() {
		return source;
	}
}
