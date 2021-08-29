package com.mycompany.a3;

import java.io.InputStream;

import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;

public class Sound {
private Media m;
	
	/*
	 * Instantiate sound from given file name
	 * @param String filename is name of the sound file.
	 */
	public Sound(String fileName) {
		if (Display.getInstance().getCurrent() == null) {
			System.out.println("Error: create sounds after calling show.");
			System.exit(0);
		}
		while(m == null) {
			try {
				InputStream is = Display.getInstance().getResourceAsStream(getClass(), "/" + fileName);
				m = MediaManager.createMedia(is, "audio/wav");
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void play() {
		m.setTime(0);
		m.play();
	}
}
