package edu.vanderbilt.psychology.gui.main;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JToolBar;

import edu.vanderbilt.psychology.controller.toolbarActions.AddContainerAction;
import edu.vanderbilt.psychology.controller.toolbarActions.AddImageAction;
import edu.vanderbilt.psychology.controller.toolbarActions.AddSoundAction;
import edu.vanderbilt.psychology.controller.toolbarActions.AddTextAction;
import edu.vanderbilt.psychology.controller.toolbarActions.AddVideoAction;
import edu.vanderbilt.psychology.controller.toolbarActions.ExportExperimentAction;
import edu.vanderbilt.psychology.gui.toolBar.ToolbarButton;
import edu.vanderbilt.psychology.model.Experiment;
import edu.vanderbilt.psychology.model.Slide;

/**
 * Manages the initial creation of {@link ToolbarButton}s and their ordered
 * addition to the {@link ToolBar}. Also sets up the dividers between the
 * buttons
 * 
 * @author Hamilton Turner
 * 
 */
public class ToolBar extends JToolBar {

	private static final int TOOLBAR_HEIGHT = 70;
	private static final Dimension TOOLBAR_SEPARATOR_DIMENSION = new Dimension(
			2, TOOLBAR_HEIGHT);

	/**
	 * The intent in the separation is to break the toolbar up into 3 sections.
	 * One section shall be buttons to add stuff to the current {@link Slide},
	 * another seciton shall be buttons to change {@link Slide} properties, and
	 * the last section shall be to change {@link Experiment} properties
	 * 
	 * @param stage
	 */
	public ToolBar(StageWrapper stage) {
		super();

		// Simple layout with some padding
		FlowLayout layout = new FlowLayout(FlowLayout.LEFT, 10, 5);
		setLayout(layout);

		// Create actions needed for the buttons
		final ExportExperimentAction exportAction = new ExportExperimentAction(
				stage);
		final AddContainerAction addContainerAction = new AddContainerAction(
				stage);
		final AddTextAction addTextAction = new AddTextAction(stage);
		final AddSoundAction addSoundAction = new AddSoundAction(stage);
		final AddVideoAction addVideoAction = new AddVideoAction(stage);
		final AddImageAction addImageAction = new AddImageAction(stage);

		// Create Toolbar buttons
		final ToolbarButton export = new ToolbarButton(exportAction,
				"images/export_icon.png", "Export");
		final ToolbarButton addCont = new ToolbarButton(addContainerAction,
				"images/container_add.png", "Add Container");
		final ToolbarButton addText = new ToolbarButton(addTextAction,
				"images/font_add.png", "Add Text");
		final ToolbarButton addSound = new ToolbarButton(addSoundAction,
				"images/sound_add.png", "Add Sound");
		final ToolbarButton addVid = new ToolbarButton(addVideoAction,
				"images/film_add.png", "Add Video");
		final ToolbarButton addImage = new ToolbarButton(addImageAction,
				"images/picture_add.png", "Add Image");

		// Disable the buttons we have not implemented
		addVid.setEnabled(false);
		addSound.setEnabled(false);
		addCont.setEnabled(false);

		// Create and add 'add' buttons
		add(addImage);
		add(addVid);
		add(addSound);
		add(addText);
		add(addCont);
		addSeparator(TOOLBAR_SEPARATOR_DIMENSION);
		// NOTE Other possible buttons: Add Webcam (or record film)

		// Create and add other buttons
		add(export);
		// add(nextButton);
		// add(prevButton);

		addSeparator(TOOLBAR_SEPARATOR_DIMENSION);

		// Show
		setVisible(true);
		setFloatable(false);
	}

	/** Provide a Universal ID for serialization */
	private static final long serialVersionUID = -263912033533453658L;
}
