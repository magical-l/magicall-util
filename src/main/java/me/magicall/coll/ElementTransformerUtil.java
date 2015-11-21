package me.magicall.coll;

import me.magicall.mark.HasIdGetter;
import me.magicall.mark.HasIdGetter.HasIntIdGetter;
import me.magicall.mark.HasIdGetter.HasLongIdGetter;
import me.magicall.mark.Renamable;

public class ElementTransformerUtil {

	public static final ElementTransformer<Object, Object> SELF = (index, element) -> element;

	@SuppressWarnings("unchecked")
	public static <F, T> ElementTransformer<F, T> self() {
		return (ElementTransformer<F, T>) SELF;
	}

	public static final ElementTransformer<Object, String> TO_STRING = new ElementTransformer<Object, String>() {

		@Override
		public String transform(final int index, final Object element) {
			return element.toString();
		}

		private Object readResolve() {
			return TO_STRING;
		}
	};
	/**
	 * 一个ElementTransformer，将元素转换为下标
	 *
	 * @author MaGiCalL
	 */
	public static final ElementTransformer<Object, Integer> TO_INDEX = new ElementTransformer<Object, Integer>() {

		private Object readResolve() {
			return TO_INDEX;
		}

		@Override
		public Integer transform(final int index, final Object element) {
			return index;
		}
	};

	public static final ElementTransformer<HasIntIdGetter, Integer> TO_INT_ID = new ElementTransformer<HasIntIdGetter, Integer>() {

		@Override
		public Integer transform(final int index, final HasIntIdGetter element) {
			return element.getId();
		}

		private Object readResolve() {
			return TO_INT_ID;
		}
	};

	public static final ElementTransformer<Renamable, String> TO_NAME = new ElementTransformer<Renamable, String>() {

		@Override
		public String transform(final int index, final Renamable element) {
			return element.getName();
		}

		private Object readResolve() {
			return TO_NAME;
		}
	};

	public static final ElementTransformer<HasLongIdGetter, Long> TO_LONG_ID = new ElementTransformer<HasLongIdGetter, Long>() {

		@Override
		public Long transform(final int index, final HasLongIdGetter element) {
			return element.getId();
		}

		private Object readResolve() {
			return TO_LONG_ID;
		}
	};
	public static final ElementTransformer<HasIdGetter<?>, Object> TO_OBJECT_ID = new ElementTransformer<HasIdGetter<?>, Object>() {

		@Override
		public Object transform(final int index, final HasIdGetter<?> element) {
			return element.getId();
		}

		private Object readResolve() {
			return TO_OBJECT_ID;
		}
	};

	public static final ElementTransformer<Object, Class<?>> TO_CLASS = new ElementTransformer<Object, Class<?>>() {

		@Override
		public Class<?> transform(final int index, final Object element) {
			return element.getClass();
		}

		private Object readResolve() {
			return TO_CLASS;
		}
	};

	public static final ElementTransformer<Object, Integer> TO_HASH_CODE = new ElementTransformer<Object, Integer>() {

		@Override
		public Integer transform(final int index, final Object element) {
			return element.hashCode();
		}

		private Object readResolve() {
			return TO_HASH_CODE;
		}
	};
	public static final ElementTransformer<Enum<?>, String> ENUM_TO_NAME = (index, element) -> element.name();
}
