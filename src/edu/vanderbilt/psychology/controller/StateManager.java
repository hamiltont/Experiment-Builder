/**
 * 
 */
package edu.vanderbilt.psychology.controller;

import java.awt.Component;

import edu.vanderbilt.psychology.gui.main.MainStageWrapper;
import edu.vanderbilt.psychology.gui.slideElements.SlideElement;
import edu.vanderbilt.psychology.model.Experiment;
import edu.vanderbilt.psychology.model.Slide;

/**
 * For now this just holds state that I have nowhere else to put. Later on this
 * should be fractioned into classes that make more sense. Right now it acts as
 * a controller in the MVC pattern, so perhaps I should create a controller
 * package and just go with a standard MVC approach
 * 
 * @author Hamilton Turner
 * 
 */
public class StateManager {
	private Experiment experiment_;
	private int currentSlide_;

	public static StateManager instance_ = null;

	private StateManager() {
		experiment_ = new Experiment();
		currentSlide_ = 0;
	}

	public static StateManager getInstance() {
		if (instance_ == null)
			instance_ = new StateManager();
		return instance_;
	}

	public void nextSlide(MainStageWrapper stage) {
		Slide s = new Slide();

		for (Component c : stage.getComponents()) {
			if ((c instanceof SlideElement) == false)
				continue;

			final SlideElement se = (SlideElement) c;
			s.saveElement(se);

			stage.remove(c);
		}

		experiment_.saveSlide(s);
		++currentSlide_;
		SelectionManager.getInstance().clearSelection();
		stage.repaint();
	}

	public void saveCurrentSlide(MainStageWrapper stage) {
		Slide s = new Slide();

		for (Component c : stage.getComponents()) {
			if ((c instanceof SlideElement) == false)
				continue;

			final SlideElement se = (SlideElement) c;
			s.saveElement(se);
		}

		// TODO - pass in the position to save here!
		experiment_.saveSlide(s);
	}

	public void export() {
		experiment_.saveExperiment();
	}
}
