package spoopTime;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.SSLEngineResult.Status;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.sound.sampled.LineEvent.Type;

public class SoundUtil {
	public static synchronized void playSound(String url) {
		if (url.contains(".mp3")) {
			playMP3(url);
		} else {
			playWAV(url);
		}
	}

	private static LineListener repeatListener;

	public static synchronized void playSoundOnRepeat(String url) {
		Thread t = new Thread() {
			@Override
			public void run() {
				while (true) {
					playSound(url);
				}
			}
		};
		t.start();
	}

	public static synchronized void playPlaylist(String[] urls) {
		Thread t = new Thread() {
			@Override
			public void run() {
				while (true) {
					for (String url : urls) {
						playSound(url);
					}
				}
			}
		};
		t.start();
	}

	private static MediaPlayer player;

	public static void playMP3(String url) {
		JFXPanel soundPanel = new JFXPanel();
		URL resource = SoundUtil.class.getResource("/Resources/Sounds/" + url);
		Media song = new Media(resource.toExternalForm());
		player = new MediaPlayer(song);
		player.setVolume(.5);
		player.play();
		player.setOnEndOfMedia(new Runnable() {
			@Override
			public void run() {
				synchronized (player) {
					player.notifyAll();
				}
			}
		});
		synchronized (player) {
			try {
				player.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("done");
	}

	public static void playWAV(String url) {
		Thread t = new Thread() {
			@Override
			public void run() {
				try {
					createListener();
					Clip clip = startClip(url);
					clip.addLineListener(repeatListener);
					waitForEnd();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		t.start();
	}

	private static void createListener() {
		repeatListener = new LineListener() {
			@Override
			public void update(LineEvent event) {
				if (event.getType() != Type.STOP) {
					return;
				}
				synchronized (this) {
					this.notifyAll();
				}
			}
		};
	}

	private static void waitForEnd() {
		synchronized (repeatListener) {
			try {
				repeatListener.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private static Clip startClip(String url) {
		Clip clip = null;
		try {
			clip = AudioSystem.getClip();
			InputStream inStream = SoundUtil.class.getResourceAsStream("/Resources/Sounds/" + url);
			InputStream buffStream = new BufferedInputStream(inStream);
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(buffStream);
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
