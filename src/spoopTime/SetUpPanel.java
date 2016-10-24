package spoopTime;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SetUpPanel extends JPanel {
	private static final int BUTTON_WIDTH = 150;
	private static final int BUTTON_HEIGHT = 50;

	public SetUpPanel() {
		setLayout(null);
		createControls();
	}
	
	private void createControls() {
		createStartButton();
		createSettingsButton();
		
	}
	
	private void createStartButton() {
		JButton startButton = new JButton("START");
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Core.setupMode = false;
			}
		});
		startButton.setBounds(175, 225, BUTTON_WIDTH, BUTTON_HEIGHT);
		startButton.setBackground(Display.ORANGE);
		startButton.setBorder(new LineBorder(Display.BORDER_ORANGE, 4));
		add(startButton);
	}
	
	private void createSettingsButton() {
		JButton settingsButton = new JButton("Settings");
		JPanel panel = this;
		settingsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panel.setVisible(false);
				JPanel settingsPanel = new SettingsPanel();
				Core.setupFrame.add(settingsPanel);
				Thread t = new Thread() {
					@Override
					public void run() {
						synchronized (settingsPanel) {
							try {
								settingsPanel.wait();
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						Core.setupFrame.remove(settingsPanel);
						panel.setVisible(true);
					}
				};
				t.start();
			}
		});
		settingsButton.setBounds(175, 275, BUTTON_WIDTH, BUTTON_HEIGHT);
		settingsButton.setBackground(Display.ORANGE);
		settingsButton.setBorder(new LineBorder(Display.BORDER_ORANGE, 4));
		add(settingsButton);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, 500, 500);
	}
}
