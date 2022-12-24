package gr.hua;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

import org.junit.jupiter.api.Test;

class UniversalHashingFunctionTest {

	@Test
	void test() {
		// Creating an instance of our hashing function for this test.
		UniversalHashingFunction hashingFunctionD = new UniversalHashingFunction(4, UniversalHashingFunction.DEFAULT_SEED);
		// Check if hash(55) == 7 which is should be.
		assertEquals(hashingFunctionD.hash(55), 7);
		
		// Check if calling our hash() method twice,
		// given the same sequence of zeros and ones,
		// with the same argument will return the same index.
		
		UniversalHashingFunction hashingFunction = new UniversalHashingFunction(4);
		
		assertEquals(hashingFunction.hash(400), hashingFunction.hash(400));
		
		int[] rands = new int[100];
		int[] idx = new int[100];
		
		for(int i=0; i<100; i++) {
			rands[i] = new Random().nextInt();
			idx[i] = hashingFunction.hash(rands[i]);
		}
		for(int i=0; i<100; i++) {
			assertEquals(hashingFunction.hash(rands[i]), hashingFunction.hash(rands[i]));
			assertEquals(hashingFunction.hash(rands[i]), idx[i]);
		}
	}

}
