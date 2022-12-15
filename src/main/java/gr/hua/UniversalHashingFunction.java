package gr.hua;

import java.util.Random;

/**
 * 
 * @author teo&manos
 * <p>This class is used for hashing.</p>
 * <p>It has 2 constructors. One for debugging purposes 
 * and one for the actuall implementation of the hashmap.
 * It also has a hash() method which is the main part of this class.</p>
 */
public class UniversalHashingFunction {
	
	// We created this constant 
	// so that the user can use the same 
	// sequence of ones and zeros for
	// making the debugging easier. He can simply
	// pass this constant when constructing an object.
	public static final int DEFAULT_SEED = 17;
	
	// This private constant is being used when creating
	// the arrayFunction. It is the width of the array.
	private static final int DEFAULT_INPUT_BITS = 32;
	
	// This private constant is being used when filling
	// the arrayFunction with ones and zeros. It is passed
	// to the random.nextInt() method in order to constrain
	// it's output to only 0 and 1.
	private static final int EXCLUSIVE_UPPER_BOUND = 2; 
	
	// This arrayFunction is resposible for storing
	// the array used for the hashing in the hash() method.
	private int arrayFunction[][];
	
	private Random random;
	
	// We created these private fields so that the code
	// becomes more readable. Explanation:
	// b = arrayFunction.length, u = arrayFunction[i].lenght
	private int b, u;
	
	/** This constructor is filling the the arrayFunction with a different
	* sequence every time we create a object.
	*/
	public UniversalHashingFunction(int b) {
		this.u = DEFAULT_INPUT_BITS;
		this.b = b;
		
		// Initializing the arrayFunction
		this.arrayFunction = new int[b][DEFAULT_INPUT_BITS];
		
		// Initializing a random instance
		this.random = new Random();
		
		// Filling the arrayFunction with ones and zeros.
		for ( int i = 0 ; i < b ; i++ ) {
			for ( int j = 0 ; j < u ; j++ ) {
				arrayFunction[i][j] = random.nextInt(EXCLUSIVE_UPPER_BOUND);
			}
		}
		
	}
	
	/** This constructor is filling the the arrayFunction with the same
	* sequence every time we create a object. It is for debugging purposes.
	*/
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
	
	/** This function accepts a hashcode and returns the appropriate
	* index for the object to be placed.
	*/
	public int hash(int hashcode) {
		
		// Creating the input array
		int input_bits[] = new int[u];
		
		// Creating the array which will hold the result
		int result[] = new int[b];
		
		// Creating some helper variables
		int mask, sum, ret=0;
		
		// Filling the input_bits array based on the
		// the given hashcode.
		for( int i = u - 1 ; i >= 0 ; i-- ) {
			
			// Creating the appropriate mask
			mask = 1 << i;
			
			// hashcode & mask results to a 0 or something none zero.
			// If the bit at the current position is 0 then the
			// we store zero else we store one.
			input_bits[i] = (hashcode & mask) == 0 ? 0 : 1;
		}
		
		// We multiply the arrayFunction matrix with the input_bits array
		for( int i = 0 ; i < b ; i++ ) {
			// Reset sum variable
			sum = 0;
			for( int j = 0; j < u; j++ ) {
				// We multiply each row of arrayFunction matrix
				// with input_bits array using addition with modulo 2
				// because the output must be only 0 or 1.
				sum = (sum + (arrayFunction[i][j] * input_bits[31-j])) % 2;
			}
			
			// We store the contents of sum variable
			// to it's appropriate position based on i varibale
			result[arrayFunction.length-1-i] = sum;
		}
		
		// We calculate the output based on the result array
		for( int i = 0 ; i < b ; i++ ) {
			
			// Basically we convert the result array of ones and zeros
			// in a decimal number from a binary number.
			ret = ret + (int)Math.pow(2, i) * result[i];
		}
		
		// Return the output to the caller
		return ret;
	}
	
}
