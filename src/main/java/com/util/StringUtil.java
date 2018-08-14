package com.util;

import java.math.BigInteger;

public class StringUtil {
	public static boolean isBlank(String str) {
		return str == null || "".equals(str);
	}
	
	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}
	
	public static String byteArrayToString(byte[] buff) {
		return new BigInteger(1, buff).toString();
	}
}