package edu.vanderbilt.psychology.controller.toolbarActions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import edu.vanderbilt.psychology.controller.SelectionManager;
import edu.vanderbilt.psychology.gui.main.StageWrapper;
import edu.vanderbilt.psychology.model.Experiment;
import edu.vanderbilt.psychology.model.Slide;
import edu.vanderbilt.psychology.model.BuilderState;

/**
 * An action capable of switching slides. This action can allow the stage to go
 * back one slide, or to go forward one slide. Whenever this action is performed
 * the current user interface state is saved into an instance of {@link Slide}
 * 
 * This keeps a handle to the {@link StageWrapper}. This handle allows access to
 * all current GUI elements. All information about a slide is contained in the
 * user interface while the slide is being edited. When the stage is switched
 * one slide forward or one slide back, all information in the user interface is
 * stored into an instance of {@link Slide}, which is then itself stored into
 * the {@link Experiment} <br>
 * 
 * <p>
 * <img src="../../../../../../doc-source/diagrams/changing-slides.jpg" alt="
 * Explanation of MVC approach to changing slides" />
 * </p>
 * 
 * @author hamiltont
 * 
 */
public class SwitchSlideAction extends AbstractAction {

	private StageWrapper stage_;
	private int direction_;

	public static final int SWITCH_NEXT_SLIDE = 0;
	public static final int SWITCH_PREV_SLIDE = 1;

	/**
	 * Creates an action capable of switching slides. This action can allow the
	 * stage to go back one slide, or to go forward one slide. Whenever this
	 * action is performed the current user interface state is saved into an
	 * instance of {@link Slide}
	 * 
	 * @param stage
	 *            A handle to the {@link StageWrapper}. This allows access to
	 *            all current GUI elements. All information about a slide is
	 *            contained in the user interface until the stages is switched
	 *            one slide forward or one slide back. When this happens, all
	 *            information in the user interface is stored into a
	 *            {@link Slide}, which is then itself stored into the
	 *            {@link Experiment} <br>
	 * <br>
	 * @param direction
	 *            Either {@link SwitchSlideAction#SWITCH_NEXT_SLIDE} or
	 *            {@link SwitchSlideAction#SWITCH_PREV_SLIDE}
	 */
	public SwitchSlideAction(StageWrapper stage, int direction) {
		super("Next Slide");

		if ((direction != SWITCH_NEXT_SLIDE)
				&& (direction != SWITCH_PREV_SLIDE))
			throw new IllegalArgumentException(
					"Invalid direction argument. Must be one of the two "
							+ "directions defined in the SwitchSlideAction class");

		stage_ = stage;
		direction_ = direction;
	}

	public void actionPerformed(ActionEvent e) {

		switch (direction_) {

		case (SWITCH_NEXT_SLIDE):
			Slide next = BuilderState.getInstance().getNextSlide();

			SelectionManager.getInstance().clearSelection();
			stage_.repaint();

			BuilderState.writeSlideToStageWrapper(next, stage_);
			break;

		case (SWITCH_PREV_SLIDE):
			Slide prev = BuilderState.getInstance().getPreviousSlide();

			// TODO put this into the SSAction
			// SelectionManager.getInstance().clearSelection();
			// stageWrapper_.repaint();

			SelectionManager.getInstance().clearSelection();
			stage_.repaint();

			BuilderState.writeSlideToStageWrapper(prev, stage_);
			break;

		default:
			throw new IllegalStateException("Unknown direction");
		}

	}

	/** Provide a Universal ID for serialization */
	private static final long serialVersionUID = 8516037965730517765L;
}
