package edu.vanderbilt.psychology.player;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import edu.vanderbilt.psychology.controller.toolbarActions.XMLFilter;
import edu.vanderbilt.psychology.model.Experiment;

/**
 * The entry point for the experiment player
 * 
 * @author hamiltont
 * @contributor sethfri
 * 
 */
public class Player {

	public static void main(String[] args) {
		Experiment e = null;
		JFileChooser open = new JFileChooser();
		open.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		XMLFilter filter = new XMLFilter();
	    open.setFileFilter(filter);
	    int returnVal = open.showOpenDialog(open); {
	    if (returnVal == JFileChooser.APPROVE_OPTION) {
	        e = Experiment.loadExperiment(open.getSelectedFile());
	    } else if (returnVal == JFileChooser.CANCEL_OPTION) {
	    	e = Experiment.loadExperiment();
	    }
	    
	    final PlayerController pc = new PlayerController(e);
		System.out.print(true);

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
}
