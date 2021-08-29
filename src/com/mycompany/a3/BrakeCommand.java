package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class BrakeCommand extends Command {
	private GameWorld target;
	
	public BrakeCommand(GameWorld t) {
		super("Brake");
		target = t;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		target.brake();
	}
}
