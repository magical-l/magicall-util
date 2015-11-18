package me.magicall.algorithm;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {

    public static String md5(final String plainText) {
        try {
            final MessageDigest md = MessageDigest.getInstance("MD5");
            final byte[] b = md.digest(plainText.getBytes());
            return toString(b);
        } catch (final NoSuchAlgorithmException e) {
            //impossible
            throw new IllegalStateException("impossible", e);
        }
    }

    private static String toString(final byte[] b) {
        final StringBuilder sb = new StringBuilder();
        for (final byte element : b) {
            int i = element;
            if (i < 0) {
                i += 256;
            }
            if (i < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(i));
        }
        return sb.toString();
    }
}
