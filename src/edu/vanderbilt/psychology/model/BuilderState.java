/**
 * 
 */
package edu.vanderbilt.psychology.model;

import edu.vanderbilt.psychology.controller.SelectionManager;
import edu.vanderbilt.psychology.gui.main.StageWrapper;

/**
 * Serves as a model in the MVC Software design pattern. Holds any information
 * needed by the builder component of the software. Additionally, contains the
 * {@link Experiment} that the builder is currently working on. To that end, the
 * {@link BuilderState} is occasionally just a call-thru class to some method in
 * the {@link Experiment}
 * 
 * Also quasi-uses the Singleton pattern. To access the {@link BuilderState},
 * simply call {@link BuilderState#getInstance()}
 * 
 * @author Hamilton Turner
 * 
 */
// TODO At some point, it might be good to make this model object be able to
// inform any model observers of a change. This would imply removing the
// StageWrapper parameters to the next/previous methods and constructing this
// with a handle to the StageWrapper. I am not sure if this confers any
// quantitative benefits above and beyond cleaner code.
public class BuilderState {
	private Experiment experiment_;
	private int currentSlidePos_;
	private StageWrapper stageWrapper_;

	public static BuilderState instance_ = null;

	public BuilderState(StageWrapper stageWrapper) {
		experiment_ = new Experiment();
		currentSlidePos_ = 0;
		stageWrapper_ = stageWrapper;

		instance_ = this;
	}

	public static BuilderState getInstance() {
		if (instance_ == null)
			throw new IllegalStateException(
					"The BuilderState was accessed, but it was never constructed");
		return instance_;
	}

	/**
	 * Saves the current {@link Slide} to the {@link Experiment}, increments the
	 * current slide counter, and returns the next {@link Slide}
	 * 
	 * @param currentSlide
	 *            the {@link Slide} that is currently showing in the GUI
	 * @return the next {@link Slide}, or a blank default {@link Slide} if there
	 *         is no saved next {@link Slide}
	 */
	public Slide nextSlide(Slide currentSlide) {

		saveCurrentSlide(currentSlide);
		++currentSlidePos_;

		// TODO put this into the SSAction
		SelectionManager.getInstance().clearSelection();
		stageWrapper_.repaint();

		return experiment_.getSlide(currentSlidePos_);
	}

	public void export() {
		experiment_.saveExperiment();
	}

	/**
	 * Saves the current {@link Slide} to the {@link Experiment}, decrements the
	 * current slide counter, and returns the next {@link Slide}. If you attempt
	 * to decrement beyond the first {@link Slide}, then this method will do
	 * nothing but return the first {@link Slide} to you
	 * 
	 * @param currentSlide
	 *            the {@link Slide} that is currently showing in the GUI
	 * @return the previous {@link Slide}. If you are already on the first
	 *         {@link Slide}, then this method will just return the first
	 *         {@link Slide} to you without saving it.
	 */
	public Slide prevSlide(Slide currentSlide) {
		if (currentSlidePos_ == 0)
			return currentSlide;

		saveCurrentSlide(currentSlide);
		--currentSlidePos_;

		// TODO put this into the SSAction
		SelectionManager.getInstance().clearSelection();
		stageWrapper_.repaint();

		return experiment_.getSlide(currentSlidePos_);
	}

	public void saveCurrentSlide(Slide currentSlide) {
		experiment_.saveSlide(currentSlide, currentSlidePos_);
	}
}
