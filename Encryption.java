import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Encryption {

	public static void main(String[] args) throws IOException {

		Scanner s = new Scanner(new File("file2.txt"));//scan the file that will be encrypted
	    File file = new File("encrypted.txt");//open a file for encrypted words
	    FileWriter fileWriter = new FileWriter(file, false);
	    BufferedWriter bWriter = new BufferedWriter(fileWriter);

	    //Set of numbers that relatively prime with 26
	  	int[] x = {1, 3, 5, 7, 9, 11, 15, 17, 19, 21, 23, 25};
  		Random rnd = new Random();
  		int random = rnd.nextInt(x.length + 1);
  		int a = x[random];	//select random number for a
  		int b = rnd.nextInt(26);	//select random number for b between 0 and 26

	    ArrayList <String> words = new ArrayList <>();//list for words
	    String word = "";

	    while(s.hasNext()){       //read file and make array list from words
	    	word = (s.next()).toUpperCase();
 			words.add(word);
 		}

	    for(int k = 0; k < words.size(); k++){
	    	String word2 = words.get(k);

	    	if(containsDigit(word2) == true){//if word consists of numbers write to file directly
	    		bWriter.write(word2);
		    	bWriter.write(" ");
	    	}
	    	else{//else encrypt the word
	    		String encryptedWord = encrypt(a, b, word2);
		    	bWriter.write(encryptedWord);//write the encrypted word to the file
		    	bWriter.write(" ");
	    	}
	    }
  		s.close();
  		bWriter.close();

	}

	public static String encrypt(int a, int b, String word){//encryption method

		String encrypted = "";//encrypted word
		boolean bool = false;

		for(int k = 0; k < word.length(); k++){
			char c = word.charAt(k);
			if(c == 'Ý')//if word contains Ý exchange it with I
				c = 'I';
			bool = Character.isLetter(c);
			if(bool == false)//if character is not letter add it to encrypted word directly
				encrypted += c;
			else{	//if character is a letter encrypt and add to encyrpted word
				int x = ((a * (int)c) + b) % 26;
				encrypted += (char) ('A' + x);
			}
		}
		return encrypted;
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
}
