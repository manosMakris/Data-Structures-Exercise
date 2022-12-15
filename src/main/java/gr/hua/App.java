package gr.hua;

public class App {

	public static void main(String[] args) {
		UniversalHashingFunction test = new UniversalHashingFunction(4, UniversalHashingFunction.DEFAULT_SEED);
		int idx = test.hash(55);
		System.out.println("hash(55) = "+idx);
		
	}

}
