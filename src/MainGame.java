// File Name:   MainGame.java 
// Author:      Lin Chun
// Student Number: 3012218145
// Description: Parade is a board card game for 2 player. User and AI both have
// 5 cards in hand, and play the card into the parade for the goal of winning
// with the least points. The program is font of Hand class, Card, Deck, AI,
// Parade and ScoreZone class. The theme of the parade is SHELL PARADE on the beach!
// Special thanks to my girlfriend, Calmy, for painting the gorgeous background, and my best
// friend, Irene, for her beautiful hand-drawing shells.
//
// Future Improvement: There's still some bugs: 1. scoreZone sometimes can't
// update majority situation right away, sometimes need to wait to next round
// to see update. 2. The first card in AI's hand cards, if it's played in last
// round, at the same time it's the default bestScore index, can't find out 
// how to fix this bug. 3. Restart button, game start UI and game over UI need to
// be done. 
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.text.*;
import javafx.scene.SceneBuilder;
import javafx.scene.paint.Color;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;


public class MainGame extends Application{

	public static void main(String[] args) {
		MainGame.launch(args);

	}
    public static boolean isDebug = false;//value for debug button
	@Override
	public void start(final Stage primaryStage) throws Exception {
		// initialize
		Deck deck = Deck.getInstance();
		deck.initialDeck();
		final AnchorPane anchorPane = new AnchorPane();
		Parade parade = new Parade();
		ScoreZone playerScoreZone = new ScoreZone();
		ScoreZone rivalScoreZone = new ScoreZone();
        
		Hand playerHand = new Hand();
        final Hand rivalHand = new Hand();
        AI computer = new AI(rivalHand, rivalScoreZone);
        playerHand.initialPlayerHand(parade, playerScoreZone, computer);
		rivalHand.initialRivalHand(parade);
		
		//Deck's remaining cards, bind with deck.remainingText
		Text remainingCards = new Text();
		remainingCards.setFont(new Font(30));
		remainingCards.setFill(Color.WHITE);
		remainingCards.textProperty().bind(deck.remainingText.textProperty());
		
		//Deck's image
		Image backImage = new Image("Card Images/back_3.png" , 80, 120, true, true);
		final ImageView backImageView = new ImageView();
		backImageView.setImage(backImage);
		
		Image background = new Image("Card Images/background.jpg", 1024, 768, true, true);
		ImageView backgroundImageView = new ImageView(background);
		
		anchorPane.getChildren().add(backgroundImageView);
		anchorPane.getChildren().add(playerHand);
		anchorPane.getChildren().add(rivalHand);
		anchorPane.getChildren().add(parade);
		anchorPane.getChildren().add(backImageView);
		anchorPane.getChildren().add(remainingCards);
		anchorPane.getChildren().add(playerScoreZone);
		anchorPane.getChildren().add(rivalScoreZone);
		AnchorPane.setBottomAnchor(playerHand,50.0);
		AnchorPane.setLeftAnchor(playerHand, 25.0);
		AnchorPane.setTopAnchor(rivalHand, 80.0);
		AnchorPane.setLeftAnchor(rivalHand, 25.0);
		AnchorPane.setLeftAnchor(parade, 120.0);
		AnchorPane.setTopAnchor(parade, 309.0);
		AnchorPane.setTopAnchor(backImageView, 309.0);
		AnchorPane.setLeftAnchor(backImageView, 10.0);
		AnchorPane.setTopAnchor(remainingCards, 309.0);
		AnchorPane.setLeftAnchor(remainingCards, 10.0);
		AnchorPane.setRightAnchor(playerScoreZone, 10.0);
		AnchorPane.setTopAnchor(playerScoreZone, 500.0);
		AnchorPane.setRightAnchor(rivalScoreZone, 10.0);
		AnchorPane.setTopAnchor(rivalScoreZone, 25.0);
	
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(800), backImageView);
		fadeTransition.setFromValue(0);
		fadeTransition.setToValue(1);
		fadeTransition.setDelay(Duration.millis(300));
		backImageView.setOpacity(0);
		fadeTransition.play();
		
		FadeTransition fade2 = new FadeTransition(Duration.millis(800), remainingCards);
		fade2.setFromValue(0);
		fade2.setToValue(1);
		fade2.setDelay(Duration.millis(300));
		remainingCards.setOpacity(0);
		fade2.play();
		
		//btn is for debugging
		Button btn = new Button();
		btn.setText("debug");
		btn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent actEvt){
				rivalHand.showAIHand();
				isDebug = true;
			}
		});
		anchorPane.getChildren().add(btn);
		
		primaryStage.setTitle("Parade Game");
		primaryStage.setScene(SceneBuilder.create().width(1024).height(768)
				.root(anchorPane).build());
		primaryStage.show();
		
	}
	
}
