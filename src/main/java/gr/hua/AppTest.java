package gr.hua;

import gr.hua.Dictionary.Entry;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class AppTest {

	public static void main(String[] args) {
		if(args.length != 1) {
			System.err.println("Enter one argument only");
			System.exit(1);
		}
		
		OpenAddrHashMap<String, Integer> hashmap = new OpenAddrHashMap<>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
		    String line = br.readLine();
		    while (line != null) {
		    	String[] words = line.split("[ \\t\\x0B\\f\\r]+");
		    	for(String w: words) {
		    		Integer curFreq = hashmap.get(w);
		    		if (curFreq == null) { 
						curFreq = 1;
					} else { 
						curFreq++;
					}
		    		hashmap.put(w, curFreq);
		    	}
		        line = br.readLine();
		    }
		    
		    
		} catch (FileNotFoundException e) {
			System.err.println("File not found!");
		} catch (IOException e) {
			System.err.println("An error occured while reading the file!");
		}
		
		for(Entry<String, Integer> e: hashmap) {
			System.out.println(e.getKey() + ": " + e.getValue());
		}
		
	}

}
