package ccgui;

public class TapCode {
	
	private static String	customAlphabet	= "ABCDEFGHIJLMNOPQRSTUVWXYZ";
	private static String	separator		= " ";
	
	public static String encode(String textOpen) {
		textOpen = textOpen.replaceAll("K", "C").replaceAll("k", "c");
		String encoded = "";
		for (int i = 0; i < textOpen.length(); i++) {
			char charTemp = textOpen.charAt(i);
			int caseIndex = getIndexByRange(charTemp);
			if (caseIndex == 0) {
				encoded += charTemp + separator;
				continue;
			}
			int charIndexInAlphabet = customAlphabet.indexOf(charTemp);
			int rowNumber = charIndexInAlphabet / 5 + 1;
			int columnNumber = charIndexInAlphabet % 5 + 1;
			encoded += (rowNumber + "," + columnNumber + separator);
		}
		return encoded;
	}
	
	public static String decode(String textEncoded) {
		String decoded = "";
		String[] splitted = textEncoded.split(" ");
		
		for (int i = 0; i < splitted.length; i++) {
			String[] strTemp = splitted[i].split(",");
			if (strTemp.length == 0) {
				continue;
			}
			if (strTemp.length == 1) {
				decoded += strTemp[0];
				continue;
			}
			int rowNumber = Integer.parseInt(strTemp[0]);
			int columnNumber = Integer.parseInt(strTemp[1]);
			int indexInAlphabet = (rowNumber - 1) * 5 + columnNumber - 1;
			decoded += customAlphabet.charAt(indexInAlphabet);
		}
		return decoded;
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
