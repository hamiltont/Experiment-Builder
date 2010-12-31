package edu.vanderbilt.psychology.player;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

import edu.vanderbilt.psychology.model.Experiment;

/**
 * The entry point for the experiment player
 * 
 * @author hamiltont
 * 
 */
public class Player {

	public static void main(String[] args) {
		Experiment e = Experiment.loadExperiment();
		System.out.print(true);

		final PlayerController pc = new PlayerController(e);

		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Create and set up the window.
				JFrame frame = new JFrame();
				frame.setUndecorated(true);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setResizable(true);
				frame.setContentPane(pc);

				// Setup full screen
				GraphicsEnvironment ge = GraphicsEnvironment
						.getLocalGraphicsEnvironment();
				GraphicsDevice[] devices = ge.getScreenDevices();
				devices[0].setFullScreenWindow(frame);

				// Display the window.
				frame.setVisible(true);

			}
		});

	}

}
