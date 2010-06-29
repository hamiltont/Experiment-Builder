package edu.vanderbilt.psychology.controller;

import java.awt.Component;
import java.awt.Point;
import java.util.List;

import edu.vanderbilt.psychology.gui.main.StageWrapper;
import edu.vanderbilt.psychology.gui.slideElements.SlideElement;
import edu.vanderbilt.psychology.model.Slide;
import edu.vanderbilt.psychology.model.elements.ModelElement;

/**
 * Exposes static methods to save the current elements on the
 * {@link StageWrapper}, and to properly clear the {@link StageWrapper}.
 * 
 * @author Hamilton Turner
 * 
 */
// TODO - Expose methods that would be useful to plugins. For example, some
// plugins might be interested in a method to clear the stagewrapper of all
// slideelements, without saving any slideelements
public class StageWrapperController {

	/**
	 * Converts the {@link StageWrapper} into a {@link Slide}, optionally
	 * clearing the {@link StageWrapper} to prepare for the addition of multiple
	 * other elements
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

	// TODO - implement. This will involve figuring out how to keep track of the
	// location of the SlideElements. Things to consider: not all slideelements
	// may have a position. The stage may change size. The stageWrapper may
	// change size. The application may change size
	public static void writeSlideToStageWrapper(Slide s,
			StageWrapper stageWrapper) {

		System.out.println("Writing slide to stage wrapper");
		
		final List<ModelElement> elements = s.getModelElements();
		for (ModelElement me:elements) {
			SlideElement se = me.getInitializedSlideElement();
			stageWrapper.add(se, me.getLayer());
			se.setLocation(me.getLocation());
		}
	}

}
