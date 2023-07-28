package net.itsrelizc.utils;

import java.nio.charset.Charset;
import java.util.Random;

import org.apache.commons.lang.RandomStringUtils;

public class RandomString {
	public static String randomString(Integer length) {
		return RandomStringUtils.random(length, true, true);
	}
	
	public static String randomClassic16String() {
		return randomString(16);
	}
}
