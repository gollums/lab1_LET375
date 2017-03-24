// Author(s):
// Version: 
// Date:	

import java.io.*;
import java.util.*;

public class WordLists {
	private Reader in = null;
	private Map<String, Integer> textMap;

	public WordLists(String inputFileName) {
		textMap = new TreeMap<>();
		try{
			in = new FileReader(inputFileName);
			getText();
		}catch (FileNotFoundException e){
			e.printStackTrace();
		}
	}

	private void getText(){
		try{
			while (true){
				String word = getWord();
				if (word != null) {
					if(textMap.containsKey(word)) {
						textMap.put(word, textMap.get(word) + 1);
					}else {
						textMap.put(word, 1);
					}
				}else {
					break;
				}
			}
		}catch (IOException e){
			e.printStackTrace();
		}
	}
	
	private boolean isPunctuationChar(char c) {
	    final String punctChars = ",.:;?!";
	    return punctChars.indexOf(c) != -1;
	}
	
	private String getWord() throws IOException {
		int state = 0;
		int i;
		String word = "";
		while ( true ) {
			i = in.read();
			char c = (char)i;
			switch ( state ) {
			case 0:
				if ( i == -1 )
					return null;
				if ( Character.isLetter( c ) ) {
					word += Character.toLowerCase( c );
					state = 1;
				}
				break;
			case 1:
				if ( i == -1 || isPunctuationChar( c ) || Character.isWhitespace( c ) )
					return word;
				else if ( Character.isLetter( c ) ) 
					word += Character.toLowerCase( c );
				else {
					word = "";
					state = 0;
				}
			}
		}
	}

	
	private String reverse(String s) {
		String out = "";
		String[] reversed = s.split("");
		if (reversed.length > 1){
			for(int i = reversed.length-1; i>=0; i--){
				out+=reversed[i];
			}
		}else {
			out +=reversed[0];
		}

		return out;
	}
	
	private void computeWordFrequencies() {
		try{
			BufferedWriter outStream = new BufferedWriter(new PrintWriter("alfaSorted.txt"));

			for (String s: textMap.keySet()) {
				outStream.write(s + "		" + textMap.get(s) +"\n");
			}
			outStream.close();
		}catch (IOException e){
			e.printStackTrace();
		}

	}

	private void computeFrequencyMap() {
		TreeMap<Integer, TreeSet<String>> frequencyMap = new TreeMap<>((o1, o2) -> o1 > o2 ? -1: o1 < o2 ? 1: 0);
		try{
			BufferedWriter outStream = new BufferedWriter(new PrintWriter("frequencySorted.txt"));

			for (Map.Entry<String,Integer> s: textMap.entrySet()) {
				if (!frequencyMap.containsKey(s.getValue())){
					frequencyMap.put(s.getValue(), new TreeSet<>(Collections.singleton(s.getKey())));
				}else {
					frequencyMap.get(s.getValue()).add(s.getKey());
				}
			}
			for(Map.Entry<Integer, TreeSet<String>> e: frequencyMap.entrySet()){
				outStream.write(e.getKey() +":\n");
				for(String word: e.getValue()){
					outStream.write("		" + word + "\n");
				}
			}

			outStream.close();
		}catch (IOException e){
			e.printStackTrace();
		}
	}
	

	private void computeBackwardsOrder() {
		TreeSet<String> reversedString = new TreeSet<>();
		try{
			BufferedWriter outStream = new BufferedWriter(new PrintWriter("backwardsSorted.txt"));
			for(String s: textMap.keySet()){
				reversedString.add(reverse(s));
			}
			for(String s: reversedString){
				outStream.write(reverse(s) + "\n");
			}
			outStream.close();
		}catch (IOException e){
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		WordLists wl = new WordLists(args[0]);  // arg[0] contains the input file name
		wl.computeWordFrequencies();
		wl.computeFrequencyMap();
		wl.computeBackwardsOrder();
		
		System.out.println("Finished!");
	}
}



















