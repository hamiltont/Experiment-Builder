package edu.vanderbilt.psychology.gui.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.border.BevelBorder;

import edu.vanderbilt.psychology.controller.SelectionManager;
import edu.vanderbilt.psychology.controller.toolbarActions.AddContainerAction;
import edu.vanderbilt.psychology.controller.toolbarActions.AddImageAction;
import edu.vanderbilt.psychology.controller.toolbarActions.AddSoundAction;
import edu.vanderbilt.psychology.controller.toolbarActions.AddTextAction;
import edu.vanderbilt.psychology.controller.toolbarActions.AddVideoAction;
import edu.vanderbilt.psychology.controller.toolbarActions.CreateListAction;
import edu.vanderbilt.psychology.controller.toolbarActions.ExportExperimentAction;
import edu.vanderbilt.psychology.gui.sideBar.PreviewPanel;
import edu.vanderbilt.psychology.gui.sideBar.SectionedPanel;
import edu.vanderbilt.psychology.gui.toolBar.ToolbarButton;
import edu.vanderbilt.psychology.model.Experiment;
import edu.vanderbilt.psychology.model.Slide;

/**
 * The sidebar and the slide switcher have a pre-defined dimension used
 * initially. The toolbar calculates it's preferred dimensions based off of it's
 * internal components. The reason for this is that the switcher and the sidebar
 * will eventually (version 2) be resizable.
 * 
 * @author Hamilton Turner
 * 
 */
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
	
	private static final int JLAYEREDPANE_WIDTH = 100;
	
	private static final int JLAYEREDPANE_HEIGHT = 100;

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
	 * <p>
	 * The intent in the separation is to break the toolbar up into 3 sections.
	 * One section shall be buttons to add stuff to the current {@link Slide},
	 * another section shall be buttons to change {@link Slide} properties, and
	 * the last section shall be to change {@link Experiment} properties
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
		final CreateListAction createListAction = new CreateListAction();

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
		final ToolbarButton createList = new ToolbarButton(createListAction,
				"images/picture_add.png", "Create List");

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
		toolbar.add(createList);
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
	 * 
	 * <p>
	 * The sidebar internally uses a {@link BorderLayout} that is broken apart
	 * as follows:
	 * </p>
	 * <img
	 * src="../../../../../../doc-source/diagrams/sidebar-border-layout.jpg" />
	 * <p>
	 * The top panel (teal color) is the {@link PreviewPanel} section. The
	 * height of the top component is set using the preferred size of the
	 * internal {@link PreviewPanel}. In the future this may be a resizable
	 * property. The preferred width of the {@link PreviewPanel} is ignored, and
	 * the top component expands to fill all available space. However, the
	 * available space is limited to {@link Builder#SIDEBAR_WIDTH}, so the width
	 * of the top component will always be equal to
	 * {@link Builder#SIDEBAR_WIDTH}.
	 * </p>
	 * 
	 * <p>
	 * The bottom panel (red color) is the {@link SectionedPanel}. The height of
	 * the bottom component expands as much as it can. The width of the bottom
	 * component expands as well. However, the bottom component has the same
	 * width limitation that the top component experiences. Namely, the bottom
	 * component width will always be equal to {@link Builder#SIDEBAR_WIDTH}.
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
	 * 
	 * @param stage
	 * @return
	 */
	protected static JPanel buildSideBar(StageWrapper stage) {
		JPanel sideBar = new JPanel();

		sideBar.setPreferredSize(new Dimension(SIDEBAR_WIDTH, 1));

		sideBar.setLayout(new BorderLayout());
		sideBar.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		PreviewPanel previewPanel = new PreviewPanel(stage);
		sideBar.add(previewPanel, BorderLayout.NORTH);

		SectionedPanel propertyListPanel = new SectionedPanel();
		sideBar.add(propertyListPanel, BorderLayout.CENTER);

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
		switcher.setLayout(new FlowLayout());

		switcher
				.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		switcher.setPreferredSize(new Dimension(1, SLIDE_SWITCHER_HEIGHT));
		
    	JLayeredPane menu = new JLayeredPane();
    	menu.setPreferredSize(new Dimension(150, 135));
    	
    	JPanel palette = new JPanel();
    	palette.setLayout(new FlowLayout());
    	JButton dropdown = new JButton("V");
    	palette.add(dropdown);
    	
    	menu.add(palette, JLayeredPane.PALETTE_LAYER);
    	palette.setBounds(75, 0, 75, 35);
    	
    	JPanel modal = new JPanel();
    	modal.setLayout(new FlowLayout());
  
    	JButton makeCopy = new JButton("Make Copy");
    	JButton repeat = new JButton("Repeat x times");
    	JButton repeatUntil = new JButton("Repeat Until...");
    	modal.add(makeCopy);
    	modal.add(repeat);
    	modal.add(repeatUntil);
    	
    	menu.add(modal, JLayeredPane.MODAL_LAYER);
    	modal.setBounds(0, 35, 150, 100);
    	
    	switcher.add(menu);
    	
    	JPanel newSlide = new JPanel();
    	newSlide.setLayout(new BorderLayout());
    	JButton plus = new JButton("+");
    	newSlide.add(plus, BorderLayout.CENTER);
    	JLabel newSlideText = new JLabel("New Slide");
    	newSlide.add(newSlideText, BorderLayout.SOUTH);
    	
    	switcher.add(newSlide);

		return switcher;
	}

}
