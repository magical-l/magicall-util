/**
 * 
 */
package me.magicall.util.kit;

import me.magicall.lang.ObjectUtil;

public final class BoolKit extends PrimitiveKit<Boolean, boolean[]> {

	private static final String[] SHORT_NAMES = { "bool" };
	private static final String[] TRUE_STR = {//
	Boolean.TRUE.toString(),//true
			"1",//
	};
	//-----------------------------------------
	public static final BoolKit INSTANCE = new BoolKit();

	//-----------------------------------------
	private BoolKit() {
		super(Boolean.class, Boolean.FALSE, SHORT_NAMES, boolean.class, "Z");
	}

	//-----------------------------------------
	@Override
	public Boolean fromString(final String source) {
		for (final String s : TRUE_STR) {
			if (s.equalsIgnoreCase(source)) {
				return true;
			}
		}
		return false;//Boolean.parseBoolean(source);
	}

	@Override
	public boolean inGreaterRange(final Boolean source, final Boolean target) {
		return false;
	}

	@Override
	public boolean inGreaterEqualsRange(final Boolean source, final Boolean target) {
		return ObjectUtil.deepEquals(source, target);
	}

	//-------------------------------------------

	@Override
	protected boolean[] newPrimitiveArray0(final int size) {
		return new boolean[size];
	}

	@Override
	protected Boolean arrayElement(final boolean[] array, final int i) {
		return array[i];
	}

	@Override
	protected int arrayLen0(final boolean[] array) {
		return array.length;
	}

	@Override
	protected void setArrayElement(final boolean[] array, final int i, final Boolean value) {
		array[i] = value;
	}

	@Override
	protected void setArrayElementValue(final boolean[] array, final int i, final Boolean[] toArray,
			final int toArrayIndex) {
		toArray[toArrayIndex] = array[i];
	}

	//======================================================
	private static final long serialVersionUID = 5629622952508909299L;

	private Object readResolve() {
		return INSTANCE;
	}
}