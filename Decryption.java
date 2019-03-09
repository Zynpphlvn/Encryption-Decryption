import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Decryption {

	public static void main(String[] args) throws IOException {

		Scanner s = new Scanner(new File("encrypted.txt"));//scan encrypted file
		File file = new File("decrypted.txt");
		FileWriter fileWriter = new FileWriter(file, false);
		BufferedWriter bWriter = new BufferedWriter(fileWriter);

	    ArrayList <String> words = new ArrayList <>();//list for encrypted words
	    String word = "";
	    boolean control = false;

	    Scanner dict = new Scanner(new File("dictionary.txt"));//scan dictionary

		ArrayList <String> dictionary = new ArrayList <>();//make a list for dictionary

		while(dict.hasNext()){
			String dictWord = (dict.next()).toUpperCase();
			if(dictWord.indexOf('Ý') > 0)//if word has Ý character exchange it with I
				dictWord = dictWord.replace('Ý', 'I');
			dictionary.add(dictWord);
		}

		dict.close();

	    while(s.hasNext()){
			word = (s.next()).toUpperCase();
			words.add(word);
		}

		for(int b = 0; b < 26; b++){
	    	for(int a = 1; a < 26; a += 2){
	    		if(a == 13) a = 15;
	    		control = decrypt(a, b, words, dictionary, bWriter);//decrypt and check is word true or not
	    		for(int x = 0; x < words.size(); x++){
	    	    	word = words.get(x);
	    			if(containsDigit(word) == true)//if word has digit remove it because of rewriting
	    				words.remove(word);
	    	    }
	    		if(control == true){//if decryption is successful write a and b values
	    			System.out.println("a is: " + a + " b is: " + b);
	    			break;
	    		}
	    	}
	    	if(control == true)//stop the searhing
   			break;
	    }
		s.close();
		bWriter.close();
	 }

	public static boolean decrypt(int a, int b, ArrayList<String> words, ArrayList<String> dict, BufferedWriter bWriter) throws IOException{
		//decryption method
		a = modInverse(a, 26);//take modular of a inverse
		String decrypted = "";
		int totalWords = words.size();//total number of words
		int cntTrues = 0;//counter for true decrypted words

		ArrayList <String> decryptedList = new ArrayList <String> ();//make a list for decrypted words

		for(int x = 0; x < words.size(); x++){
			String word = words.get(x);
			decrypted = "";
			for(int y = 0; y < word.length(); y++){
				if(word.charAt(y) >= 33 && word.charAt(y) <= 64){//if word has punctuation write it directly
					decrypted += word.charAt(y);
				}
				else{//else decrypt the character
					char encrypted = word.charAt(y);
					if(encrypted == 'Ý') encrypted = 'I';
					int k = ((a * ((int)encrypted - b))) % 26;
					decrypted += (char)('A' + k);//add decrypted char to word
				}

			}
			decryptedList.add(decrypted);
		}

		for(int k = 0; k < decryptedList.size(); k++){//control decrytped words from dictionary
			String decrypt = decryptedList.get(k);
			if(containsDigit(decrypt) == false && dict.indexOf(decrypt) < 0){
			}
			else if(containsDigit(decrypt) == true){//if word consists of numbers count it true
				cntTrues++;
			}
			else if(containsDigit(decrypt) == false && dict.contains(decrypt) == true){
				cntTrues++;
			}
		}
		int percentage = (100 * cntTrues) / totalWords;//calculate accuracy percentage

		if(percentage > 60){//if decryption is successful write the words to file
			for(int k = 0; k < decryptedList.size(); k++){
				String write = decryptedList.get(k);
				bWriter.write(write);
				bWriter.write(" ");
			}
			return true;
		}
		return false;
	}

	public static boolean containsDigit(String s) {//check if string has digit or not
	    boolean containsDigit = false;

	    if (s != null && !s.isEmpty()) {
	        for (char c : s.toCharArray()) {
	            if (containsDigit = Character.isDigit(c)) {
	                break;
	            }
	        }
	    }

	    return containsDigit;
	}
	static int modInverse(int a, int m){//modularity method
		a = a % m;
	    for (int x = 1; x < m; x++)
	    	if ((a * x) % m == 1)
	    		return x;
	    return 1;
	}
}
