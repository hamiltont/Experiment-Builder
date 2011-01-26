package edu.vanderbilt.psychology.player;

import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLayeredPane;

import com.sun.tools.javac.util.Pair;

import edu.vanderbilt.psychology.model.Experiment;
import edu.vanderbilt.psychology.model.ListDatabase;
import edu.vanderbilt.psychology.model.Slide;
import edu.vanderbilt.psychology.model.properties.Appearance;
import edu.vanderbilt.psychology.model.reactor.Action;
import edu.vanderbilt.psychology.model.reactor.ActionListener;
import edu.vanderbilt.psychology.model.reactor.ActionType;
import edu.vanderbilt.psychology.model.reactor.Sleeper;

/**
 * Given an {@link Experiment} that has been loaded from disk, the
 * {@link PlayerController} loads individual {@link Slide}s and displays them.
 * This integrates tightly with the main UI thread. If the user is on a slide,
 * then that slide is displayed and accepting interaction. If the user is
 * between slides, then the pause screen is shown
 * 
 * @author hamiltont
 * 
 */
@SuppressWarnings("serial")
public class PlayerController extends JLayeredPane implements ActionListener {
	private Experiment mExperiment;
	private int mCurrentSlide = 0;

	public PlayerController(Experiment e) {
		// We are interested in slide events
		EventManager.getInstance().registerEventObserver(
				ActionType.TYPE_SLIDE_EVENTS, this);
		EventManager.getInstance().registerEventObserver(
				ActionType.TYPE_APPEARANCE_EVENTS, new Appearance());
		EventManager.getInstance().registerEventObserver(
				ActionType.TYPE_SLEEP_EVENTS, new Sleeper());
		EventManager.getInstance().registerEventObserver(
				ActionType.TYPE_LIST_EVENTS, ListDatabase.getInstance());

		mExperiment = e;

		if (e.getSize() == 0)
			throw new IllegalArgumentException(
					"The loaded experiment has no slides!");

		loadSlide(e.getSlide(mCurrentSlide));

	}

	/**
	 * Loads the new {@link Slide} into the GUI
	 * 
	 * @param s
	 *            passing null indicates that we have reached the end of the
	 *            {@link Experiment} and should wrap up
	 */
	private void loadSlide(Slide s) {
		removeAll();
		
		if (s == null)
			System.exit(0);

		List<Pair<JComponent, Integer>> components = s.getGui();
		for (Pair<JComponent, Integer> pair : components) {
			setLayer(pair.fst, pair.snd.intValue());
			add(pair.fst);
		}

		validate();
		repaint();
	}

	@Override
	public void receiveAction(Action e) {
		if (e.getType().equals(ActionType.TYPE_SLIDE_EVENTS)) {
			mCurrentSlide++;
			if (mExperiment.getSlideExistsAtPosition(mCurrentSlide))
				loadSlide(mExperiment.getSlide(mCurrentSlide));
			else
				loadSlide(null);
		}

	}
}
