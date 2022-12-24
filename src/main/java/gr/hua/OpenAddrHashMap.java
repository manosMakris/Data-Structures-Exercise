package gr.hua;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.NoSuchElementException;

import gr.hua.Dictionary.Entry;

public class OpenAddrHashMap<K, V> implements Dictionary<K, V>{
	
	private static final int INITIAL_CAPACITY = 16;
	private int size;
	private Entry<K, V> array[];
	private UniversalHashingFunction hashFunction;
	
	@SuppressWarnings("unchecked")
	public OpenAddrHashMap() {
		this.size = 0;
		array = (Entry<K, V>[]) Array.newInstance(EntryImpl.class, INITIAL_CAPACITY);
		hashFunction = new UniversalHashingFunction(log2(INITIAL_CAPACITY));
	}
	
	@SuppressWarnings("unchecked")
	private OpenAddrHashMap(int length) {
		this.size = 0;
		array = (Entry<K, V>[]) Array.newInstance(EntryImpl.class, length);
		hashFunction = new UniversalHashingFunction(log2(length));
	}
	
	@Override
	public void put(K key, V value) {
		rehashIfNeeded();
		insert(key, value);
	}

	@Override
	public V remove(K key) {
		if (!this.contains(key)) {
			throw new NoSuchElementException("The key "+key+" doesn't exist.");
		}
		rehashIfNeeded();
		
		int cur = hashFunction.hash(key.hashCode());
		V ret = null;
		
		while(!array[cur].getKey().equals(key)) {
			cur = (cur+1) % array.length;
		}
		// delete the element if found
		ret = array[cur].getValue();
		array[cur] = null;
		size--;
		
		//fix the position of null if needed
		int next = (cur+1) % array.length;
		while(array[next] != null) {
			int defIdx = hashFunction.hash(array[next].getKey().hashCode());
			if(defIdx != next) {
				Entry<K, V> temp = array[next];
				array[next] = null;
				size--;
				insert(temp.getKey(), temp.getValue());
				
//				if(next < cur) {
//					if(defIdx < cur) {
//						array[cur] = array[next];
//						array[next] = null;
//						cur = next;
//					}
//				}
//				else if((defIdx <= cur && next > defIdx) || (next < defIdx && defIdx > cur)) {
//					array[cur] = array[next];
//					array[next] = null;
//					cur = next;
//				}
			}

			next = (next+1) % array.length;
		}

		return ret;
	}

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

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public int size() {
		return size;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void clear() {
		this.size = 0;
		array = (Entry<K, V>[]) Array.newInstance(EntryImpl.class, INITIAL_CAPACITY);
		hashFunction = new UniversalHashingFunction(log2(INITIAL_CAPACITY));
	}

	@Override
	public Iterator<Entry<K, V>> iterator() {
		return new HashMapIterator();
	}
	
	private class HashMapIterator implements Iterator<Dictionary.Entry<K, V>> {
		
		private int cur;
		
		public HashMapIterator() {
			this.cur = 0;
		}
		
		@Override
		public boolean hasNext() {
			while (cur<array.length) {
				if (array[cur] == null) {
					cur++;
					continue;
				}
				return true;
			}
			return false;
		}

		@Override
		public Entry<K, V> next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			return array[cur++];
		}
		
	}
	
	private static class EntryImpl<K, V> implements Dictionary.Entry<K, V> {
		
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
		
		public void setValue(V value) {
			this.value = value;
		}
		
	}
	
	private void rehashIfNeeded() {
		double utilization = (double)this.size / array.length;
		int newLength;
		if (utilization >= 0.5) {
			// Double array capacity
			newLength = array.length * 2;
		} else if (utilization <= 0.25 && array.length >= 2*INITIAL_CAPACITY) {
			// Half array capacity
			newLength = array.length / 2;
		} else {
			return;
		}
		
		OpenAddrHashMap<K, V> newHashMap = new OpenAddrHashMap<>(newLength);
		for (Entry<K, V> e: this) {
			newHashMap.insert(e.getKey(), e.getValue());
		}
		
		this.array = newHashMap.array;
		this.size = newHashMap.size;
		this.hashFunction = newHashMap.hashFunction;
	}
	
	private void insert(K key, V value) {
		
		if (value == null) {
			throw new IllegalArgumentException("Value can not be null.");
		}
		
		Entry<K, V> newEntry = new EntryImpl<>(key, value);
		int hascode = key.hashCode();
		int idx = hashFunction.hash(hascode);
		while (array[idx] != null) {
			if (array[idx].getKey().equals(key)) {
				array[idx] = newEntry;
				return;
			}
			idx = (idx + 1) % array.length;
		}
		array[idx] = newEntry;
		size++;
	}
	
	private static int log2(int N) {
        int result = (int)(Math.log(N) / Math.log(2));
        return result;
    }
	
}
