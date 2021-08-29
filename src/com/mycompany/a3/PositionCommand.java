package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class PositionCommand extends Command {
	private MapView target;
	
	public PositionCommand(MapView mv) {
		super("Position");
		target = mv;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		target.positionClicked();
	}
}
