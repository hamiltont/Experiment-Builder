package edu.vanderbilt.psychology.gui.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.border.BevelBorder;

import edu.vanderbilt.psychology.controller.SelectionManager;
import edu.vanderbilt.psychology.controller.toolbarActions.AddContainerAction;
import edu.vanderbilt.psychology.controller.toolbarActions.AddImageAction;
import edu.vanderbilt.psychology.controller.toolbarActions.AddSoundAction;
import edu.vanderbilt.psychology.controller.toolbarActions.AddTextAction;
import edu.vanderbilt.psychology.controller.toolbarActions.AddVideoAction;
import edu.vanderbilt.psychology.controller.toolbarActions.ExportExperimentAction;
import edu.vanderbilt.psychology.gui.sideBar.PreviewPanel;
import edu.vanderbilt.psychology.gui.sideBar.SectionedPanel;
import edu.vanderbilt.psychology.gui.toolBar.ToolbarButton;

public class Builder {
	/**
	 * The only place this is used right now is for the separator dimension.
	 * This is pretty annoying, perhaps at some later point we can refactor the
	 * separator code to work around the bug on OS X.
	 */
	private static final int TOOLBAR_HEIGHT = 70;

	/**
	 * Needed because the default Look And Feel (LAF) on Mac OS X has a default
	 * separator dimension of 0 height, so no separator is visibly shown
	 * (although some width is reserved)
	 */
	private static final Dimension TOOLBAR_SEPARATOR_DIMENSION = new Dimension(
			2, TOOLBAR_HEIGHT);

	private static final int SIDEBAR_WIDTH = 300;

	private static final int SLIDE_SWITCHER_HEIGHT = 100;

	/**
	 * Creates and populates the toolbar.
	 * 
	 * <p>
	 * The toolbar is put into {@link BorderLayout#NORTH}, so the preferred
	 * height of the toolbar is the height it receives. The preferred height of
	 * the toolbar is equal to the maximum preferred height of any elements the
	 * toolbar contains, such as buttons or separators. In other words, the
	 * tallest component contained in the toolbar determines the toolbar's
	 * preferred height. The {@link BorderLayout} will set the width of the
	 * toolbar to take all available room, and the preferred width of the
	 * toolbar is ignored.
	 * </p>
	 * 
	 * @param stage
	 * @return
	 */
	protected static JToolBar buildToolBar(StageWrapper stage) {
		JToolBar toolbar = new JToolBar(JToolBar.HORIZONTAL);

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

		// Create section to add slide elements to the current slide
		toolbar.add(addImage);
		toolbar.add(addVid);
		toolbar.add(addSound);
		toolbar.add(addText);
		toolbar.add(addCont);
		toolbar.addSeparator(TOOLBAR_SEPARATOR_DIMENSION);
		// NOTE Other possible buttons: Add Webcam (or record film)

		// Create and add other sections
		toolbar.add(export);
		// add(nextButton);
		// add(prevButton);
		toolbar.addSeparator(TOOLBAR_SEPARATOR_DIMENSION);

		// TODO - It would be nice to just have a border on the south side of
		// the toolbar that is simply a small gradient
		toolbar.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		toolbar.setFloatable(false);

		return toolbar;
	}

	/**
	 * Creates the sidebar.
	 * 
	 * <p>
	 * This is placed inside of a {@link BorderLayout#EAST} section, so the
	 * preferred width of this section is used as the width. The width of the
	 * sidebar is initially SIDEBAR_WIDTH pixels. In version 1, the sidebar will
	 * not be resizable, but in version 2 it may have resize capabilities added
	 * </p>
	 * 
	 * <p>
	 * TODO Currently this method does some state initialization. I would prefer
	 * to put this state initialization elsewhere and have the {@link Builder}
	 * class be constrained to only building gui elements. If someone wants to
	 * understand the state of the application, they should not have to dig
	 * through gui code to figure it out.
	 * </p>
	 * 
	 * <p>
	 * TODO Currently the sidebar is internally using a {@link GridBagLayout}.
	 * This is likely overkill, and is probably resulting in some hard to debug
	 * issues. Change this to use a simpler {@link LayoutManager}
	 * <p>
	 * 
	 * @param stage
	 * @return
	 */
	protected static JPanel buildSideBar(StageWrapper stage) {
		JPanel sideBar = new JPanel();

		sideBar.setPreferredSize(new Dimension(SIDEBAR_WIDTH, 1));

		sideBar.setLayout(new GridBagLayout());
		sideBar.setBorder(BorderFactory.createLoweredBevelBorder());

		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;

		PreviewPanel previewPanel = new PreviewPanel(stage);
		sideBar.add(previewPanel, c);

		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.fill = GridBagConstraints.VERTICAL;
		c.weighty = 1.0;

		SectionedPanel propertyListPanel = new SectionedPanel();
		sideBar.add(propertyListPanel, c);

		// Setup the SelectionManger singleton
		new SelectionManager(previewPanel, propertyListPanel);

		return sideBar;
	}

	/**
	 * Creates the slide switcher.
	 * 
	 * <p>
	 * The switcher is put into {@link BorderLayout#SOUTH}, so the preferred
	 * height of the switcher is the height it is set to. The
	 * {@link BorderLayout} will set the width of the switcher will stretch to
	 * take all available room, the preferred width of the switcher is ignored.
	 * </p>
	 * 
	 * @return
	 */
	protected static JPanel buildSlideSwitcher() {
		JPanel switcher = new JPanel();

		switcher
				.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		switcher.setPreferredSize(new Dimension(1, SLIDE_SWITCHER_HEIGHT));

		return switcher;
	}

}
