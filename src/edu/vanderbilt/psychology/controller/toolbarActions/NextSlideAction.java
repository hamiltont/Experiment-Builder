package edu.vanderbilt.psychology.controller.toolbarActions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import edu.vanderbilt.psychology.controller.StateManager;
import edu.vanderbilt.psychology.gui.main.MainStageWrapper;

public class NextSlideAction extends AbstractAction {

	private MainStageWrapper stage_;

	public NextSlideAction(MainStageWrapper stage) {
		super("Next Slide");
		stage_ = stage;
	}

	public void actionPerformed(ActionEvent e) {
		StateManager.getInstance().nextSlide(stage_);
		
	}

	
	/** Provide a Universal ID for serialization */
	private static final long serialVersionUID = 8516037965730517765L;
}
