package edu.vanderbilt.psychology.controller.toolbarActions;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;

import edu.vanderbilt.psychology.gui.main.StageWrapper;
import edu.vanderbilt.psychology.model.BuilderState;
import edu.vanderbilt.psychology.model.Slide;

@SuppressWarnings("serial")
public class SaveExperimentAction extends AbstractAction {

	private StageWrapper stage_;

	public SaveExperimentAction(StageWrapper stage) {
		super("Save");

		stage_ = stage;
	}

	/**
	 * Ensures the current {@link StageWrapper} state is saved into a
	 * {@link Slide}, and that that {@link Slide} is saved into the
	 * {@link BuilderState}, and then tells the {@link BuilderState} to save to
	 * disk
	 */
	public void actionPerformed(ActionEvent e) {

		Slide s = BuilderState.writeStageWrapperToSlide(stage_, false);
		BuilderState.getInstance().saveCurrentSlide(s);
		JFileChooser save = new JFileChooser();
	    int returnVal = save.showSaveDialog(stage_); {
	    if (returnVal == JFileChooser.APPROVE_OPTION) {
	    	File selectedFile = new File(save.getCurrentDirectory() + "/" + save.getName());
			BuilderState.getInstance().writeExperimentToDisk(selectedFile);}
	    }
	}
}
