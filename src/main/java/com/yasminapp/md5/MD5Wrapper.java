package com.yasminapp.md5;

import com.twmacinta.util.*;

public class MD5Wrapper {
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		
		String inString = new String("this is my string. there are many like it but this one is mine.");
		MD5 myHash = new MD5(inString);
		
		System.out.println(myHash.asHex());

	}

}
