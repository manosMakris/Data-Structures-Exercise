package gr.hua;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

import org.junit.jupiter.api.Test;

import gr.hua.Dictionary.Entry;

class OpenAddrHashMapTest {
	
	public static final int SIZE = 8;
	
	@Test
void test() {
		
		for(int k=0; k<1000; k++) {
			
			OpenAddrHashMap<Integer, Integer> hashMap = new OpenAddrHashMap<Integer, Integer>();
			int r = new Random().nextInt();
			
			int SIZE = Math.abs(new Random().nextInt(1, 121));
			
			assertEquals(hashMap.size(), 0);
			assertTrue(hashMap.isEmpty());
			
			for (int i=1;i<=SIZE;i++) {
				hashMap.put(r+i, i);
				assertTrue(hashMap.contains(r+i));
			}
			assertEquals(hashMap.size(), SIZE);
			assertFalse(hashMap.isEmpty());
			
			for (int i=1;i<=SIZE;i++) {
				assertTrue(hashMap.contains(r+i));
				assertEquals(hashMap.get(r+i), i);
			}
			
			for (int i=1;i<=SIZE;i++) {
				assertTrue(hashMap.contains(r+i));
				hashMap.remove(r+i);
			}
			assertTrue(hashMap.isEmpty());
			assertEquals(hashMap.size(), 0);
			
		}
		
	}

}
