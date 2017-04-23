// File Name:   AI.java 
// Author:      Lin Chun
// Student Number: 3012218145
// Description: An AI can make decision on which card played leads to best
// score situation, by calculating possible score against user and try out
// each card of the 5-card aiHand.

import java.util.Vector;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class AI {
	Hand aiHand;
	ScoreZone aiScoreZone;
	
	public AI(Hand hand, ScoreZone scoreZone){
		aiHand = hand;
		aiScoreZone = scoreZone;
	}
	public void play(Parade parade, Hand playerHand, ScoreZone user){
		int bestScore = 1000;//initial with a max value
		int bestIndex = 0;
		//find the best choice
		for(int i = 0; i < 5 && aiHand.handCards[i].isEmpty == false && aiHand.handCards[i] != null;i++){
			int temp = tryAddOne(aiHand.handCards[i], parade, aiScoreZone, user);
			if (bestScore >= temp){
				bestScore = temp;
				bestIndex = i;
			}
		}
    	parade.moveParade(aiHand.handCards[bestIndex], aiScoreZone, user);
	    //final round conditions 
    	if(aiScoreZone.isColorFull() || user.isColorFull() || Deck.remainingCards == 0){
			if(Hand.isLastRound == false){
    		  Hand.isLastRound = true;
    		  System.out.println("FINAL ROUND!");
    		  Text text = new Text();
    		  text.setText("FINAL ROUND!");
    	      text.setFont(new Font("Cobac", 24.0));
    	      playerHand.getChildren().add(text);
    	      AnchorPane.setTopAnchor(text, 150.0);
			}
			else {
			  Hand.lastTimes--;
			  aiHand.handCards[bestIndex].isEmpty = true;
			  
			  aiHand.getChildren().remove(aiHand.backCards[bestIndex]);
	     	  aiHand.getChildren().remove(aiHand.handCards[bestIndex]);
	     	  aiHand.handCards[bestIndex] = null;
			  if(Hand.lastTimes == 0){
				  playerHand.finalChoice(user, this);
              }
			}	
		}
    	
        if ((Hand.isLastRound == true && Hand.lastTimes == 2) || Hand.isLastRound == false)
    	   aiHand.refreshRivalHand(aiHand.handCards[bestIndex], parade, aiScoreZone);
	}
	
	public int tryAddOne(Card tryCard, Parade parade, ScoreZone scoreZone, ScoreZone rival){
		//try if one card played and removal cards go into score
		//use vector to add all cards in score and a new card
		Vector<Card> tryAddBlue = new Vector<Card>(); 
		Vector<Card> tryAddPurple = new Vector<Card>(); 
		Vector<Card> tryAddOrange = new Vector<Card>(); 
		Vector<Card> tryAddWhite = new Vector<Card>(); 
		Vector<Card> tryAddGreen = new Vector<Card>(); 
		Vector<Card> tryAddRed = new Vector<Card>(); 
		for (int i = 0; i < scoreZone.blueCount; i++)
			tryAddBlue.add(scoreZone.blue[i]);
		for (int i = 0; i < scoreZone.purpleCount; i++)
			tryAddPurple.add(scoreZone.purple[i]);
		for (int i = 0; i < scoreZone.orangeCount; i++)
			tryAddOrange.add(scoreZone.orange[i]);
		for (int i = 0; i < scoreZone.whiteCount; i++)
			tryAddWhite.add(scoreZone.white[i]);
		for (int i = 0; i < scoreZone.greenCount; i++)
			tryAddGreen.add(scoreZone.green[i]);
		for (int i = 0; i < scoreZone.redCount; i++)
			tryAddRed.add(scoreZone.red[i]);
		
		for (int i = 0; i < Parade.count; i++ ){
			if((i < Parade.count - tryCard.getNumber()) && (tryCard.getColor() == parade.paradeCards[i].getColor()
					|| parade.paradeCards[i].getNumber() <= tryCard.getNumber())){
				if (tryCard.getColor() == 1) 
					tryAddBlue.add(tryCard);
				else if (tryCard.getColor() == 2)
				    tryAddPurple.add(tryCard);
				else if (tryCard.getColor() == 3)
					tryAddOrange.add(tryCard);
				else if (tryCard.getColor() == 4)
					tryAddWhite.add(tryCard);
				else if (tryCard.getColor() == 5)
					tryAddGreen.add(tryCard);
				else if (tryCard.getColor() == 6)
					tryAddRed.add(tryCard);
			}
	    }
		//count temp score
		int tempScore = 0;
		   if(tryAddBlue.size() - 2 >= rival.blueCount)
			   tempScore = tempScore + tryAddBlue.size();
		   else{
			   for (int i = 0; i < tryAddBlue.size(); i++)
				   tempScore = tempScore + tryAddBlue.get(i).getNumber();
		   }
		   if(tryAddPurple.size() - 2 >= rival.purpleCount)
			   tempScore = tempScore + tryAddPurple.size();
		   else{
			   for (int i = 0; i < tryAddPurple.size(); i++)
				   tempScore = tempScore + tryAddPurple.get(i).getNumber();
		   }
		   if(tryAddOrange.size() - 2 >= rival.orangeCount)
			   tempScore = tempScore + tryAddOrange.size();
		   else{
			   for (int i = 0; i < tryAddOrange.size(); i++)
				   tempScore = tempScore + tryAddOrange.get(i).getNumber();
		   }
		   if(tryAddWhite.size() - 2 >= rival.whiteCount)
			   tempScore = tempScore + tryAddWhite.size();
		   else{
			   for (int i = 0; i < tryAddWhite.size(); i++)
				   tempScore = tempScore + tryAddWhite.get(i).getNumber();
		   }
		   if(tryAddGreen.size() - 2 >= rival.greenCount)
			   tempScore = tempScore + tryAddGreen.size();
		   else{
			   for (int i = 0; i < tryAddGreen.size(); i++)
				   tempScore = tempScore + tryAddGreen.get(i).getNumber();
		   }
		   if(tryAddRed.size() - 2 >= rival.redCount)
			   tempScore = tempScore + tryAddRed.size();
		   else{
			   for (int i = 0; i < tryAddRed.size(); i++)
				   tempScore = tempScore + tryAddRed.get(i).getNumber();
		   }
		   
		   return tempScore;
	}
	
	public int addLastTwo(Card one, Card two, ScoreZone scoreZone, ScoreZone rival){
		Vector<Card> tryAddBlue = new Vector<Card>(); 
		Vector<Card> tryAddPurple = new Vector<Card>(); 
		Vector<Card> tryAddOrange = new Vector<Card>(); 
		Vector<Card> tryAddWhite = new Vector<Card>(); 
		Vector<Card> tryAddGreen = new Vector<Card>(); 
		Vector<Card> tryAddRed = new Vector<Card>(); 
		for (int i = 0; i < scoreZone.blueCount; i++)
			tryAddBlue.add(scoreZone.blue[i]);
		for (int i = 0; i < scoreZone.purpleCount; i++)
			tryAddPurple.add(scoreZone.purple[i]);
		for (int i = 0; i < scoreZone.orangeCount; i++)
			tryAddOrange.add(scoreZone.orange[i]);
		for (int i = 0; i < scoreZone.whiteCount; i++)
			tryAddWhite.add(scoreZone.white[i]);
		for (int i = 0; i < scoreZone.greenCount; i++)
			tryAddGreen.add(scoreZone.green[i]);
		for (int i = 0; i < scoreZone.redCount; i++)
			tryAddRed.add(scoreZone.red[i]);
		
		if (one.getColor() == 1) 
			tryAddBlue.add(one);
		else if (one.getColor() == 2)
		    tryAddPurple.add(one);
		else if (one.getColor() == 3)
			tryAddOrange.add(one);
		else if (one.getColor() == 4)
			tryAddWhite.add(one);
		else if (one.getColor() == 5)
			tryAddGreen.add(one);
		else if (one.getColor() == 6)
			tryAddRed.add(one);

		if (two.getColor() == 1) 
			tryAddBlue.add(two);
		else if (two.getColor() == 2)
		    tryAddPurple.add(two);
		else if (two.getColor() == 3)
			tryAddOrange.add(two);
		else if (two.getColor() == 4)
			tryAddWhite.add(two);
		else if (two.getColor() == 5)
			tryAddGreen.add(two);
		else if (two.getColor() == 6)
			tryAddRed.add(two);

		
		int tempScore = 0;
		   if(tryAddBlue.size() - 2 >= rival.blueCount)
			   tempScore = tempScore + tryAddBlue.size();
		   else{
			   for (int i = 0; i < tryAddBlue.size(); i++)
				   tempScore = tempScore + tryAddBlue.get(i).getNumber();
		   }
		   if(tryAddPurple.size() - 2 >= rival.purpleCount)
			   tempScore = tempScore + tryAddPurple.size();
		   else{
			   for (int i = 0; i < tryAddPurple.size(); i++)
				   tempScore = tempScore + tryAddPurple.get(i).getNumber();
		   }
		   if(tryAddOrange.size() - 2 >= rival.orangeCount)
			   tempScore = tempScore + tryAddOrange.size();
		   else{
			   for (int i = 0; i < tryAddOrange.size(); i++)
				   tempScore = tempScore + tryAddOrange.get(i).getNumber();
		   }
		   if(tryAddWhite.size() - 2 >= rival.whiteCount)
			   tempScore = tempScore + tryAddWhite.size();
		   else{
			   for (int i = 0; i < tryAddWhite.size(); i++)
				   tempScore = tempScore + tryAddWhite.get(i).getNumber();
		   }
		   if(tryAddGreen.size() - 2 >= rival.greenCount)
			   tempScore = tempScore + tryAddGreen.size();
		   else{
			   for (int i = 0; i < tryAddGreen.size(); i++)
				   tempScore = tempScore + tryAddGreen.get(i).getNumber();
		   }
		   if(tryAddRed.size() - 2 >= rival.redCount)
			   tempScore = tempScore + tryAddRed.size();
		   else{
			   for (int i = 0; i < tryAddRed.size(); i++)
				   tempScore = tempScore + tryAddRed.get(i).getNumber();
		   }
		   
		   return tempScore;
	}
		
}
