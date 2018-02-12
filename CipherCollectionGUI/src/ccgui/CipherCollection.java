package ccgui;

public class CipherCollection {
	
	public static void main(String[] args) {
		new GUI();
		System.out.println(Atbash.encode("blabla"));
		System.out.println(Atbash.encode("yozyoz"));
		
		System.out.println(Atbash.encode("BlaBla"));
		System.out.println(Atbash.encode("YozYoz"));
	}
	
}
