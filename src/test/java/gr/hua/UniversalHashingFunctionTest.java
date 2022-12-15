package gr.hua;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UniversalHashingFunctionTest {

	@Test
	void test() {
		// Creating an instance of our hashing function for this test.
		UniversalHashingFunction hashingFunction = new UniversalHashingFunction(4, UniversalHashingFunction.DEFAULT_SEED);
		
		// Check if hash(55) == 7 which is should be.
		assertEquals(hashingFunction.hash(55), 7);
		
		// Check if calling our hash() method twice,
		// given the same sequence of zeros and ones,
		// with the same argument will return the same index.
		assertEquals(hashingFunction.hash(400), hashingFunction.hash(400));
	}

}
