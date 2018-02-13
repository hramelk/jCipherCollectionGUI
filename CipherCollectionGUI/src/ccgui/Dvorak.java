package ccgui;

/*
 * Here is the translation from Qwerty to Dvorak (U.S. Simplified, Standard).
 * Ordering is based on a US 104 Key keyboard from left to right, top row to
 * bottom row, then shifted keys.
 * 
 * Qwerty: `1234567890-=qwertyuiop[]\asdfghjkl;'zxcvbnm,./~!@#$%^&*()_+QWERTYUIOP{}|ASDFGHJKL:"ZXCVBNM<>?
 * Dvorak: `1234567890[]',.pyfgcrl/=\aoeuidhtns-;qjkxbmwvz~!@#$%^&*(){}"<>PYFGCRL?+|AOEUIDHTNS_:QJKXBMWVZ
 * 
 * Here are two simplified tables, ordered nearly-alphabetically and so the top
 * one can be used in a regular expression pattern class (between brackets []).
 * 
 * Qwerty: ][abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ;',./_+{}:"<>?=-
 * Dvorak: =/axje.uidchtnmbrl'poygk,qf;AXJE>UIDCHTNMBRL"POYGK<QF:s-wvz{}?+S_WVZ][
 *
 * and
 *
 * Dvorak: ][abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ;',./_+{}:"<>?=-
 * Qwerty: =-anihdyujgcvpmlsrxo;kf.,bt/ANIHDYUJGCVPMLSRXO:KF><BT?zqwe["}_+ZQWE{]'
 */

public class Dvorak {
	
	private static String	qwertyKeyboard	= "`1234567890-=qwertyuiop[]\\asdfghjkl;'zxcvbnm,./~!@#$%^&*()_+QWERTYUIOP{}|ASDFGHJKL:\"ZXCVBNM<>?";
	private static String	dvorakKeyboard	= "`1234567890[]',.pyfgcrl/=\\aoeuidhtns-;qjkxbmwvz~!@#$%^&*(){}\"<>PYFGCRL?+|AOEUIDHTNS_:QJKXBMWVZ";
	
	public static String encode(String textOpen) {
		String encoded = "";
		for (int i = 0; i < textOpen.length(); i++) {
			char charTemp = textOpen.charAt(i);
			int charIndex = qwertyKeyboard.indexOf(charTemp);
			if (charIndex == -1) {
				encoded += charTemp;
				continue;
			}
			encoded += dvorakKeyboard.charAt(charIndex);
		}
		return encoded;
	}
	
	public static String decode(String textEncoded) {
		String decoded = "";
		for (int i = 0; i < textEncoded.length(); i++) {
			char charTemp = textEncoded.charAt(i);
			int charIndex = dvorakKeyboard.indexOf(charTemp);
			if (charIndex == -1) {
				decoded += charTemp;
				continue;
			}
			decoded += qwertyKeyboard.charAt(charIndex);
		}
		return decoded;
	}
	
}
