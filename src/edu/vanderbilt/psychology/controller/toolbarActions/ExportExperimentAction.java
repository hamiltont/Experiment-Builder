package edu.vanderbilt.psychology.controller.toolbarActions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import edu.vanderbilt.psychology.controller.StageWrapperController;
import edu.vanderbilt.psychology.gui.main.StageWrapper;
import edu.vanderbilt.psychology.model.BuilderState;
import edu.vanderbilt.psychology.model.Slide;

public class ExportExperimentAction extends AbstractAction {

	private StageWrapper stage_;

	public ExportExperimentAction(StageWrapper stage) {
		super("Export");
		
		stage_ = stage;
	}

	public void actionPerformed(ActionEvent e) {
		Slide s = StageWrapperController.writeStageWrapperToSlide(stage_, false);
		BuilderState.getInstance().saveCurrentSlide(s);
		BuilderState.getInstance().export();
	}

	
	/** Provide a Universal ID for serialization */
	private static final long serialVersionUID = -875092351023371296L;
}
