package com.mycompany.a3;

import java.io.InputStream;

import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;

public class BGSound implements Runnable{
	private Media m;
	
	/*
	 * Instantiate background sound
	 * @param String filename is the filename of the sound
	 */
	public BGSound(String fileName) {
		if (Display.getInstance().getCurrent() == null) {
			System.out.println("Error: create sounds after calling show.");
			System.exit(0);
		}
		while (m == null) {
			try {
				InputStream is = Display.getInstance().getResourceAsStream(getClass(), "/" + fileName);
				m = MediaManager.createMedia(is, "audio/wav", this);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void play() {
		m.play();
	}
	
	public void pause() {
		m.pause();
	}

	@Override
	public void run() {
		m.setTime(0);
		m.play();
	}
}