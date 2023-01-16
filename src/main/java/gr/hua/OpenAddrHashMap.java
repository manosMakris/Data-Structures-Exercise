package gr.hua;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class OpenAddrHashMap<K, V> implements Dictionary<K, V>{
	
	// this is default capacity whenever a new object is created. 
	private static final int INITIAL_CAPACITY = 16;
	// hold the amount of entries within the hashMap.
	private int size;
	// the array that holds the entries and is the internal state of the hashMap.
	private Entry<K, V> array[];
	// the hashFunction: (int:hashcode) -> (int:index)
	private UniversalHashingFunction hashFunction;
	

	
	/**
	 * constructor for the user
	 */
	@SuppressWarnings("unchecked")
	public OpenAddrHashMap() {
		this.size = 0;
		array = (Entry<K, V>[]) Array.newInstance(EntryImpl.class, INITIAL_CAPACITY);
		hashFunction = new UniversalHashingFunction(log2(INITIAL_CAPACITY));
	}
	
	// private constructor used only by the rehashIfNeeded() method.
	@SuppressWarnings("unchecked")
	private OpenAddrHashMap(int length) {
		this.size = 0;
		array = (Entry<K, V>[]) Array.newInstance(EntryImpl.class, length);
		hashFunction = new UniversalHashingFunction(log2(length));
	}
	
	/**
	 * <p>Stores a key-value pair in the hashMap</p>
	 * @params K, V
	 * @return void
	 */
	@Override
	public void put(K key, V value) {
		rehashIfNeeded();
		insert(key, value);
	}

	/**
	 * <p>Deletes a key-value pair from the hashMap if that exists</p>
	 * @params K
	 * @return V: the value of the deleted key-value pair or else null if that doesn't exist.
	 */
	@Override
	public V remove(K key) {
		if (!this.contains(key)) {
			throw new NoSuchElementException("The key "+key+" doesn't exist.");
		}
		rehashIfNeeded();
		
		// get the position of the given key using the hashFunction.
		int cur = hashFunction.hash(key.hashCode());
		
		// locate the actual location starting from cur
		while(!array[cur].getKey().equals(key)) {
			cur = (cur+1) % array.length;
		}
		// store the value and delete the element, if found.
		V ret = array[cur].getValue();
		array[cur] = null;
		size--;
		
		//fix the position of null if needed:
		
		// check the first element next to the deleted one
		int next = (cur+1) % array.length;
		
		while(array[next] != null) {
			
			int defIdx = hashFunction.hash(array[next].getKey().hashCode());
			
			if(defIdx != next) {
				
				Entry<K, V> temp = array[next];
                array[next] = null;
                size--;
                insert(temp.getKey(), temp.getValue());
				
//				if(defIdx < next && next > cur) {
//					if(defIdx <= cur) {
//						array[cur] = array[next];
//						array[next] = null;
//						cur = next;
//					}
//				}
//				else {
//					if(defIdx >= cur) {
//						array[cur] = array[next];
//						array[next] = null;
//						cur = next;
//					}
//				}
				
				
			}
			next = (next + 1) % array.length;
		}
		
		
		// return the value of the deleted element.
		return ret;
	}

	/**
	 * <p>Searches for a key-value pair in the hashMap and returns the value if it exists.</p>
	 * @params K
	 * @return V: the value of key-value pair or else null if that doesn't exist.
	 */
	@Override
	public V get(K key) {
		int hascode = key.hashCode();
		int idx = hashFunction.hash(hascode);
		
		while(array[idx] != null) {
			if (array[idx].getKey().equals(key)) {
				return array[idx].getValue();
			}
			idx = (idx + 1) % array.length;
		}
		
		return null;
	}
	
	/**
	 * <p>Searches for a key-value pair in the hashMap and returns true if it exists and false otherwise</p>
	 * @params K 
	 * @return boolean: true if the key is found, and false otherwise.
	 */
	@Override
	public boolean contains(K key) {
		int hascode = key.hashCode();
		int idx = hashFunction.hash(hascode);
		
		while(array[idx] != null) {
			if (array[idx].getKey().equals(key)) {
				return true;
			}
			idx = (idx + 1) % array.length;
		}
		
		return false;
	}

	/**
	 * <p>Checks if the hashMap is empty.</p>
	 * @params void 
	 * @return boolean: true if the hashMap is empty, and false otherwise.
	 */
	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * <p>Return the number of elements in the hashMap.</p>
	 * @params void 
	 * @return int: size.
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * <p>Resets the hashMap to the default size and removes all elements.</p>
	 * @params void 
	 * @return void
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void clear() {
		this.size = 0;
		array = (Entry<K, V>[]) Array.newInstance(EntryImpl.class, INITIAL_CAPACITY);
		hashFunction = new UniversalHashingFunction(log2(INITIAL_CAPACITY));
	}

	/**
	 * <p>Creates an iterator over the key-value pairs of the hashMap.</p>
	 * @params void 
	 * @return Iterator&ltEntry&ltK, V&gt&gt: A new iterator object for the hashMap.
	 */
	@Override
	public Iterator<Entry<K, V>> iterator() {
		return new HashMapIterator();
	}
	
	private class HashMapIterator implements Iterator<Dictionary.Entry<K, V>> {
		
		// current element position
		private int cur;
		
		// at the beginning it's the array[0]
		public HashMapIterator() {
			this.cur = 0;
		}
		
		@Override
		public boolean hasNext() {
			// if the end of the array has not been reached, continue to look for elements.
			while (cur<array.length) {
				if (array[cur] == null) {
					// if cur element is null then check the next one
					cur++;
					continue;
				}
				// return true if non-null is found
				return true;
			}
			// return false if the end is reached
			return false;
		}

		@Override
		public Entry<K, V> next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			// if there exist an element, return it and point to the next possible element.
			return array[cur++];
		}
		
	}
	
	private static class EntryImpl<K, V> implements Dictionary.Entry<K, V> {
		// the default implementation of a key-value pair.
		
		private K key;
		private V value;
		
		public EntryImpl(K key, V value) {
			this.key = key;
			this.value = value;
		}
		
		@Override
		public K getKey() {
			return this.key;
		}

		@Override
		public V getValue() {
			return this.value;
		}
		
	}
	
	private void rehashIfNeeded() {
		// percentage of utilization of the hashMap.
		double utilization = (double)this.size / array.length;
		int newLength;
		// if it's over or equal to 50%:
		if (utilization >= 0.5) {
			// Double array capacity
			newLength = array.length * 2;
		//if it's less or equal to 25% and the length of the array is big enough:
		} else if (utilization <= 0.25 && array.length >= 2*INITIAL_CAPACITY) {
			// Half array capacity
			newLength = array.length / 2;
		// if it's between 25% and 50% then it's fine.
		} else {
			return;
		}
		
		// create a new hashMap with the newLength.
		OpenAddrHashMap<K, V> newHashMap = new OpenAddrHashMap<>(newLength);
		// copy every element from this hashMap to the new one
		for (Entry<K, V> e: this) {
			newHashMap.insert(e.getKey(), e.getValue());
		}
		
		// hold the new hashMap to the array reference variable
		this.array = newHashMap.array;
		// copy the size but if everything is correct the size must be the same in both hashMaps
		this.size = newHashMap.size;
		// hold the new hashFunction to the hashFunction reference variable
		this.hashFunction = newHashMap.hashFunction;
	}
	
	private void insert(K key, V value) {
		
		// null values are not accepted.
		if (value == null) {
			throw new IllegalArgumentException("Value can not be null.");
		}
		
		// create the new Entry element.
		Entry<K, V> newEntry = new EntryImpl<>(key, value);
		// get the hashcode of the key.
		int hascode = key.hashCode();
		// map it to the appropriate index.
		int idx = hashFunction.hash(hascode);
		// while we don't find an empty spot, keep searching left with linear probing.
		while (array[idx] != null) {
			// if the key already in hashMap, just overwrite with the new value without incrementing the size. 
			if (array[idx].getKey().equals(key)) {
				array[idx] = newEntry;
				return;
			}
			// check the next one if the keys are different.
			idx = (idx + 1) % array.length;
		}
		// insert the new entry in the first empty spot, if it was not found.
		array[idx] = newEntry;
		// increment the size.
		size++;
	}
	
	// helps map the hashMap desired length, with the appropriate length needed for the output array for the matrix method hashing.
	private static int log2(int N) {
        int result = (int)(Math.log(N) / Math.log(2));
        return result;
    }
	
}
