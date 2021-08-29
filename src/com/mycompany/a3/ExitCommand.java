package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;

public class ExitCommand extends Command {
	private GameWorld target;
	
	public ExitCommand(GameWorld t) {
		super("Exit");
		target = t;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		exit();
	}
	
	private void exit() {
		Boolean bOK = Dialog.show("Confirm Exit", "Are you sure you want to exit?", "Yes", "No");
		if (bOK) {
			target.exit();
		}
	}
}