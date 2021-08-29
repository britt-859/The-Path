package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class PauseCommand extends Command {
	private Game target;
	
	public PauseCommand(Game g) {
		super("Pause");
		target = g;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		target.pause();
	}
}
