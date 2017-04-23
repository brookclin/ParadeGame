// File Name:   Parade.java 
// Author:      Lin Chun
// Student Number: 3012218145
// Description: A parade is the play zone for both player and AI, After the
// card is played into parade, cards in removal mode which meet the condition
// will be moved out to the specific score zone, and calculate the real time
// score. In addition, mouse entered cards in playerHand will show the prediction
// on which cards will be moved out. WATCH OUT THE PARADE IS MOVING! XD

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;


public class Parade extends AnchorPane{

	public static int count = 0;
	//NOTICE: count from 1 while array from 0
	Deck deck = Deck.getInstance();
	public Card [] paradeCards = new Card[30];

	public Parade(){
		initialParade();
	}
	public void initialParade(){
		for (int i = 0; i < 6; i++){
			paradeCards[i] = deck.drawCard();
			TranslateTransition translateTransition = new TranslateTransition(Duration.millis(1000),paradeCards[i]);
			translateTransition.setFromX(-50);
			translateTransition.setToX(paradeCards[i].getLayoutX());
			translateTransition.setDelay(Duration.millis(500));
			translateTransition.play();
			FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), paradeCards[i]);
			fadeTransition.setFromValue(0);
			fadeTransition.setToValue(1);
			paradeCards[i].setOpacity(0);
			fadeTransition.setDelay(Duration.millis(500));
			fadeTransition.play();
			AnchorPane.setLeftAnchor(paradeCards[i], i*50.0);
		    this.getChildren().add(paradeCards[i]);
		    count++;
		}
	}
	
	public void joinParade(Card card){
		card.setOnMouseEntered(null);
		card.setOnMouseExited(null);
		paradeCards[count] = new Card(card.getColor(), card.getNumber());
		TranslateTransition trans = new TranslateTransition(Duration.millis(400), paradeCards[count]);
		trans.setFromY(-50);
		trans.setToY(paradeCards[count].getLayoutY());
		trans.play();
		count++;
		System.out.println("Card joined parade: " + card.getColor() + card.getNumber());
	}
	
	public void removeParade(){
		//use to update parade: step 1
		for(int i = 0; i < count; i++){
			this.getChildren().remove(paradeCards[i]);
		}
	}
	
	public void refreshParade(){
		for(int i = 0; i < count; i++){
			AnchorPane.setLeftAnchor(paradeCards[i], i*50.0);
			this.getChildren().add(paradeCards[i]);
			
		}
	}
	
	public void predict(Card card){
		//cards possible move out will be shown
		for (int i = 0; i < Parade.count; i++ ){
			if((i < Parade.count - card.getNumber()) && (card.getColor() == paradeCards[i].getColor()
					|| paradeCards[i].getNumber() <= card.getNumber())){
				 TranslateTransition transition2 = new TranslateTransition(Duration.millis(100),paradeCards[i]);
				 transition2.setToY(paradeCards[i].getLayoutY() - 20);
			     transition2.play();
			}
			if(i >= Parade.count - card.getNumber()){
				 FadeTransition fadeTransition = new FadeTransition(Duration.millis(100), paradeCards[i]);
				 fadeTransition.setFromValue(1);
				 fadeTransition.setToValue(0.35);
				 fadeTransition.play();
			}
		}
	}
	
	public void disPredict(){
		for (int i = 0; i < Parade.count; i++ ){
				 TranslateTransition transition2 = new TranslateTransition(Duration.millis(100),paradeCards[i]);
				 transition2.setToY(paradeCards[i].getLayoutY());
			     transition2.play();
			     FadeTransition fadeTransition = new FadeTransition(Duration.millis(100), paradeCards[i]);
				 fadeTransition.setToValue(1);
				 fadeTransition.play();
		}
	}
	
	public void moveParade(Card card, ScoreZone scoreZone, ScoreZone rivalScore){
		int removeCount = 0;
		for (int i = 0; i < count; i++ ){
			if(paradeCards[i]!=null&&(i < count - card.getNumber()) && (card.getColor() == paradeCards[i].getColor()
					|| paradeCards[i].getNumber() <= card.getNumber())){
				scoreZone.addToScoreZone(paradeCards[i], rivalScore);
				paradeCards[i] = new Card();
				removeCount++;
				System.out.println("removeCount: " + removeCount);
			}
		}
		//move cards forward
		for(int i = 0; i < count; i++){
			if(!paradeCards[i].isEmpty)
				continue;
			else{
				int j = i + 1;
				for(; j < count && paradeCards[j].isEmpty; j++);
				paradeCards[i] = paradeCards[j];
				paradeCards[j] = new Card();
			}
		}
		removeParade();
		count = Parade.count - removeCount;
	    joinParade(card);
		refreshParade();
		System.out.println("parade.count: " + Parade.count);
	}
}
