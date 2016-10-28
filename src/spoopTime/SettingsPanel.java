package spoopTime;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SettingsPanel extends JPanel {
	private static final int BUTTON_WIDTH = 150;
	private static final int BUTTON_HEIGHT = 50;
	
	private JSlider difficulty;
	private JSlider worldSize;
	private JSlider windowSize;
	private JButton back;
	private JCheckBox fastGrass;
	private JCheckBox fastWalls;
	private JCheckBox sound;
	
	public SettingsPanel() {
		setLayout(null);
		createDifficultySlider();
		createWorldSizeSlider();
		createWindowSizeSlider();
		createFastGrassBox();
		createFastWallsBox();
		createSoundBox();
		createBackButton();
	}
	
	private void createDifficultySlider() {
		difficulty = new JSlider(10, 110);
		difficulty.setValue((int) Settings.difficulty);
		difficulty.setBounds(175, 50, BUTTON_WIDTH, BUTTON_HEIGHT);
		difficulty.setBackground(Color.black);
		difficulty.setForeground(Color.ORANGE);
		difficulty.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				Settings.difficulty = difficulty.getValue();
				
			}
			
		});
		add(difficulty);
	}
	
	private void createWorldSizeSlider() {
		worldSize = new JSlider(1000, 6000);
		worldSize.setValue(World.getSize());
		worldSize.setBounds(175, 125, BUTTON_WIDTH, BUTTON_HEIGHT);
		worldSize.setBackground(Color.black);
		worldSize.setForeground(Color.ORANGE);
		worldSize.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				World.setSize(worldSize.getValue());
			}
			
		});
		add(worldSize);
	}
	
	private void createWindowSizeSlider() {
		windowSize = new JSlider(100, 2000);
		windowSize.setValue((int) (Display.SCALE * 1000));
		windowSize.setBounds(175, 200, BUTTON_WIDTH, BUTTON_HEIGHT);
		windowSize.setBackground(Color.black);
		windowSize.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				Display.SCALE = windowSize.getValue() / 1000.0;
			}
		});
		add(windowSize);
	}
	
	private void createBackButton() {
		back = new JButton("Back");
		JPanel panel = this;
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				synchronized (panel) {
					panel.notifyAll();
				}
			}
		});
		back.setBounds(175, 425, BUTTON_WIDTH, BUTTON_HEIGHT);
		back.setBackground(Display.ORANGE);
		back.setBorder(new LineBorder(Display.BORDER_ORANGE, 4));
		add(back);
	}
	
	private void createFastGrassBox() {
		fastGrass = new JCheckBox("Fast Grass");
		fastGrass.setSelected(Settings.fastGrass);
		fastGrass.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Settings.fastGrass = fastGrass.isSelected();
				
			}
		});
		fastGrass.setBounds(175, 250, BUTTON_WIDTH, BUTTON_HEIGHT);
		fastGrass.setForeground(Display.ORANGE);
		fastGrass.setBackground(Color.BLACK);
		fastGrass.setBorder(new LineBorder(Display.BORDER_ORANGE, 4));
		add(fastGrass);
	}
	
	private void createFastWallsBox() {
		fastWalls = new JCheckBox("Fast Walls");
		fastWalls.setSelected(Settings.fastWalls);
		fastWalls.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Settings.fastWalls = fastWalls.isSelected();
			}
		});
		
		fastWalls.setBounds(175, 300, BUTTON_WIDTH, BUTTON_HEIGHT);
		fastWalls.setBackground(Color.BLACK);
		fastWalls.setForeground(Display.ORANGE);
		fastWalls.setBorder(new LineBorder(Display.BORDER_ORANGE, 4));
		add(fastWalls);
	}
	
	private void createSoundBox() {
		sound = new JCheckBox("Sound");
		sound.setSelected(Settings.sound);
		sound.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Settings.sound = sound.isSelected();
			}
		});
		
		sound.setBounds(175, 350, BUTTON_WIDTH, BUTTON_HEIGHT);
		sound.setBackground(Color.BLACK);
		sound.setForeground(Display.ORANGE);
		sound.setBorder(new LineBorder(Display.BORDER_ORANGE, 4));
		add(sound);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, 500, 500);
		g.setColor(Display.ORANGE);
		g.drawString("DIFFICULTY: " + Settings.difficulty, 183, 50);
		drawMoons(g);
		g.setColor(Display.ORANGE);
		g.drawString("WORLD SIZE: " + World.getSize(), 183, 125);
		g.drawString("WINDOW SIZE: " + Display.SCALE * 1000, 183, 200);
	}
	
	private void drawMoons(Graphics g) {
		g.setColor(Color.white);
		g.fillOval(325, 60, 25, 25);
		g.fillOval(150, 60, 25, 25);
		g.setColor(Color.black);
		g.fillOval(155, 60, 25, 25);
	}
}
