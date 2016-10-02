package spoopTime;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SetUpPanel extends JPanel {
	private static final int BUTTON_WIDTH = 150;
	private static final int BUTTON_HEIGHT = 50;

	public SetUpPanel() {
		setLayout(null);
		createButtons();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, 500, 500);
		g.setColor(Color.white);
		g.drawString("DIFFICULTY", 220, 300);
		g.fillOval(325, 310, 25, 25);
		g.fillOval(150, 310, 25, 25);
		g.setColor(Color.black);
		g.fillOval(155, 310, 25, 25);
	}
	
	private void createButtons() {
		JButton startButton = new JButton("START");
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Core.setupMode = false;
			}
		});
		startButton.setBounds(175, 225, BUTTON_WIDTH, BUTTON_HEIGHT);
		add(startButton);
		
		JSlider slider = new JSlider(0, 100);
		slider.setBounds(175, 300, BUTTON_WIDTH, BUTTON_HEIGHT);
		slider.setBackground(Color.black);
		add(slider);
		slider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				Core.difficulty = slider.getValue();
				
			}
			
		});
	}

}
