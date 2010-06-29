/**
 * 
 */
package edu.vanderbilt.psychology.gui.main;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import edu.vanderbilt.psychology.controller.SelectionManager;
import edu.vanderbilt.psychology.gui.sideBar.PreviewPanel;
import edu.vanderbilt.psychology.gui.sideBar.SectionedPanel;

/**
 * Contains and lays out the {@link PreviewPanel} and {@link SectionedPanel}, as
 * well as setting the default width for the side bar
 * 
 * @author Hamilton Turner
 * 
 */
public class SideBar extends JPanel {
	public static final int width_ = 300;

	public SideBar(StageWrapper stage) {
		super(new GridBagLayout());

		setPreferredSize(new Dimension(width_, Short.MAX_VALUE));

		setBorder(BorderFactory.createLoweredBevelBorder());

		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;

		PreviewPanel previewPanel = new PreviewPanel(stage);
		add(previewPanel, c);

		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.fill = GridBagConstraints.VERTICAL;
		c.weighty = 1.0;

		SectionedPanel propertyListPanel = new SectionedPanel();
		add(propertyListPanel, c);

		// Setup the SelectionManger singleton
		new SelectionManager(previewPanel, propertyListPanel);
		
	}
	

	/** Provide a Universal ID for serialization */
	private static final long serialVersionUID = -140658382170500404L;

}
