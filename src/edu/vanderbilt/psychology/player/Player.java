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

	private static final boolean DEBUG = true;

	public static void main(String[] args) {
		Experiment e = null;
		JFileChooser open = new JFileChooser();

		open.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		XMLFilter filter = new XMLFilter();
	    open.setFileFilter(filter);
	    int returnVal = open.showOpenDialog(open); {
	    if (returnVal == JFileChooser.APPROVE_OPTION) {
	        	try {
					e = Experiment.loadExperiment(open.getSelectedFile());
				} catch (RuntimeException re) {
					System.out
							.println("There was some error loading the experiment");
					re.printStackTrace();
					System.exit(1);
				}

	    } else 
	    	System.exit(0);
	    
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
				if (DEBUG == false) {
					GraphicsEnvironment ge = GraphicsEnvironment
							.getLocalGraphicsEnvironment();
					GraphicsDevice[] devices = ge.getScreenDevices();
					devices[0].setFullScreenWindow(frame);
				} else {
					frame.setSize(1400, 900);
				}

				// Display the window.
				frame.setVisible(true);

			}
		});
	}
	}
}
