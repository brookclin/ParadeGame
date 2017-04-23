// File Name:   Deck.java 
// Author:      Lin Chun
// Student Number: 3012218145
// Description: A deck is used to save 66 cards and for drawing,
// use a 2-dimension bool array to save if the card is drawn.

import javafx.scene.text.Text;

public class Deck {
    //NOTICE: BOOL[][] isInDeck counts starting at 0, while color 1 ~ 6, number 0 ~ 10 
	public static int remainingCards;
	public static boolean [][] isInDeck = new boolean[6][11];
	//use the boolean[][] to mark which card is out, to shuffle
	Text remainingText = new Text();

	public void initialDeck(){
		for (int i = 0; i < 6; i++){
			for(int j = 0; j < 11; j++)
				isInDeck[i][j] = true;
		}
		remainingCards = 66; 
		remainingText.setText(remainingCards + "");
	}
	
	//drawCard(): use 2 random number to check if isInDeck[][] is true
	//which can be drawn, or do it again
	public Card drawCard(){
		int col = (int) (Math.random() * 6);//0 ~ 5
		int num = (int) (Math.random() * 11);//0 ~ 10
		while(!isInDeck[col][num]){
			col = (int) (Math.random() * 6);
			num = (int) (Math.random() * 11);
		}
		isInDeck[col][num] = false;
		int actualCol = col + 1;
		int actualNum = num;
		remainingCards--;
		remainingText.setText(remainingCards + "");
		
		Card tempCard = new Card(actualCol, actualNum);
		return tempCard;
	}
	
	public static Deck getInstance() {
		return DeckHolder.INSTANCE;
	}

	private static class DeckHolder {
		private static final Deck INSTANCE = new Deck();
	}
	
	public void restartDeck(){
		remainingCards = 66;
		isInDeck = new boolean[6][11];
	}
}
