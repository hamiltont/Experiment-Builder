/**
 * 
 */
package edu.vanderbilt.psychology.gui.main;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLayeredPane;

import net.java.swingfx.jdraggable.Draggable;
import edu.vanderbilt.psychology.gui.stage.Stage;

/**
 * The main {@link JLayeredPane} for the application. Contained within the
 * {@link MainFrame}. Note that in the MainFrame the {@link MainStageWrapper} is
 * wired up as a {@link Draggable} Container, so any objects added to
 * {@link MainStageWrapper} that implement {@link Draggable} will be have drag
 * functionality.
 * 
 * Note that a {@link JLayeredPane} has no layout manager by default, so any
 * item added to this view will not show up unless the size and location are set
 * explicitly.
 * 
 * @author Hamilton Turner
 * 
 */
// TODO Update the minimum and maximum sizes for this component based off of the
// Stage
public class MainStageWrapper extends JLayeredPane {

	private boolean addedStage_ = false;
	private static final int stageInset_ = 20;

	public MainStageWrapper(MainFrame frame) {
		super();

		setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

		setVisible(true);
	}

	public void doLayout() {
		super.doLayout();
		
		if (addedStage_ == false) {
			Stage s = new Stage(getWidth() - stageInset_, getHeight()
					- stageInset_);
			
			// Center the stage
			final int vspace = getWidth() - s.getWidth();
			final int hspace = getHeight() - s.getHeight();
			s.setLocation(vspace / 2, hspace / 2);
			
			add(s, JLayeredPane.DEFAULT_LAYER);
			addedStage_ = true;
		}
	}

	/** Provide a Universal ID for serialization */
	private static final long serialVersionUID = 1851624859168284892L;

}
