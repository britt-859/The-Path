package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;

public class AboutCommand extends Command {
	
	public AboutCommand() {
		super("About");
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		about();
	}
	
	private void about() {
		Command cOK = new Command("Ok");
		Label myLabel = new Label("Name: Brittney Scheven Course Name: CSC 133");
		Command c = Dialog.show("About", myLabel, cOK);
		if (c == cOK) {}
	}
}