package me.magicall.util;

import me.magicall.consts.UrlCons;

public class UrlUtil implements UrlCons {
	public static boolean isRootPath(final String url) {
		return url.startsWith(URL_SEPARATOR);
	}
}
