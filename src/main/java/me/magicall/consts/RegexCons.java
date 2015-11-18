package me.magicall.consts;

import java.util.regex.Pattern;

public interface RegexCons {

	String COMMON_SEPARATOR = "(,|\\s|/|，|、)+";

	Pattern ZIP_CODE = Pattern.compile("\\d{6}");
}
