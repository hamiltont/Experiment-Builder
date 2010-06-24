package edu.vanderbilt.psychology.controller.toolbarActions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import edu.vanderbilt.psychology.controller.StateManager;
import edu.vanderbilt.psychology.gui.main.MainStageWrapper;

public class ExportExperimentAction extends AbstractAction {

	private MainStageWrapper stage_;

	public ExportExperimentAction(MainStageWrapper stage) {
		super("Export");
		
		stage_ = stage;
	}

	public void actionPerformed(ActionEvent e) {
		StateManager.getInstance().saveCurrentSlide(stage_);
		StateManager.getInstance().export();
	}

	
	/** Provide a Universal ID for serialization */
	private static final long serialVersionUID = -875092351023371296L;
}
