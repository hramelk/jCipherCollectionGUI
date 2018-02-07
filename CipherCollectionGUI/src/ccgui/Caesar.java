package ccgui;

public class Caesar {
	
	public static String encode(String openText, int key) {
		String encoded = "";
		for (int i = 0; i < openText.length(); i++) {
			char cp = openText.charAt(i);
			int index = getIndexByRange(cp);
			if (index == 0) {
				encoded += cp;
				continue;
			}
			int num = cp - index;
			num = (num + key) % 26;
			encoded += (char) (num + index);
		}
		return encoded;
	}
	
	public static String decode(String encodedText, int key) {
		String decodedText = "";
		for (int i = 0; i < encodedText.length(); i++) {
			char cp = encodedText.charAt(i);
			int index = getIndexByRange(cp);
			if (index == 0) {
				decodedText += cp;
				continue;
			}
			int num = cp - index;
			num = (26 + num - key) % 26;
			decodedText += (char) (num + index);
		}
		return decodedText;
	}
	
	public static String encode(String openText, String key) {
		int keyParsed = 0;
		try {
			keyParsed = Integer.parseInt(key);
		} catch (Exception e) {
			return "";
		}
		return encode(openText, keyParsed);
	}
	
	public static String decode(String encodedText, String key) {
		int keyParsed = 0;
		try {
			keyParsed = Integer.parseInt(key);
		} catch (Exception e) {
			return "";
		}
		return encode(encodedText, keyParsed);
	}
	
	private static int getIndexByRange(char cp) {
		if (cp >= 'a' && cp <= 'z') {
			return 97;
		} else if (cp >= 'A' && cp <= 'Z') {
			return 65;
		} else {
			return 0;
		}
	}
}
