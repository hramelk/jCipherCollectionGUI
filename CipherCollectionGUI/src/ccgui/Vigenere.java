package ccgui;

public class Vigenere {
	public static String encode(String openText, String key) {
		String encoded = "";
		int keysize = key.length();
		key = deSymbolizeKey(key);
		key = key.toUpperCase();
		for (int i = 0; i < openText.length(); i++) {
			char cp = openText.charAt(i);
			int index = getIndexByRange(cp);
			if (index == 0) {
				encoded += cp;
				continue;
			}
			int num = cp - index;
			int keyNum = key.charAt(i % keysize) - 'A';
			num = (num + keyNum) % 26;
			encoded += (char) (num + index);
		}
		return encoded;
	}
	
	public static String decode(String encodedText, String key) {
		String decoded = "";
		int keysize = key.length();
		key = deSymbolizeKey(key);
		key = key.toUpperCase();
		for (int i = 0; i < encodedText.length(); i++) {
			char cp = encodedText.charAt(i);
			int index = getIndexByRange(cp);
			if (index == 0) {
				decoded += cp;
				continue;
			}
			int num = cp - index;
			int keyNum = key.charAt(i % keysize) - 'A';
			num = (26 + num - keyNum) % 26;
			decoded += (char) (num + index);
		}
		return decoded;
	}
	
	private static int getIndexByRange(char cp) {
		if (cp >= 'a' && cp <= 'z') {
			return 'a';
		} else if (cp >= 'A' && cp <= 'Z') {
			return 'A';
		} else {
			return 0;
		}
	}
	
	private static String deSymbolizeKey(String key) {
		String deSymbolizedKey = "";
		for (int i = 0; i < key.length(); i++) {
			char tempChar = key.charAt(i);
			if ((tempChar >= 'A' && tempChar <= 'Z')
					|| (tempChar >= 'a' && tempChar <= 'z')) {
				deSymbolizedKey += tempChar;
			} else {
				deSymbolizedKey += 'A';
			}
		}
		return deSymbolizedKey;
	}
	
}// end of class
