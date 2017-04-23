// File Name:   ScoreZone.java 
// Author:      Lin Chun
// Student Number: 3012218145
// Description: A ScoreZone is a area for display those cards moved out from
// the parade, and calculate the score in real-time.

import javafx.animation.FadeTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;


public class ScoreZone extends AnchorPane {

   public Card blue [] = new Card[11];
   public Card purple [] = new Card[11];
   public Card orange [] = new Card[11];
   public Card white [] = new Card[11];
   public Card green [] = new Card[11];
   public Card red [] = new Card[11];
   
   public int blueCount = 0;
   public int purpleCount = 0;
   public int orangeCount = 0;
   public int whiteCount = 0;
   public int greenCount = 0;
   public int redCount = 0;
   public int size = 0;
   public int currentScore = 0;
   Text scoreText = new Text(0 + "");
   
   
   public ScoreZone(){
   Image image = new Image("Card Images/blank.png", 80, 120, true, true);
   
   ImageView imageView1 = new ImageView(image);
   ImageView imageView2 = new ImageView(image);
   ImageView imageView3 = new ImageView(image);
   ImageView imageView4 = new ImageView(image);
   ImageView imageView5 = new ImageView(image);
   ImageView imageView6 = new ImageView(image);
   
   scoreText.setFont(new Font("Cobac",25));
   scoreText.setFill(Color.BLACK);
   
   HBox hbox = new HBox();
   hbox.getChildren().addAll(imageView1, imageView2, imageView3, imageView4, imageView5, imageView6);
   hbox.setOpacity(0.5);
   FadeTransition fadeTransition = new FadeTransition(Duration.millis(800), hbox);
   fadeTransition.setFromValue(0);
   fadeTransition.setToValue(0.5);
   fadeTransition.setDelay(Duration.millis(300));
   hbox.setOpacity(0);
   fadeTransition.play();

   AnchorPane.setLeftAnchor(scoreText, 480.0);
   
   this.getChildren().addAll(hbox, scoreText);
   this.setPrefWidth(500.0);
 
   }
   
   public void addToScoreZone(Card card, ScoreZone rival){
//	   card.setPrefSize(60.0, 90.0);
	   size++;
	   card.setOnMouseClicked(null);
	   card.setOnMouseEntered(null);
	   card.setOnMouseExited(null);
	   switch (card.getColor()) {
	case 1:
		blue[blueCount] = card;
		this.getChildren().add(card);
		AnchorPane.setLeftAnchor(card, 0.0);
		AnchorPane.setTopAnchor(card, blueCount * 20.0);
		blueCount++;
		System.out.println("blue: " + blueCount);
		
		break;
	case 2:
		purple[purpleCount] = card;
		this.getChildren().add(card);
		AnchorPane.setLeftAnchor(card, 80.0);
		AnchorPane.setTopAnchor(card, purpleCount * 20.0);
		purpleCount++;
		System.out.println("purple: " + purpleCount);
		
		break;
	case 3:
		orange[orangeCount] = card;
		this.getChildren().add(card);
		AnchorPane.setLeftAnchor(card, 160.0);
		AnchorPane.setTopAnchor(card, orangeCount * 20.0);
		orangeCount++;
		System.out.println("orange: " + orangeCount);
		break;
	case 4:
		white[whiteCount] = card;
		this.getChildren().add(card);
		AnchorPane.setLeftAnchor(card, 240.0);
		AnchorPane.setTopAnchor(card, whiteCount * 20.0);
		whiteCount++;
		System.out.println("white: " + whiteCount);
		
		break;
	case 5:
		green[greenCount] = card;
		this.getChildren().add(card);
		AnchorPane.setLeftAnchor(card, 320.0);
		AnchorPane.setTopAnchor(card, greenCount * 20.0);
		greenCount++;
		System.out.println("green: " + greenCount);

		break;
	case 6:
		red[redCount] = card;
		this.getChildren().add(card);
		AnchorPane.setLeftAnchor(card, 400.0);
		AnchorPane.setTopAnchor(card, redCount * 20.0);
		redCount++;
		System.out.println("red: " + redCount);
		break;
	default:
		break;
	}
	   currentScore = countScore(rival);
	   rival.currentScore = countRivalScore(rival);
	   scoreText.setText(currentScore + "");
	   rival.scoreText.setText(rival.currentScore + "");
   }
   
   public int countScore(ScoreZone rival){
	   for(int i = 0;i < blueCount;i++)
		   blue[i].cardImageView.setOpacity(1);
	   for(int i = 0;i < purpleCount;i++)
		   purple[i].cardImageView.setOpacity(1); 
	   for(int i = 0;i < orangeCount;i++)
		   orange[i].cardImageView.setOpacity(1); 
	   for(int i = 0;i < whiteCount;i++)
		   white[i].cardImageView.setOpacity(1);
	   for(int i = 0;i < greenCount;i++)
		   green[i].cardImageView.setOpacity(1);
	   for(int i = 0;i < redCount;i++)
		   red[i].cardImageView.setOpacity(1);
	   
	   int tempScore = 0;
	   if(blueCount - 2 >= rival.blueCount){
		   tempScore = tempScore + blueCount;
		   for(int i = 0; i < blueCount; i++){
			   blue[i].cardImageView.setOpacity(0.4);
		   }
	   }
	   else{
		   for (int i = 0; i < blueCount; i++)
			   tempScore = tempScore + blue[i].getNumber();
	   }
	   if(purpleCount - 2 >= rival.purpleCount){
		   tempScore = tempScore + purpleCount;
		   for(int i = 0; i < purpleCount; i++){
			   purple[i].cardImageView.setOpacity(0.4);
		   }
	   }
	   else{
		   for (int i = 0; i < purpleCount; i++)
			   tempScore = tempScore + purple[i].getNumber();
	   }
	   if(orangeCount - 2 >= rival.orangeCount){
		   tempScore = tempScore + orangeCount;
		   for(int i = 0; i < orangeCount; i++){
			   orange[i].cardImageView.setOpacity(0.4);
		   }
	   }
	   else{
		   for (int i = 0; i < orangeCount; i++)
			   tempScore = tempScore + orange[i].getNumber();
	   }
	   if(whiteCount - 2 >= rival.whiteCount){
		   tempScore = tempScore + whiteCount;
		   for(int i = 0; i < whiteCount; i++){
			   white[i].cardImageView.setOpacity(0.4);
		   }
	   }
	   else{
		   for (int i = 0; i < whiteCount; i++)
			   tempScore = tempScore + white[i].getNumber();
	   }
	   if(greenCount - 2 >= rival.greenCount){
		   tempScore = tempScore + greenCount;
		   for(int i = 0; i < greenCount; i++){
			   green[i].cardImageView.setOpacity(0.4);
		   }
	   }
	   else{
		   for (int i = 0; i < greenCount; i++)
			   tempScore = tempScore + green[i].getNumber();
	   }
	   if(redCount - 2 >= rival.redCount){
		   tempScore = tempScore + redCount;
		   for(int i = 0; i < redCount; i++){
			   red[i].cardImageView.setOpacity(0.4);
		   }
	   }
	   else{
		   for (int i = 0; i < redCount; i++)
			   tempScore = tempScore + red[i].getNumber();
	   }
	   
	   return tempScore;
   }
   
   public int countRivalScore(ScoreZone rival){
	   for(int i = 0;i < rival.blueCount;i++)
		   rival.blue[i].cardImageView.setOpacity(1);
	   for(int i = 0;i < rival.purpleCount;i++)
		   rival.purple[i].cardImageView.setOpacity(1); 
	   for(int i = 0;i < rival.orangeCount;i++)
		   rival.orange[i].cardImageView.setOpacity(1); 
	   for(int i = 0;i < rival.whiteCount;i++)
		   rival.white[i].cardImageView.setOpacity(1);
	   for(int i = 0;i < rival.greenCount;i++)
		   rival.green[i].cardImageView.setOpacity(1);
	   for(int i = 0;i < rival.redCount;i++)
		   rival.red[i].cardImageView.setOpacity(1);
	   
	   int tempScore = 0;
	   if(rival.blueCount - 2 >= blueCount){
		   tempScore = tempScore + rival.blueCount;
		   for(int i = 0; i < rival.blueCount; i++){
			   rival.blue[i].cardImageView.setOpacity(0.4);
		   }
	   }
	   else{
		   for (int i = 0; i < rival.blueCount; i++)
			   tempScore = tempScore + rival.blue[i].getNumber();
	   }
	   if(rival.purpleCount - 2 >= purpleCount){
		   tempScore = tempScore + rival.purpleCount;
		   for(int i = 0; i < rival.purpleCount; i++){
			   rival.purple[i].cardImageView.setOpacity(0.4);
		   }
	   }
	   else{
		   for (int i = 0; i < rival.purpleCount; i++)
			   tempScore = tempScore + rival.purple[i].getNumber();
	   }
	   if(rival.orangeCount - 2 >= orangeCount){
		   tempScore = tempScore + rival.orangeCount;
		   for(int i = 0; i < rival.orangeCount; i++){
			   rival.orange[i].cardImageView.setOpacity(0.4);
		   }
	   }
	   else{
		   for (int i = 0; i < rival.orangeCount; i++)
			   tempScore = tempScore + rival.orange[i].getNumber();
	   }
	   if(rival.whiteCount - 2 >= whiteCount){
		   tempScore = tempScore + rival.whiteCount;
		   for(int i = 0; i < rival.whiteCount; i++){
			   rival.white[i].cardImageView.setOpacity(0.4);
		   }
	   }
	   else{
		   for (int i = 0; i < rival.whiteCount; i++)
			   tempScore = tempScore + rival.white[i].getNumber();
	   }
	   if(rival.greenCount - 2 >= greenCount){
		   tempScore = tempScore + rival.greenCount;
		   for(int i = 0; i < rival.greenCount; i++){
			   rival.green[i].cardImageView.setOpacity(0.4);
		   }
	   }
	   else{
		   for (int i = 0; i < rival.greenCount; i++)
			   tempScore = tempScore + rival.green[i].getNumber();
	   }
	   if(rival.redCount - 2 >= redCount){
		   tempScore = tempScore + rival.redCount;
		   for(int i = 0; i < rival.redCount; i++){
			   rival.red[i].cardImageView.setOpacity(0.4);
		   }
	   }
	   else{
		   for (int i = 0; i < rival.redCount; i++)
			   tempScore = tempScore + rival.red[i].getNumber();
	   }
	   
	   return tempScore;
   }
   
   public boolean isColorFull(){
	   if(blueCount != 0 && purpleCount != 0 && orangeCount != 0 && whiteCount != 0 && greenCount != 0 && redCount != 0) {
		   return true;
	   }
	   else
		   return false;
   }
}
