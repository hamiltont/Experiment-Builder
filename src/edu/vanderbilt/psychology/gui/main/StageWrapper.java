/**
 * 
 */
package edu.vanderbilt.psychology.gui.main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BorderFactory;
import javax.swing.JLayeredPane;

import net.java.swingfx.jdraggable.Draggable;
import edu.vanderbilt.psychology.gui.stage.Stage;

/**
 * The main {@link JLayeredPane} for the application. Contained within the
 * {@link MainFrame}. Note that in the MainFrame the {@link StageWrapper} is
 * wired up as a {@link Draggable} Container, so any objects added to
 * {@link StageWrapper} that implement {@link Draggable} will be have drag
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

// TODO In order to implement a fully-resizable application window, the
// SlideElements need to be resizeable. That implies that this JLayeredPane
// needs to be able to lay out the components in some way. Until we figure that
// out, the main JFrame is marked as not resizable.

// TODO It would be nice to add a custom LayoutManager to the JLayeredPane that
// completely ignored the layers and simply handled the resize operations. Aka
// if the JLayeredPane was resized, then this would resize all of the internal
// components as well, following some pre-defined rules
public class StageWrapper extends JLayeredPane {

	private Stage stage_;
	private static final int stageInset_ = 20;

	public StageWrapper(MainFrame frame) {
		super();

		setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

		stage_ = new Stage();
		add(stage_, JLayeredPane.DEFAULT_LAYER);

		setVisible(true);

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {

				stage_.setSize(getWidth() - stageInset_, getHeight()
						- stageInset_);

				// Center the stage
				final int vspace = getWidth() - stage_.getWidth();
				final int hspace = getHeight() - stage_.getHeight();
				stage_.setLocation(vspace / 2, hspace / 2);
			}
		});

	}

	/** Provide a Universal ID for serialization */
	private static final long serialVersionUID = 1851624859168284892L;
}
