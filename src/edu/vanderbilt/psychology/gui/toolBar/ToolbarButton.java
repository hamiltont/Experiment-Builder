package edu.vanderbilt.psychology.gui.toolBar;

import java.awt.Font;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * Provides a common button look and feel
 * 
 * @todo Add in a method for creating a new button using only an action and some
 *       string. This will make it easier for plugin developers to get started
 *       right away on the core pieces of their code, rather than having to
 *       create an ImageIcon first
 * @author hamiltont
 * 
 */
public class ToolbarButton extends JButton {

	private final static int TOOLBAR_FONT_SIZE = 12;
	private static final Font TOOLBAR_FONT = new Font(Font.SANS_SERIF,
			Font.PLAIN, TOOLBAR_FONT_SIZE);

	/**
	 * 
	 * @param action
	 * @param imageIconPath
	 *            such as "images/export_icon.png"
	 * @param text
	 */
	public ToolbarButton(AbstractAction action, String imageIconPath,
			String text) {
		this(action, new ImageIcon(imageIconPath), text, text);
	}

	/**
	 * 
	 * @param action
	 * @param icon
	 * @param text
	 *            used as the text shown below the toolbar button
	 * @param toolTipText
	 *            used as the toolTip text (typically shown when the mouse
	 *            hovers over the button)
	 */
	public ToolbarButton(AbstractAction action, ImageIcon icon, String text,
			String toolTipText) {
		// You have to call the set...TextPosition functions before setting the
		// icon, the text, or the action
		super();

		setVerticalTextPosition(AbstractButton.BOTTOM);
		setHorizontalTextPosition(AbstractButton.CENTER);
		setAction(action);
		setFont(TOOLBAR_FONT);
		setIcon(icon);
		setBorderPainted(false);
		setOpaque(false);
		setText(text);
		setToolTipText(toolTipText);

	}

	/** Provide a Universal ID for serialization */
	private static final long serialVersionUID = -4435758144141920348L;

}
