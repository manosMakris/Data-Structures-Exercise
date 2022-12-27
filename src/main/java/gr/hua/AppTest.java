package gr.hua;

import gr.hua.Dictionary.Entry;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

// this main method tests the hashMap by counting frequency of words from a given file.
// file path is passed to args[0] from terminal

public class AppTest {

	public static void main(String[] args) {
		// accepts only one argument
		if(args.length != 1) {
			System.err.println("Enter one argument only");
			System.exit(1);
		}
		
		OpenAddrHashMap<String, Integer> hashmap = new OpenAddrHashMap<>();
		
		// try block with resources, to ensure cleanup operations.
		try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
			// reading the first line.
		    String line = br.readLine();
		    // exit the loop if the line is null == eof
		    while (line != null) {
		    	// splits the line at the whitespaces and hold the words in an array
		    	String[] words = line.split("[ \\t\\x0B\\f\\r]+");
		    	// iterate over the words
		    	for(String w: words) {
		    		// gets the frequency of the current word
		    		Integer curFreq = hashmap.get(w);
		    		// if the frequency is null which means this is a new word, then the frequency of the word is one.
		    		if (curFreq == null) { 
						curFreq = 1;
					} else { // else just increment by one 
						curFreq++;
					}
		    		// store the word with its updated frequency.
		    		hashmap.put(w, curFreq);
		    	}
		    	//reading the next line in every loop.
		        line = br.readLine();
		    }
		    
		    
		} catch (FileNotFoundException e) {
			System.err.println("File not found!");
		} catch (IOException e) {
			System.err.println("An error occured while reading the file!");
		}
		
		// print every word with its frequency.
		for(Entry<String, Integer> e: hashmap) {
			System.out.println(e.getKey() + ": " + e.getValue());
		}
		
	}

}
