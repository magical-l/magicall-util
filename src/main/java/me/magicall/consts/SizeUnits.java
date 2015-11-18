package me.magicall.consts;

public enum SizeUnits {
    BYTE,
    KB(BYTE),
    MB(KB),
    GB(MB),
    TB(GB),
    PB(TB),;

    public static final int RADIX = 1 << 10;

    public final int size;

    SizeUnits() {
        size = 1;
    }

    SizeUnits(final SizeUnits lowLv) {
        size = RADIX * lowLv.size;
    }

    public int toBytes(final int num) {
        return size * num;
    }

    public int toOtherSize(final int num, final SizeUnits sizeUnits) {
        return size / sizeUnits.size * num;
    }
}
