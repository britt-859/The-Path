package com.mycompany.a3;

import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.GridLayout;

public class ScoreView extends Container implements Observer {
	private Label livesLabel;
	private Label clockLabel;
	private Label flagLabel;
	private Label foodLabel;
	private Label healthLabel;
	private Label soundLabel;
	
	/*
	 * Construct a score view with labels.
	 */
	public ScoreView() {
		livesLabel = new Label("Lives left: ");
		clockLabel = new Label("Time: ");
		flagLabel = new Label("Last Flag Reached: ");
		foodLabel = new Label("Food Level: ");
		healthLabel = new Label("health Level: ");
		soundLabel = new Label("Sound: ");
		
		livesLabel.getAllStyles().setFgColor(ColorUtil.BLACK);
		clockLabel.getAllStyles().setFgColor(ColorUtil.BLACK);
		flagLabel.getAllStyles().setFgColor(ColorUtil.BLACK);
		foodLabel.getAllStyles().setFgColor(ColorUtil.BLACK);
		healthLabel.getAllStyles().setFgColor(ColorUtil.BLACK);
		soundLabel.getAllStyles().setFgColor(ColorUtil.BLACK);
		
		//GridLayout 
		this.setLayout(new GridLayout(1, 6));
		
		this.add(clockLabel);
		this.add(livesLabel);
		this.add(flagLabel);
		this.add(foodLabel);
		this.add(healthLabel);
		this.add(soundLabel);
	}

	@Override
	public void update(Observable observable, Object data) {
		livesLabel.setText("Lives left: " + ((GameWorld) observable).getLives());
		clockLabel.setText("Time: " + ((GameWorld) observable).getClock());
		flagLabel.setText("Last Flag Reached: " + ((GameWorld) observable).getLastFlagReached());
		foodLabel.setText("Food Level: " + ((GameWorld) observable).getFoodLevel());
		healthLabel.setText("Health Level: " + ((GameWorld) observable).getHealthLevel());
		if (((GameWorld) observable).getSound()) {
			soundLabel.setText("Sound: ON");
		} else {
			soundLabel.setText("Sound: OFF");
		}
	}
}