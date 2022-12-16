package gr.hua;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import gr.hua.Dictionary.Entry;

class OpenAddrHashMapTest {
	
	public static final int SIZE = 16;
	
	@Test
	void test() {
		OpenAddrHashMap<Integer, Integer> hashMap = new OpenAddrHashMap<>();
		
		for (int i=0;i<SIZE;i++) {
			hashMap.put(i, i+1);
		}
	
		assertEquals(hashMap.size(), SIZE);
		for (int i=0;i<SIZE;i++) {
			assertEquals(hashMap.get(i), i+1);
			hashMap.remove(i);
		}
		assertEquals(hashMap.size(), 0);
	}

}
