package com.ssafy.util;

public class KMPService {
	public static int[] makeFailFunction(String pattern) {
		int[] pi = new int[pattern.length()];
		pi[0] = 0;
		
		int i = 1, j = 0;
		int p = pattern.length();
		while(i < p) {
			if(pattern.charAt(i) == pattern.charAt(j)) {
				pi[i] = j + 1;
				++i;
				++j;
			}else if(j == 0) {
				++i;
			}else {
				j = pi[j - 1];
			}
		}
		
		return pi;
	}

	public static int calcOccurence(String str, String pattern, int[] pi) {
		int cnt = 0;
		
		int i = 0, j = 0;
		
		int s = str.length();
		int p = pattern.length();
		
		if(s < p)
			return 0;
		
		while(i < s) {
			if(str.charAt(i) == pattern.charAt(j)) {
				++i;
				++j;
			}else if(j == 0) {
				++i;
			}else {
				j = pi[j - 1];
			}
			
			if(j == p) {
				++cnt;
				j = pi[j - 1];
			}
		}
		
		return cnt;
	}
}
