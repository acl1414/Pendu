package com.acl14.game.pendu;

public class TestReplaceCharacter {

	String wordToFind;
	String wordToDisplay ;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		TestReplaceCharacter test = new TestReplaceCharacter();
		test.replace();
	
		
	}
	
	
	private void replace() {
		
		wordToFind = "ABCDEFG";
		wordToDisplay = initWordToDisplay(wordToFind);
		
		boolean isLetterFind;
		
		System.out.println(addSpace(wordToDisplay));
		
		isLetterFind = checkLetter("B");
		System.out.println(addSpace(wordToDisplay));
		
		isLetterFind = checkLetter("D");
		System.out.println(addSpace(wordToDisplay));
		
		isLetterFind = checkLetter("F");
		System.out.println(addSpace(wordToDisplay));	
		
	}
	
	
	private String initWordToDisplay(String wordToFind) {
		
		String result = "";
		
		for (int i = 0; i < wordToFind.length(); i++) {
			result=result+"_";
		}
		
		
		return result;
		
	}
	
	private boolean checkLetter(String letter) {
		boolean result = false;
		
		for (int i = 0; i < wordToFind.length(); i++) {
			if (wordToFind.substring(i, i+1).equals(letter)) {
				result = true;
				
				wordToDisplay = wordToDisplay.substring(0,i)+letter+wordToDisplay.substring(i+1);
				
			}
		}
		 
		
		return result;
		
	}
	
	private String addSpace(String word) {
		
		String result="";
		
		for (int i = 0; i < word.length(); i++) {

			result= result + word.substring(i, i+1) + " ";
		}
		
		return result;
	}

}
