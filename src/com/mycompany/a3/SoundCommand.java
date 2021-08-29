package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class SoundCommand extends Command {
	private GameWorld target;
	
	public SoundCommand(GameWorld t) {
		super("Toggle Sound");
		target = t;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		target.toggleSound();
	}
}