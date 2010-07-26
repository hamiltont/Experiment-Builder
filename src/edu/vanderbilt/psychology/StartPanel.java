/**
 * 
 */
package edu.vanderbilt.psychology;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Hamilton Turner
 * 
 */
@SuppressWarnings("serial")
public class StartPanel extends JPanel {

	public StartPanel(String instructions, final ThreeDimensionalStrategy tds) {
		super();
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		add(Box.createVerticalGlue());

		JLabel text = new JLabel(instructions);
		text.setAlignmentX(CENTER_ALIGNMENT);
		add(text);

		add(Box.createVerticalGlue());

		JButton button = new JButton("Continue");
		button.setAlignmentX(CENTER_ALIGNMENT);
		button.setPreferredSize(new Dimension(200, 75));
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				tds.resumeGame();

			}
		});
		add(button);

		add(Box.createVerticalGlue());
	}
}
