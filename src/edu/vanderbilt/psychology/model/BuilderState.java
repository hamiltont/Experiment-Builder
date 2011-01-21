/**
 * 
 */
package edu.vanderbilt.psychology.model;

import java.awt.Component;
import java.awt.Point;
import java.io.File;
import java.util.Set;

import javax.swing.JLayeredPane;
import javax.swing.SwingUtilities;

import edu.vanderbilt.psychology.controller.SelectionManager;
import edu.vanderbilt.psychology.gui.main.StageWrapper;
import edu.vanderbilt.psychology.gui.slideElements.SlideElement;
import edu.vanderbilt.psychology.model.elements.ModelElement;
import edu.vanderbilt.psychology.model.events.EventReactor;

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
	 * Searches for the {@link Slide} that is associated with the passed
	 * thumbnail and sets that {@link Slide} as the current slide, updating the
	 * GUI. Does not automatically save the current {@link Slide}
	 * 
	 * @param thumbnail
	 */
	public void setCurrentSlide(JLayeredPane thumbnail) {
		int size = experiment_.getSize();
		for (int i = 0; i < size; i++)
			if (experiment_.getSlideExistsAtPosition(i)
					&& experiment_.getSlide(i).getSlideThumbnail() == thumbnail) {
				currentSlidePos_ = i;
				clearStageWrapper(stageWrapper_);
				writeSlideToStageWrapper(getCurrentSlide(), stageWrapper_);
				return;
			}
	}

	/**
	 * Increments the current slide position, and returns the next {@link Slide}
	 * . If no next {@link Slide} exists, one will be created and returned for
	 * that position. Note that this <b>automatically</b> saves the current
	 * {@link Slide}. Additionally, this method automatically updates the
	 * {@link StageWrapper} with the info for the next slide. Also clears the
	 * selection
	 * 
	 * 
	 * @return the next {@link Slide}
	 */
	public Slide getNextSlide() {
		saveCurrentSlide();
		
		SelectionManager.getInstance().clearSelection();

		++currentSlidePos_;

		Slide next = getCurrentSlide();
		clearStageWrapper(stageWrapper_);
		writeSlideToStageWrapper(next, stageWrapper_);
		return next;
	}

	/**
	 * Gets the {@link Slide} that is currently being shown on the
	 * {@link StageWrapper}. This does not save the current {@link Slide}
	 * 
	 * @return
	 */
	public Slide getCurrentSlide() {
		return experiment_.getSlide(currentSlidePos_);
	}

	/**
	 * Decrements the current slide counter, and returns the {@link Slide} at
	 * that location. If you attempt to decrement beyond the first {@link Slide}
	 * , then this method will do nothing but return the first {@link Slide} to
	 * you. Note that this <b>automatically</b> saves the current {@link Slide}.
	 * Additionally, this method automatically updates the {@link StageWrapper}
	 * with the information for the newly retrieved slide. Also clears the
	 * selection
	 * 
	 * @return the previous {@link Slide}. If you are already on the first
	 *         {@link Slide}, then this method will just return the first
	 *         {@link Slide} to you, re-saving it in the process.
	 */
	public Slide getPreviousSlide() {
		saveCurrentSlide();

		SelectionManager.getInstance().clearSelection();

		if (currentSlidePos_ != 0)
			--currentSlidePos_;

		Slide prev = getCurrentSlide();
		clearStageWrapper(stageWrapper_);
		writeSlideToStageWrapper(prev, stageWrapper_);
		return prev;
	}

	/**
	 * This method assumes that all {@link Slide}s have been saved into the
	 * {@link Experiment}, and requests the {@link Experiment} save itself to
	 * disk
	 */
	// TODO - Should this do something with ensuring all Slides have been saved
	// to the experiment?
	public void writeExperimentToDisk(File fileToBeWritten) {
		experiment_.saveExperimentToDisk(fileToBeWritten);
	}

	/**
	 * Saves the Slide that is currently being worked on.
	 */
	public void saveCurrentSlide() {
		Slide newSlide = null;
		if (experiment_.getSlideExistsAtPosition(currentSlidePos_)) {
			Slide oldSlide = experiment_.getSlide(currentSlidePos_);

			newSlide = writeStageWrapperToSlide(stageWrapper_, false, oldSlide);
		} else
			// Just save the GUI info
			newSlide = writeStageWrapperToSlide(stageWrapper_, false, null);

		experiment_.saveSlide(newSlide, currentSlidePos_);

	}

	/**
	 * Converts the {@link StageWrapper} into a {@link Slide}, optionally
	 * clearing the {@link StageWrapper} to prepare for the addition of other
	 * elements.
	 * 
	 * @param stageWrapper
	 *            The {@link StageWrapper} to be saved
	 * @param clearStage
	 *            true if all {@link SlideElement}s should be removed from the
	 *            {@link StageWrapper} after they are saved into a {@link Slide}
	 * @param oldSlide
	 *            If null, a new {@link Slide} is created. Otherwise any model
	 *            information in the oldSlide, such as any event reactors, will
	 *            be saved.
	 * @return The {@link Slide} containing all {@link SlideElement}s that were
	 *         present on the {@link StageWrapper}
	 */
	private static Slide writeStageWrapperToSlide(StageWrapper stageWrapper,
			boolean clearStage, Slide oldSlide) {

		Slide s = null;
		if (oldSlide == null)
			s = new Slide();
		else
			s = oldSlide;

		for (Component c : stageWrapper.getComponents()) {
			if ((c instanceof SlideElement) == false)
				continue;

			SlideElement se = (SlideElement) c;

			// Setup the ModelElement and store to Slide
			ModelElement me = se.getModel();
			int layer = stageWrapper.getLayer(se);
			Point p = c.getLocation();
			me.addGuiProperties(layer, p);
			s.saveElement(me);

			// Update the EventReactors so that each EventReactor has a
			// reference to the ModelElement for this SlideElement
			// TODO - Check for memory leaks here
			if (oldSlide != null)
				for (EventReactor er : oldSlide.getReactorsReferencing(se))
					er.setModelElement(me);

			if (clearStage)
				stageWrapper.remove(c);
		}

		return s;
	}

	public static void clearStageWrapper(StageWrapper sw) {
		for (Component c : sw.getComponents()) {
			if ((c instanceof SlideElement) == false)
				continue;

			sw.remove(c);
		}

		sw.validate();
		sw.repaint();
	}

	// TODO - Things to consider: not all SlideElements
	// may have a position. The stage may change size. The stageWrapper may
	// change size. The application may change size
	public static void writeSlideToStageWrapper(Slide s,
			StageWrapper stageWrapper) {

		System.out.println("Writing slide to stage wrapper");

		final Set<ModelElement> elements = s.getModelElements();
		for (ModelElement me : elements) {
			SlideElement se = me.getInitializedSlideElement();
			stageWrapper.add(se, me.getLayer());
			se.setLocation(me.getLocation());
		}
	}

}
