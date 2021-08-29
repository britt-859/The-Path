package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;

public class HelpCommand extends Command {
	
	public HelpCommand() {
		super("Help");
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		help();
	}
	
	private void help() {
		Command cOK = new Command("OK");
		Label myLabel = new Label("Key Commands: (Accelerate - a) (Brake - b) "
				+ "(Left - l) (Right - r) (Food Station - f) (Spider Collision - g) (Clock tick - t)");
		Command c = Dialog.show("Help", myLabel, cOK);
		if (c == cOK) {}
	}
}