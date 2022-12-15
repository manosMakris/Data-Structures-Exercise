package gr.hua;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UniversalHashingFunctionTest {

	@Test
	void test() {
		UniversalHashingFunction hashingFunction = new UniversalHashingFunction(4, UniversalHashingFunction.DEFAULT_SEED);
		
		assertEquals(hashingFunction.hash(55), 7);
		assertEquals(hashingFunction.hash(400), hashingFunction.hash(400));
	}

}
