package edu.vanderbilt.psychology.controller.toolbarActions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;

import edu.vanderbilt.psychology.gui.main.StageWrapper;
import edu.vanderbilt.psychology.model.Experiment;

@SuppressWarnings("serial")
public class OpenExperimentAction extends AbstractAction {

	private StageWrapper stage_;

	public OpenExperimentAction(StageWrapper stage) {
		super("Open");

		stage_ = stage;
	}

	/**
	 * Opens a {@link JFileChooser} so that the user can select an
	 * {@link Experiment} to load.
	 */
	public void actionPerformed(ActionEvent e) {

		JFileChooser open = new JFileChooser();
	    // TODO Fix file filter so that only XML, i.e. compatible, files are
		// displayed.
		/*
		XMLFilter filter = new XMLFilter();
	    open.setFileFilter(filter);
	    */
	    int returnVal = open.showOpenDialog(stage_); {
	    if (returnVal == JFileChooser.APPROVE_OPTION) {
	       Experiment.loadExperiment(open.getSelectedFile());}
	    }
	}
}
