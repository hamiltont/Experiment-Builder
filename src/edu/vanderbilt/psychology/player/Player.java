package edu.vanderbilt.psychology.player;

import javax.swing.JFrame;

/**
 * The entry point for the experiment player
 * 
 * @author hamiltont
 * 
 */
public class Player {

	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() { 
				// Make sure we have nice window decorations.
				JFrame.setDefaultLookAndFeelDecorated(true);

				// Create and set up the window.
				JFrame frame = new JFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setResizable(false);

				// Display the window.
				frame.setVisible(true);
			}
		});

	}

}
