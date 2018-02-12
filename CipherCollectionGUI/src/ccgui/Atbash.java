package ccgui;

public class Atbash {
	
	public static String encode(String openText) {
		return processCipher(openText);
	}
	
	public static String decode(String encodedText) {
		return processCipher(encodedText);
	}
	
	private static String processCipher(String text) {
		String processed = "";
		for (int i = 0; i < text.length(); i++) {
			char charTemp = text.charAt(i);
			int caseIndex = getIndexByRange(charTemp);
			if (caseIndex == 0) {
				processed += charTemp;
				continue;
			}
			int charTempAsNumber = charTemp - caseIndex;
			int processedCharTemp = reverseNumber(charTempAsNumber, 0, 25);
			processed += (char) (processedCharTemp + caseIndex);
		}
		return processed;
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
	
	private static int reverseNumber(int num, int min, int max) {
		return (max + min) - num;
	}
	
}
