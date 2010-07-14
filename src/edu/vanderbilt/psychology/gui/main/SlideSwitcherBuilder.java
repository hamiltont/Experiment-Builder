package edu.vanderbilt.psychology.gui.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JPanel;

/**
 * Creates a {@link JPanel} that will act as the slide switcher. Also creates a
 * {@link ComponentListener} that listens for size or location changes in the
 * {@link StageWrapper} and resizes or moves the slide switcher panel
 * appropriately
 * 
 * @author Hamilton Turner
 * 
 */
public class SlideSwitcherBuilder {

	private static final int SWITCHER_MIN_HEIGHT = 200;

	private SlideSwitcherBuilder() {
	}

	public static JPanel build(StageWrapper stage) {
		JPanel panel = new JPanel();
		panel.setBackground(Color.green);
		
		Dimension parentSize = stage.getSize();
		System.out.println("Building SlideSwitcher");
		System.out.println("StageWrapper has size " + stage.getSize());
		
		Point topLeft = new Point(0,parentSize.height - SWITCHER_MIN_HEIGHT);
		panel.setLocation(topLeft);
		

		StageListener stageListener = new StageListener(panel);
		stage.addComponentListener(stageListener);

		return panel;
	}

	private static class StageListener extends ComponentAdapter {
		private JPanel slideSwitcherPanel_;

		public StageListener(JPanel slideSwitcherPanel) {
			slideSwitcherPanel_ = slideSwitcherPanel;
		}

		/**
		 * As long as there is room to display the {@link SlideSwitcherBuilder},
		 * it should be displayed. It will always take up the width of the
		 * {@link StageWrapper} and a height of
		 * {@link SlideSwitcherBuilder#SWITCHER_MIN_HEIGHT}. If there is no room
		 * to display the switcher, it should be hidden
		 */
		@Override
		public void componentResized(ComponentEvent e) {
			System.out.println("Resized to " + e.getComponent().getSize());
			JPanel s = slideSwitcherPanel_;
			Dimension newSize = new Dimension(e.getComponent().getSize());

			// If visible, resize or make invisible
			if (s.isVisible()) {
				if (newSize.height < SWITCHER_MIN_HEIGHT)
					s.setVisible(false);
				else {
					newSize.height = SWITCHER_MIN_HEIGHT;
					s.setSize(newSize);
				}
			}
			// If invisible, only resize if we should be made visible
			else if (newSize.height >= SWITCHER_MIN_HEIGHT) {
				newSize.height = SWITCHER_MIN_HEIGHT;
				s.setSize(newSize);
				s.setVisible(true);
			}

		}
	}

	/** Provide a Universal ID for serialization */
	private static final long serialVersionUID = 4877702530002030964L;

}
