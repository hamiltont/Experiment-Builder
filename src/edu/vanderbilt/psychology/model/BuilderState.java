/**
 * 
 */
package edu.vanderbilt.psychology.model;

import java.awt.Component;
import java.awt.Point;
import java.util.List;

import edu.vanderbilt.psychology.controller.SelectionManager;
import edu.vanderbilt.psychology.gui.main.StageWrapper;
import edu.vanderbilt.psychology.gui.slideElements.SlideElement;
import edu.vanderbilt.psychology.model.elements.ModelElement;

/**
 * Holds state information needed by the builder component. For example,
 * {@link BuilderState} contains the {@link Experiment} that the builder is
 * currently working on and the {@link Slide} that is currently being displayed
 * on the {@link StageWrapper}.
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
// TODO - Expose methods that would be useful to plugins. For example, some
// plugins might be interested in a method to clear the stagewrapper of all
// slideelements, without saving any slideelements
// TODO This is a bit tangled with the Experiment class. Both builder state and
// experiment are largely a model objects
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
	public Slide getNextSlide(Slide currentSlide) {

		saveCurrentSlide(currentSlide);
		++currentSlidePos_;

		// TODO put this into the SSAction
		SelectionManager.getInstance().clearSelection();
		stageWrapper_.repaint();

		return experiment_.getSlide(currentSlidePos_);
	}

	/**
	 * Gets the {@link Slide} that is currently being shown on the
	 * {@link StageWrapper}. If the {@link StageWrapper} has not yet been saved
	 * to a {@link Slide} (or the saved representation is outdated) this
	 * re-saves the {@link StageWrapper}
	 * 
	 * @return
	 */
	public Slide getCurrentSlide() {
		if (experiment_.getSlideExistsAtPosition(currentSlidePos_) == true)
			writeStageWrapperToExistingSlide(experiment_.getSlide(currentSlidePos_), stageWrapper_);
		else
			return writeStageWrapperToSlide(stageWrapper_, false);
		
		return experiment_.getSlide(currentSlidePos_);
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
	public Slide getPreviousSlide(Slide currentSlide) {
		if (currentSlidePos_ == 0)
			return currentSlide;

		saveCurrentSlide(currentSlide);
		--currentSlidePos_;

		// TODO put this into the SSAction
		SelectionManager.getInstance().clearSelection();
		stageWrapper_.repaint();

		return experiment_.getSlide(currentSlidePos_);
	}

	/**
	 * This method assumes that all {@link Slide}s have been saved into the
	 * {@link Experiment}, and requests the {@link Experiment} save itself to
	 * disk
	 */
	// TODO - Should this do something with ensuring all Slides have been saved
	// to the experiment?
	public void writeExperimentToDisk() {
		experiment_.saveExperimentToDisk();
	}

	/**
	 * Given a {@link Slide}, saves that {@link Slide} to the {@link Experiment}
	 * in the current position
	 * 
	 * @param currentSlide
	 */
	public void saveCurrentSlide(Slide currentSlide) {
		experiment_.saveSlide(currentSlide, currentSlidePos_);
	}

	/**
	 * Converts the {@link StageWrapper} into a {@link Slide}, optionally
	 * clearing the {@link StageWrapper} to prepare for the addition of other
	 * elements
	 * 
	 * @param stageWrapper
	 *            The {@link StageWrapper} to be saved
	 * @param clearStage
	 *            true if all {@link SlideElement}s should be removed from the
	 *            {@link StageWrapper} after they are saved into a {@link Slide}
	 * @return The {@link Slide} containing all {@link SlideElement}s that were
	 *         present on the {@link StageWrapper}
	 */
	public static Slide writeStageWrapperToSlide(StageWrapper stageWrapper,
			boolean clearStage) {

		Slide s = new Slide();

		for (Component c : stageWrapper.getComponents()) {
			if ((c instanceof SlideElement) == false)
				continue;

			ModelElement me = ((SlideElement) c).getModel();
			int layer = stageWrapper.getLayer(c);
			Point p = c.getLocation();

			me.addGuiProperties(layer, p);

			s.saveElement(me);

			if (clearStage)
				stageWrapper.remove(c);
		}

		return s;
	}

	/**
	 * Given an existing {@link Slide}, replaces all of the {@link ModelElement}
	 * s in the {@link Slide} with the {@link ModelElement}s generated from all
	 * of the {@link SlideElement}s currently stored on the {@link StageWrapper}
	 * 
	 * @param existingSlide
	 * @param stageWrapper
	 */
	// TODO - this is a convoluted way of "updating" an existing slide w/o
	// throwing away the reactor references
	public static void writeStageWrapperToExistingSlide(Slide existingSlide,
			StageWrapper stageWrapper) {

		existingSlide.clearElements();

		for (Component c : stageWrapper.getComponents()) {
			if ((c instanceof SlideElement) == false)
				continue;

			ModelElement me = ((SlideElement) c).getModel();
			int layer = stageWrapper.getLayer(c);
			Point p = c.getLocation();

			me.addGuiProperties(layer, p);

			existingSlide.saveElement(me);
		}
	}

	// TODO - implement. This will involve figuring out how to keep track of the
	// location of the SlideElements. Things to consider: not all slideelements
	// may have a position. The stage may change size. The stageWrapper may
	// change size. The application may change size
	public static void writeSlideToStageWrapper(Slide s,
			StageWrapper stageWrapper) {

		System.out.println("Writing slide to stage wrapper");

		final List<ModelElement> elements = s.getModelElements();
		for (ModelElement me : elements) {
			SlideElement se = me.getInitializedSlideElement();
			stageWrapper.add(se, me.getLayer());
			se.setLocation(me.getLocation());
		}
	}

}
