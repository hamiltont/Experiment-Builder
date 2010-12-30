package edu.vanderbilt.psychology.player;

import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import com.sun.tools.javac.util.Pair;

import edu.vanderbilt.psychology.model.Experiment;
import edu.vanderbilt.psychology.model.Slide;
import edu.vanderbilt.psychology.model.events.Event;
import edu.vanderbilt.psychology.model.events.EventListener;
import edu.vanderbilt.psychology.model.events.EventType;

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

	public PlayerController(Experiment e) {
		// We are interested in slide events
		EventManager.getInstance().registerEventObserver(
				new EventType(EventType.TYPE_SLIDE_EVENTS), this);

		mExperiment = e;

		if (e.getSize() == 0)
			throw new IllegalArgumentException(
					"The loaded experiment has no slides!");

		loadSlide(e.getSlide(0));
		
		
	}

	private void loadSlide(Slide s) {
		
		List<Pair<JComponent, Integer>> components = s.getGui();
		for (Pair<JComponent, Integer> pair : components)
			add(pair.fst, pair.snd);

		revalidate();
	}

	@Override
	public void receiveEvent(Event e) {
		// TODO Auto-generated method stub

	}

}
