package edu.vanderbilt.psychology.player;

import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLayeredPane;

import com.sun.tools.javac.util.Pair;

import edu.vanderbilt.psychology.model.Experiment;
import edu.vanderbilt.psychology.model.Slide;
import edu.vanderbilt.psychology.model.events.Event;
import edu.vanderbilt.psychology.model.events.EventListener;
import edu.vanderbilt.psychology.model.events.EventType;
import edu.vanderbilt.psychology.model.properties.Appearance;

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
public class PlayerController extends JLayeredPane implements EventListener {
	private Experiment mExperiment;
	private int mCurrentSlide = 0;

	public PlayerController(Experiment e) {
		// We are interested in slide events
		EventManager.getInstance().registerEventObserver(
				EventType.TYPE_SLIDE_EVENTS, this);
		EventManager.getInstance().registerEventObserver(
				EventType.TYPE_APPEARANCE_EVENTS, new Appearance());

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

		if (s == null)
			System.exit(0);

		List<Pair<JComponent, Integer>> components = s.getGui();
		for (Pair<JComponent, Integer> pair : components)
		{			
			setLayer(pair.fst, pair.snd.intValue());
			add(pair.fst);
		}

		revalidate();
	}

	@Override
	public void receiveEvent(Event e) {
		if (e.getType().equals(EventType.TYPE_SLIDE_EVENTS)) {
			mCurrentSlide++;
			if (mExperiment.getSlideExistsAtPosition(mCurrentSlide))
				loadSlide(mExperiment.getSlide(mCurrentSlide));
			else
				loadSlide(null);
		}

	}
}
