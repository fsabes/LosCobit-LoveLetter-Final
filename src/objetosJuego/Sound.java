package objetosJuego;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {
	Clip clip;
	static public Sound buttonSound;
	static public Sound playCard;
	static public Sound menuSoundtrack;
	static public Sound gameSoundtrack;

	public static void initSounds() {
		try {
			buttonSound = new Sound("Sounds/soundButton.wav");
			playCard = new Sound("Sounds/cardPlace2.wav");
			menuSoundtrack = new Sound("Sounds/soundMenu.wav",true);
			gameSoundtrack = new Sound("Sounds/gameSound.wav");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Sound(String path) throws Exception {
		AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File(path));
		clip = AudioSystem.getClip();
		clip.open(audioIn);

	}

	public Sound(String path, boolean canLoop) throws Exception {
		AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File(path));
		clip = AudioSystem.getClip();
		clip.open(audioIn);
		if (canLoop)
			clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

	public void play() {
		clip.stop();
		clip.flush();
		clip.setMicrosecondPosition(0);
		clip.start();

	}

	public void playText() {
		clip.stop();
		clip.flush();
		clip.setMicrosecondPosition(0);
		clip.start();

	}

	public void stop() {
		clip.stop();
	}

	public void setVolume(float volume) {
		if (volume < 0f || volume > 1f)
			throw new IllegalArgumentException("Volume not valid: " + volume);
		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		gainControl.setValue(20f * (float) Math.log10(volume));
	}
}
