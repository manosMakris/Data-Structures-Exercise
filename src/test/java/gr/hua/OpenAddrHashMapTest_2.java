package gr.hua;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Random;

import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

import gr.hua.Dictionary.Entry;

class OpenAddrHashMapTest_2 {

	@Test
	void test() {
		
		for(int k=0; k<100; k++) {
			OpenAddrHashMap<String, String> hashMap = new OpenAddrHashMap<>();
			
			int SIZE = new Random().nextInt(1, 121);
			
			Faker randomNames = new Faker();
	
			ArrayList<String> fnames = new ArrayList<>();
			ArrayList<String> lnames = new ArrayList<>();
			
			for(int i=0; i<SIZE; i++) {
				String temp;
				do {
					temp = randomNames.name().firstName();
				}while(fnames.contains(temp));
				fnames.add(temp);
				lnames.add(randomNames.name().lastName());
			}
			
			assertTrue(hashMap.isEmpty());
			for(int i=0; i<SIZE; i++) {
				hashMap.put(fnames.get(i), lnames.get(i));
			}
			assertFalse(hashMap.isEmpty());
			assertEquals(hashMap.size(), SIZE);
			
			int j=0;
			for(Entry<String, String> e: hashMap) {
				assertTrue(hashMap.contains(e.getKey()));
				assertEquals(hashMap.get(e.getKey()), e.getValue());
				assertEquals(hashMap.get(fnames.get(j)), lnames.get(j));
				j++;
				//System.out.println(e.getKey() + " " + e.getValue());
			}
			
			hashMap.clear();
			assertTrue(hashMap.isEmpty());
			assertEquals(hashMap.size(), 0);
			fnames.clear();
			lnames.clear();
			
			for(int i=0; i<SIZE; i++) {
				String temp;
				do {
					temp = randomNames.name().firstName();
				}while(fnames.contains(temp));
				fnames.add(temp);
				lnames.add(randomNames.name().lastName());
			}
			
			assertTrue(hashMap.isEmpty());
			for(int i=0; i<SIZE; i++) {
				hashMap.put(fnames.get(i), lnames.get(i));
			}
			assertFalse(hashMap.isEmpty());
			assertEquals(hashMap.size(), SIZE);
			
			j = 0;
			for(Entry<String, String> e: hashMap) {
				assertTrue(hashMap.contains(e.getKey()));
				assertEquals(hashMap.get(e.getKey()), e.getValue());
				assertEquals(hashMap.get(fnames.get(j)), lnames.get(j));
				j++;
				//System.out.println(e.getKey() + " " + e.getValue());
			}
			
			for(int i=0; i<SIZE; i++) {
				assertTrue(hashMap.contains(fnames.get(i)));
				hashMap.remove(fnames.get(i));
				assertEquals(hashMap.size(), SIZE-i-1);
			}
			assertTrue(hashMap.isEmpty());
		}
	
	} 
	
}
