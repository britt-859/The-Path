package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class AccelerateCommand extends Command {
	private GameWorld target;
	
	public AccelerateCommand(GameWorld t) {
		super("Accelerate");
		target = t;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		target.accelerate();
	}
}