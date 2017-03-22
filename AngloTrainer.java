// Author(s):
// Email:	
// Date:	

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class AngloTrainer {
	private Set wordList;
	private Random randomGenerator;
	private int longestWord;

	public AngloTrainer(String dictionaryFile) throws IOException {

		wordList = new TreeSet();
		randomGenerator = new Random();
		longestWord = 0;

		loadDictionary(dictionaryFile);
		System.out.println(wordList.size());
		dumpDict();

	}

	// use this to verify loadDictionary
	private void dumpDict() {
	    // Print out the dictionary at the screen.
		Iterator <String> setIterator = wordList.iterator();
		String s = randomLetters(longestWord);
		System.out.println(s);
		while (setIterator.hasNext()){
			if(includes(setIterator.next(),s)){
				System.out.println("herå");
			}

		}
	}

	/**
	 * todo: define
	 * @param fileName
	 */
	private void loadDictionary( String fileName ) {
	    // Read the dictionary into a suitable container.
	    // The file is a simple text file. One word per line.
		String length;
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
			String word;
			while (true){
				word =  bufferedReader.readLine();
				if (word == null) {
					break;
				}
				else {
					wordList.add(word);

					length = word;
					if (length.length() > longestWord) {
						longestWord = length.length();
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private String randomLetters( int length ) {
	    // this makes vovels a little more likely
	    String letters = "aabcdeefghiijklmnoopqrstuuvwxyyz";  
	    StringBuffer buf = new StringBuffer(length);
	    for ( int i = 0; i < length; i++ ) 
		    buf.append( letters.charAt(randomGenerator.nextInt(letters.length())));
	
	    return buf.toString();
	}
	
	
	/* Def. includes
	 * Let #(x,s) = the number of occurrences of the charcter x in the string s.
	 * includes(a,b) holds iff for every character x in b, #(x,b) <= #(x,a)
	 * 
	 * A neccessary precondition for includes is that both strings are sorted
	 * in ascending order.
	 */
	private boolean includes( String a, String b ) {
		if ( b == null || b.length() == 0 )
			return true;
		else if ( a == null || a.length() == 0 )
			return false;
		
		//precondition: a.length() > 0 && b.length() > 0
		int i = 0, j = 0;
		while ( j < b.length() ) {
			if (i >= a.length() || b.charAt(j) < a.charAt(i))
				return false;
			else if (b.charAt(j) == a.charAt(i)) {
				i++; j++;
			} else if (b.charAt(j) > a.charAt(i))
				i++;
		}
		//postcondition: j == b.length()
		return true;
	}
	
     // This is just for demonstration purposes.
	private void testIncludes() { 
		//                                            expected value
		System.out.println(includes("abc",""));		//t
		System.out.println(includes("","abc"));		//f
		System.out.println(includes("abc","abc"));	//t
		System.out.println(includes("abc","bcd"));	//f
		System.out.println(includes("abc","a"));	//t
		System.out.println(includes("abc","b"));	//t
		System.out.println(includes("abc","c"));	//t
		System.out.println(includes("abc","ab"));	//t
		System.out.println(includes("abc","bc"));	//t
		System.out.println(includes("abc","ac"));	//t
		System.out.println(includes("abc","abcd"));	//f
		System.out.println(includes("abc","abd"));	//f
		System.out.println(includes("abc","d"));	//f
		System.out.println(includes("",""));		//t
		System.out.println(includes("abc","ca"));	//f
		System.out.println(includes("abc","bac"));	//f
		System.out.println(includes("abc","abbc"));	//f
		System.out.println(includes("abbc","abc"));	//t
		System.out.println(includes(null,null));    //t
		System.out.println(includes(null,""));	    //t
		System.out.println(includes(null,"abc"));	//f
		System.out.println(includes("",null));		//t
		System.out.println(includes("abc",null));   //t
	}

    public static void main(String[] args) {
		try {
			new AngloTrainer("/Users/gollum/Documents/Programing/Java_Dev/LET375/lab1/dictionary.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}












