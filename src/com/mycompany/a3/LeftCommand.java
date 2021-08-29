package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class LeftCommand extends Command {
	private GameWorld target;
	
	public LeftCommand(GameWorld t) {
		super("Left");
		target = t;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		target.left();
	}
}