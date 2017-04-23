// File Name:   Hand.java 
// Author:      Lin Chun
// Student Number: 3012218145
// Description: A Hand saves 5 cards both for User and AI, with card 
// in and out transitions, refresh (update card) method, and some methods
// only for final round situation such as finalChoice and AIChoice.
import java.util.Vector;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;


public class Hand extends AnchorPane{
	
	public Hand(){
		
	}
	
	Deck deck = Deck.getInstance();
	
	public static boolean isLastRound = false;
	public static int lastTimes = 2;
	public Card [] handCards = new Card[5];
	public ImageView [] backCards = new ImageView[5];
	public Vector<Card> finalChoiceCard = new Vector<Card>();
	
	public void initialPlayerHand(Parade parade, ScoreZone scoreZone, AI computer){
		
		for (int i = 0; i < 5; i++){
			handCards[i] = deck.drawCard();
			this.setPrefSize(500.0, 150.0);
			AnchorPane.setLeftAnchor(handCards[i], i*100.0);
		    this.getChildren().add(handCards[i]);
		    //Intro translate + fade
		    TranslateTransition translateTransition = new TranslateTransition(Duration.millis(1000),handCards[i]);
			translateTransition.setFromY(50);
			translateTransition.setToY(handCards[i].getLayoutY());
			translateTransition.setDelay(Duration.millis(100));
			translateTransition.play();
			FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), handCards[i]);
			fadeTransition.setFromValue(0);
			fadeTransition.setToValue(1);
			handCards[i].setOpacity(0);
			fadeTransition.setDelay(Duration.millis(100));
			fadeTransition.play();
			//Mouse Event Adding
		    handCards[i].mouseEntered(parade);
		    handCards[i].mouseClicked(this, parade, scoreZone, computer);
		}
	}
    public void initialRivalHand(Parade parade){
    	Image backImage = new Image("Card Images/back_3.png" , 80, 120, true, true);
		ImageView backImageView = new ImageView();
		backImageView.setImage(backImage);
		for (int i = 0; i < 5; i++){
			handCards[i] = deck.drawCard();
			//backCards[] to save positions of AI's cards (back) 
			backCards[i] = new ImageView(backImage);
			this.setPrefSize(500.0, 150.0);
			TranslateTransition translateTransition = new TranslateTransition(Duration.millis(1000),backCards[i]);
			translateTransition.setFromY(backCards[i].getLayoutY() -50);
			translateTransition.setToY(backCards[i].getLayoutY());
			translateTransition.play();
			FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), backCards[i]);
			fadeTransition.setFromValue(0);
			fadeTransition.setToValue(1);
			backCards[i].setOpacity(0);
			fadeTransition.setDelay(Duration.millis(300));
			fadeTransition.play();
			AnchorPane.setLeftAnchor(backCards[i], i*100.0);
			this.getChildren().add(backCards[i]);
		}
	}
	
	public void refreshHandCard(Card card, Parade parade, ScoreZone scoreZone, AI computer){
		int i = 0;
		for(; i < 5 && card != handCards[i]; i++);
		//find out card's position for anchorPane to add
		TranslateTransition translateTransition = new TranslateTransition(Duration.millis(500),card);
		translateTransition.setByY(- 100);
		translateTransition.play();
		FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), card);
		fadeTransition.setFromValue(1);
		fadeTransition.setToValue(0);
		fadeTransition.play();
		
		handCards[i] = deck.drawCard();
		AnchorPane.setLeftAnchor(handCards[i], i*100.0);
		this.getChildren().add(handCards[i]);
		FadeTransition fadeTransition2 = new FadeTransition(Duration.millis(500), handCards[i]);
		fadeTransition2.setDelay(Duration.millis(500));
		handCards[i].setOpacity(0);
		fadeTransition2.setFromValue(0);
		fadeTransition2.setToValue(1);
		fadeTransition2.play();
	
		//add mouse actions
		handCards[i].mouseEntered(parade);
		handCards[i].mouseClicked(this, parade, scoreZone, computer);
		
	}
	
	
	public void refreshRivalHand(Card card, Parade parade, ScoreZone scoreZone){
		int i = 0;
		for(; i < 5 && card != handCards[i]; i++);
		//find the position of card
		if(MainGame.isDebug == false){
		     FadeTransition fadeBack = new FadeTransition(Duration.millis(500), backCards[i]);
		     fadeBack.setFromValue(1);
		     fadeBack.setToValue(0);
		     fadeBack.play();
		}
		
		TranslateTransition translateTransition = new TranslateTransition(Duration.millis(800),card);
		translateTransition.setByY(100);
		translateTransition.play();
		
		FadeTransition fadeTransition = new FadeTransition(Duration.millis(400), card);
		fadeTransition.setFromValue(1);
		fadeTransition.setToValue(0);
		fadeTransition.setDelay(Duration.millis(400));
		fadeTransition.play();
		
		if(MainGame.isDebug == false){
		     AnchorPane.setLeftAnchor(handCards[i], i*100.0);
		     this.getChildren().add(handCards[i]);
        }
		handCards[i] = deck.drawCard();
		
		
		if(MainGame.isDebug == false){
		     FadeTransition fadeBack2 = new FadeTransition(Duration.millis(1000), backCards[i]);
		     fadeBack2.setFromValue(0);
		     fadeBack2.setToValue(1);
		     backCards[i].setOpacity(0);
		     fadeBack2.play();
		}
		else {
		     AnchorPane.setLeftAnchor(handCards[i], i*100.0);
		     this.getChildren().add(handCards[i]);
		}
		
	}
	
    public void finalChoice(ScoreZone userScore, AI computer){
    	Text text = new Text();
    	text.setText("Please Choose 2 cards");
    	text.setFont(new Font("Cobac", 24.0));
    	this.getChildren().add(text);
    	AnchorPane.setTopAnchor(text, 130.0);
    	for(int i = 0; i < 5 ; i++){
    		handCards[i].setOnMouseEntered(null);
        	handCards[i].setOnMouseClicked(null);
        	//remove mouse actions
    		if (handCards[i].isEmpty == false) {
    			handCards[i].finalMouseClick(this, userScore, computer);
			}
    		else {
				this.getChildren().remove(handCards[i]);
			}
    
    	}
    	
    }
    
    public void aiChoice(AI computer, ScoreZone user){
    	
    	int bestI = 0;
    	int bestL = 1;
    	int bestScore = 1000;
    	int i = 0;
    	for(;i<5;i++){
    		if(handCards[i]!=null)
    			break;
    	}
    	for(; i < 5 && handCards[i] != null&& handCards[i].isEmpty == false; i++){
    		int l = i + 1;
    		while(l<5&&handCards[l]==null){
    			l++;
    		}
    		for(; l < 5 && handCards[l] != null && handCards[l].isEmpty == false; l++)
    		{
    			int temp = computer.addLastTwo(handCards[i], handCards[l], computer.aiScoreZone, user);
    		    if (temp <= bestScore){
    		    	bestI = i;
    		    	bestL = l;
    		    	bestScore = temp;
    		    }
    		}
    	}
    	finalChoiceCard.add(handCards[bestI]);
    	finalChoiceCard.add(handCards[bestL]);
    	this.getChildren().remove(backCards[bestI]);
    	this.getChildren().remove(backCards[bestL]);
    }
	
	public void showAIHand(){
		for(int i = 0; i < 5; i++){
			backCards[i].setOpacity(0);
			this.getChildren().add(handCards[i]);
			AnchorPane.setLeftAnchor(handCards[i], i*100.0);
		}
	}
	
	public static void restartHand(){
		isLastRound = false;
		lastTimes = 2;
	}
}
