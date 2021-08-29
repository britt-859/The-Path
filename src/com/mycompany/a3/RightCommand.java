package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class RightCommand extends Command {
	private GameWorld target;
	
	public RightCommand(GameWorld t) {
		super("Right");
		target = t;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		target.right();
	}
}