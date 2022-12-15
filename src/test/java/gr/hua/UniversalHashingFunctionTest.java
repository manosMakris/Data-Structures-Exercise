package gr.hua;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UniversalHashingFunctionTest {

	@Test
	void test() {
		UniversalHashingFunction hashingFunction = new UniversalHashingFunction(4, 15);
		
		assertEquals(hashingFunction.hash(55), 12);
		assertEquals(hashingFunction.hash(400), hashingFunction.hash(400));
	}

}
