// File Name:   Card.java 
// Author:      Lin Chun
// Student Number: 3012218145
// Description: A card has its color and number (value), and the specific
// card image for each card. Include Card clicked, entered and exited event.

import javafx.event.ActionEvent;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Card extends AnchorPane {

	private int number;
	private int color;
	boolean isEmpty;
	public ImageView cardImageView;
	Deck deck = Deck.getInstance();

	public Card() {
		this.number = -1;
		this.color = -1;
		this.isEmpty = true;
	}

	public Card(int col, int num) {
		this.number = num;
		this.color = col;
		String tempNum;// number of card's image
		if (num < 10)
			tempNum = "0" + Integer.toString(num);
		else
			tempNum = Integer.toString(num);

		String imageNO = Integer.toString(this.color) + tempNum;
		String imageURL = "Card Images/" + imageNO + "_3.png";
		Image cardImage = new Image(imageURL, 80, 120, true, true);
		cardImageView = new ImageView();
		cardImageView.setImage(cardImage);

		this.isEmpty = false;
		this.setPrefSize(80, 120);
		this.getChildren().add(cardImageView);
	}

	public void setNumber(int num) {
		this.number = num;
	}

	public int getNumber() {
		return number;
	}

	public void setColor(int col) {
		this.color = col;
	}

	public int getColor() {
		return color;
	}

	public void mouseEntered(final Parade parade) {
		// transition: mouse move by , card jump out
		this.setOnMouseEntered(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent mseEvt) {
				Card tempCard = (Card) mseEvt.getSource();
				TranslateTransition transition = new TranslateTransition(
						Duration.millis(100), tempCard);
				transition.setByY(-20);
				transition.play();
				parade.predict(tempCard);
			}
		});

		this.setOnMouseExited(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent mseEvt) {
				Card tempCard = (Card) mseEvt.getSource();
				TranslateTransition transition = new TranslateTransition(
						Duration.millis(100), tempCard);
				transition.setToY(tempCard.getLayoutY());
				transition.play();
				parade.disPredict();
			}
		});
	}

	public void mouseClicked(final Hand hand, final Parade parade,
			final ScoreZone scoreZone, final AI computer) {
		this.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent mseEvt) {
				parade.disPredict();
				final Card tempCard = (Card) mseEvt.getSource();
				// remove mouse event on card
				tempCard.setOnMouseEntered(null);
				tempCard.setOnMouseExited(null);
				parade.moveParade(tempCard, scoreZone, computer.aiScoreZone);
				System.out.println("player's score: " + scoreZone.currentScore);
				// if it's final round situation
				if (scoreZone.isColorFull()
						|| computer.aiScoreZone.isColorFull()
						|| Deck.remainingCards == 0) {
					if (Hand.isLastRound == false) {// just enter final
													// round,haven't play card
						Hand.isLastRound = true;
						System.out.println("FINAL ROUND!");
						Text text = new Text();
						text.setText("FINAL ROUND!");
						text.setFont(new Font("Cobac", 24.0));
						hand.getChildren().add(text);
						AnchorPane.setTopAnchor(text, 150.0);
					} else {// has played at least one card
						Hand.lastTimes--;
						hand.getChildren().remove(tempCard);
						if (Hand.lastTimes == 0) {// two sides have both played
													// the last cards out of 5
							hand.finalChoice(scoreZone, computer);
						}
					}
				}

				if ((Hand.isLastRound == true && Hand.lastTimes == 2)
						|| Hand.isLastRound == false)
					hand.refreshHandCard(tempCard, parade, scoreZone, computer);

				if (Hand.lastTimes != 0) {
					computer.play(parade, hand, scoreZone);
					System.out.println("CPU's score: "
							+ computer.aiScoreZone.currentScore);
				}
			}
		});
	}

	public void finalMouseClick(final Hand playerHand,
			final ScoreZone userScore, final AI computer) {
		this.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent mseEvt) {
				Card tempCard = (Card) mseEvt.getSource();
				tempCard.setOpacity(0.5);
				tempCard.setOnMouseClicked(null);
				if (tempCard != null)
					playerHand.finalChoiceCard.add(tempCard);
				if (playerHand.finalChoiceCard.size() == 2) {// click 2 times
					userScore.addToScoreZone(playerHand.finalChoiceCard.get(0),
							computer.aiScoreZone);
					userScore.addToScoreZone(playerHand.finalChoiceCard.get(1),
							computer.aiScoreZone);
					System.out.println("user final score: "
							+ userScore.currentScore);
					computer.aiHand.aiChoice(computer, userScore);
					computer.aiScoreZone.addToScoreZone(
							computer.aiHand.finalChoiceCard.get(0), userScore);
					computer.aiScoreZone.addToScoreZone(
							computer.aiHand.finalChoiceCard.get(1), userScore);
					System.out.println("AI final score: "
							+ computer.aiScoreZone.currentScore);
					for (int i = 0; i < 5
							&& playerHand.handCards[i].isEmpty == false; i++) {
						playerHand.handCards[i].setOnMouseEntered(null);
						playerHand.handCards[i].setOnMouseClicked(null);
					}
					// final round end, scoring result window going to show
					String string;
					if (userScore.currentScore < computer.aiScoreZone.currentScore) {
						string = new String("You Win!");
					} else if (userScore.currentScore > computer.aiScoreZone.currentScore)
						string = new String("You Lose!");
					else if (userScore.size > computer.aiScoreZone.size)
						string = new String("You Lose!");
					else {
						string = new String("You Win!");
					}
					Text text = new Text();
					text.setText("Your Score: " + userScore.currentScore
							+ "\nCPU's Score: "
							+ computer.aiScoreZone.currentScore + "\n" + string);
					text.setFont(new Font("Cobac", 24.0));
					StackPane stackPane = new StackPane();
					stackPane.getChildren().add(text);
					Stage stage = new Stage();
					stage.setScene(new Scene(stackPane, 200, 200));
					stage.show();
				}
			}

		});
	}
}
