// Author(s):
// Email:	
// Date:	

import java.io.*;
import java.util.*;

public class AngloTrainer {
	// ...
	private Set wordList;
	private Random randomGenerator;
	private int maxLength;
	private int numberOfWords;
	private String randLetters;

	public AngloTrainer(String dictionaryFile) throws IOException {
		wordList = new TreeSet();
		randomGenerator = new Random();
		maxLength = 5;
		numberOfWords = 0;

		loadDictionary(dictionaryFile);
		randLetters = randomLetters(maxLength);
		output();
		userInput();

	}

	// use this to verify loadDictionary
	private void dumpDict() {
		// Print out the dictionary at the screen.
        for (Object s:wordList) {

            String sortedRandLetters = sort(randLetters);
            String sortedString = sort((String) s);
            
            if(includes(sortedRandLetters,sortedString)){
                System.out.println((String) s);
            }
        }
	}

	private void loadDictionary( String fileName ) {
		String word;
		try {
			BufferedReader inStream = new BufferedReader(new FileReader(fileName));
			while (true) {
				word = inStream.readLine();
				if(word == null) {
					break;
				}
				wordList.add(word);
				
			/*	if(word.length() > maxLength)
					maxLength = word.length();*/

				numberOfWords++;
			}
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
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

	private String sort(String s){
		String out = "";
		String[] toBeSorted = s.split("");
		Arrays.sort(toBeSorted);

		for(int i = 0; i<toBeSorted.length;i++){
			out+=toBeSorted[i];
		}
		return out;
	}

	private void userInput(){
		try {
			Scanner inputScanner = new Scanner(System.in);
			while (true) {
				String inputString = inputScanner.nextLine();
				String sortedInput = sort(inputString);
				String sortedRandLetters;
				sortedRandLetters = sort(randLetters);
			/*System.out.println(sortedRandLetters);
			System.out.println(sortedInput);*/
				if (includes(sortedRandLetters, sortedInput) && wordList.contains(inputString)) {
					System.out.println("Ok!");
				} else if (includes(sortedRandLetters, sortedInput)) {
					System.out.println("Your suggestion was not found in the dictionary.\nI found:");
					dumpDict();
					break;
				} else {
					System.out.println("error lol u suck\nTry again\n");

				}
			}
		}catch (NoSuchElementException e){
			e.printStackTrace();
		}
	}

	private void output(){
		System.out.println(numberOfWords + " words loaded from dictionary.txt\n");
		System.out.println("The random letters are: " + randLetters + "\n");
		System.out.println("Try to build as many words from these letters as you can!\n");
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

	public static void main(String[] args) throws IOException {
		new AngloTrainer("/Users/gollum/Documents/Programing/Java_Dev/LET375/lab1/dictionary.txt");
	}
}
