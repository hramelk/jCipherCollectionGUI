package ccgui;

//Bkblukbwgbttgdeuaj
public class KeywordCipher {
	
	public static String encode(String textOpen, String key) {
		if (!isValidKey(key)) {
			return "";
		}
		String alphabetCipher = getAlphabetByKey(key);
		String encoded = "";
		
		for (int i = 0; i < textOpen.length(); i++) {
			char ct = textOpen.charAt(i);
			int caseIndex = getIndexByRange(ct);
			if (caseIndex == 0) {
				encoded += ct;
				continue;
			}
			int alphabetIndex = ("" + ct).toUpperCase().charAt(0) - 'A';
			char encodedChar = alphabetCipher.charAt(alphabetIndex);
			char encodedCharNoCase = (char) (encodedChar - getIndexByRange(encodedChar));
			char newCharValue = (char) (encodedCharNoCase + caseIndex);
			encoded += newCharValue;
		}
		return encoded;
	}
	
	public static String decode(String textEncypted, String key) {
		if (!isValidKey(key)) {
			return "";
		}
		String alphabetCipher = getAlphabetByKey(key);
		String decoded = "";
		
		for (int i = 0; i < textEncypted.length(); i++) {
			char ct = textEncypted.charAt(i);
			int caseIndex = getIndexByRange(ct);
			if (caseIndex == 0) {
				decoded += ct;
				continue;
			}
			// int alphabetIndex = ("" + ct).toUpperCase().charAt(0) - 'A';// no
			int alphabetIndex = alphabetCipher.indexOf(("" + ct).toUpperCase()
					.charAt(0));
			char decodedChar = (char) ('A' + alphabetIndex);
			char decodedCharNoCase = (char) (decodedChar - getIndexByRange(decodedChar));
			char newCharValue = (char) (decodedCharNoCase + caseIndex);
			decoded += newCharValue;
		}
		return decoded;
	}
	
	public static boolean isValidKey(String key) {
		if (key.length() == 0) {
			return false;
		}
		key = key.toUpperCase();
		for (int i = 0; i < key.length(); i++) {
			char charTemp = key.charAt(i);
			if (charTemp >= 'A' && charTemp <= 'Z') {
				
			} else {
				return false;
			}
		}
		
		return true;
	}
	
	private static String getAlphabetByKey(String key) {
		key = key.toUpperCase();
		String sanitizedKey = getSanitizedKey(key);
		String alphabet = sanitizedKey;
		
		for (int i = 0; i < 26; i++) {
			char ct = (char) ('A' + i);
			if (alphabet.indexOf(ct) == -1) {
				alphabet += ct;
			}
		}
		return alphabet;
	}
	
	private static String getSanitizedKey(String key) {
		String sanitizedKey = "";
		
		for (int i = 0; i < key.length(); i++) {
			char ct = key.charAt(i);
			if (sanitizedKey.indexOf(ct) == -1) {
				sanitizedKey += ct;
			}
		}
		return sanitizedKey;
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
