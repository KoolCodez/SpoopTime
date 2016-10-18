package spoopTime;

import java.io.BufferedInputStream;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.LineEvent.Type;

public class SoundUtil {
	public static synchronized void playSound(String url) {
		Thread t = new Thread() {
			@Override
			public void run() {
				try {
					Clip clip = AudioSystem.getClip();
					InputStream inStream = SoundUtil.class.getResourceAsStream("/Resources/Sounds/" + url);
					InputStream buffStream = new BufferedInputStream(inStream);
					AudioInputStream inputStream = AudioSystem
							.getAudioInputStream(buffStream);
					
					clip.open(inputStream);
					FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
					gainControl.setValue(-30.0f);
					clip.start();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		t.start();
	}

	public static synchronized void playSoundOnRepeat(String url) {
		Thread t = new Thread() {
			@Override
			public void run() {
				try {
					Clip clip = startClip(url);
					LineListener listener = new LineListener() {
						@Override
						public void update(LineEvent event) {
							if (event.getType() != Type.STOP) {
								return;
							}
							playSoundOnRepeat(url);
						}
					};
					clip.addLineListener(listener);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		t.start();
	}

	private static Clip startClip(String url) {
		Clip clip = null;
		try {
			clip = AudioSystem.getClip();
			InputStream inStream = SoundUtil.class.getResourceAsStream("/Resources/Sounds/" + url);
			InputStream buffStream = new BufferedInputStream(inStream);
			AudioInputStream inputStream = AudioSystem
					.getAudioInputStream(buffStream);
			clip.open(inputStream);
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(-30.0f);
			clip.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return clip;
	}
}
