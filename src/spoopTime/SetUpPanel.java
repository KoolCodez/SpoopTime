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
	
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, 500, 500);
		g.setColor(Display.ORANGE);
		g.drawString("DIFFICULTY: " + Core.difficulty, 183, 300);
		drawMoons(g);
		g.setColor(Display.ORANGE);
		g.drawString("WORLD SIZE: " + World.getSize(), 183, 375);
		g.drawString("WINDOW SIZE: " + Display.SCALE * 1000, 183, 450);
	}
	
	private void drawMoons(Graphics g) {
		g.setColor(Color.white);
		g.fillOval(325, 310, 25, 25);
		g.fillOval(150, 310, 25, 25);
		g.setColor(Color.black);
		g.fillOval(155, 310, 25, 25);
	}
	
	private void createControls() {
		createStartButton();
		createDifficultySlider();
		createSizeSlider();
		createScaleSlider();
		
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
	
	private void createDifficultySlider() {
		JSlider diffSlider = new JSlider(10, 110);
		diffSlider.setValue(60);
		diffSlider.setBounds(175, 300, BUTTON_WIDTH, BUTTON_HEIGHT);
		diffSlider.setBackground(Color.black);
		add(diffSlider);
		diffSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				Core.difficulty = diffSlider.getValue();
				
			}
			
		});
		diffSlider.setForeground(Color.ORANGE);
	}
	
	private void createSizeSlider() {
		JSlider sizeSlider = new JSlider(1000, 11000);
		sizeSlider.setBounds(175, 375, BUTTON_WIDTH, BUTTON_HEIGHT);
		sizeSlider.setBackground(Color.black);
		add(sizeSlider);
		sizeSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				World.setSize(sizeSlider.getValue());
			}
			
		});
		sizeSlider.setForeground(Color.ORANGE);
	}
	
	private void createScaleSlider() {
		JSlider scaleSlider = new JSlider(100, 2000);
		scaleSlider.setBounds(175, 450, BUTTON_WIDTH, BUTTON_HEIGHT);
		scaleSlider.setBackground(Color.black);
		add(scaleSlider);
		scaleSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				Display.SCALE = scaleSlider.getValue() / 1000.0;
			}
		});
	}

}
