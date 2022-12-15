package gr.hua;

import java.util.Random;

public class UniversalHashingFunction {
	
	public static final int DEFAULT_INPUT_BITS = 32;
	public static final int DEFAULT_SEED = 17;
	public static final int EXCLUSIVE_UPPER_BOUND = 2; 
	private int arrayFunction[][];
	private Random random;
	private int b, u;
	
	public UniversalHashingFunction(int b) {
		this.u = DEFAULT_INPUT_BITS;
		this.b = b;
		this.arrayFunction = new int[b][DEFAULT_INPUT_BITS];
		this.random = new Random(DEFAULT_SEED);
		
		for ( int i = 0 ; i < b ; i++ ) {
			for ( int j = 0 ; j < u ; j++ ) {
				arrayFunction[i][j] = random.nextInt(EXCLUSIVE_UPPER_BOUND);
			}
		}
		
	}
	
	public UniversalHashingFunction(int b, int seed) {
		this.u = DEFAULT_INPUT_BITS;
		this.b = b;
		this.arrayFunction = new int[b][DEFAULT_INPUT_BITS];
		this.random = new Random(seed);
		
		for ( int i = 0 ; i < b ; i++ ) {
			for ( int j = 0 ; j < u ; j++ ) {
				arrayFunction[i][j] = random.nextInt(EXCLUSIVE_UPPER_BOUND);
			}
		}
		
	}
	
	public int hash(int hashcode) {
		int input_bits[] = new int[u];
		int result[] = new int[b];
		int mask, sum, ret=0;
		
		for( int i = u - 1 ; i >= 0 ; i-- ) {
			mask = 1 << i;
			input_bits[i] = (hashcode & mask) == 0 ? 0 : 1;
		}
		
		
		for( int i = 0 ; i < b ; i++ ) {
			sum = 0;
			for( int j = 0; j < u; j++ ) {
				sum = (sum + (arrayFunction[i][j] * input_bits[31-j])) % 2;
			}
			result[arrayFunction.length-1-i] = sum;
		}
		
		
		for( int i = 0 ; i < b ; i++ ) {
			ret = ret + (int)Math.pow(2, i) * result[i];
		}
		
//		System.out.print("Input bits are: ");
//		for(int i=31;i>=0;i--) {
//			System.out.print(input_bits[i]+" ");
//		}
//		System.out.println("\nInput_bits[0] = "+input_bits[0]);
//		System.out.print("Result bits are: ");
//		for(int i=arrayFunction.length-1;i>=0;i--) {
//			System.out.print(result[i]+" ");
//		}
//		System.out.println("\nResult[2] = "+result[2]);
		return ret;
	}
	
	public void printState() {
		for (int i=0;i<arrayFunction.length;i++) {
			for (int j=0;j<32;j++) {
				System.out.print(arrayFunction[i][j]+" ");
			}
			System.out.println();
		}
	}
	
}
