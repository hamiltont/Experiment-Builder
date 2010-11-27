/*
 * Copyright (C) 2001-2004 Colin Bell
 * colbell@users.sourceforge.net
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package edu.vanderbilt.psychology.controller.toolbarActions.util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * A dialog allow selection and a font and its associated info.
 * 
 * @author <A HREF="mailto:colbell@users.sourceforge.net">Colin Bell</A>
 * @author Modified by Hamilton Turner
 */
public class FontChooser extends JDialog {

	private static final long serialVersionUID = -8475243513671516259L;

	public enum Result {
		OK, CANCEL
	};

	private Result result_ = Result.CANCEL;

	private JComboBox fontNamesCombo_ = new JComboBox(GraphicsEnvironment
			.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());

	private static final String[] possibleFontSizes = new String[] { "8", "9",
			"10", "12", "14", "16", "18", "20", "22", "24", "26", "28", "36",
			"48", "72" };
	private static final int DEFAULT_FONT_SIZE_INDEX = 8;

	private final JComboBox fontSizesCombo_ = new JComboBox(possibleFontSizes);

	private final JCheckBox boldCheck_ = new JCheckBox("Bold", false);
	private final JCheckBox italicCheck_ = new JCheckBox("Italic", false);
	private final JTextArea previewLabel_ = new JTextArea(
			"Type or Paste your text here!", 5, 10);
	final JColorChooser colorChooser = new JColorChooser();

	private Font font_;

	private PreviewLabelUpdateListener previewUpdateListener_;

	public FontChooser() {
		super((JFrame) null, "Font Chooser", true);
		fontSizesCombo_.setEditable(true);

		// TODO Removed the ability to hit return until the TextElement can
		// display multi-line text
		// TODO An error message would be nice
		previewLabel_.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					e.consume();
			}
		});
		previewLabel_.setLineWrap(true);
		

		colorChooser.setColor(102, 102, 102);
		ColorSelectionModel model = colorChooser.getSelectionModel();
		ChangeListener changeListener = new ChangeListener() {
			public void stateChanged(ChangeEvent changeEvent) {
				Color newForegroundColor = colorChooser.getColor();
				previewLabel_.setForeground(newForegroundColor);
			}
		};
		model.addChangeListener(changeListener);

		createUserInterface();

		previewUpdateListener_ = new PreviewLabelUpdateListener();
		fontNamesCombo_.addActionListener(previewUpdateListener_);
		fontSizesCombo_.addActionListener(previewUpdateListener_);
		boldCheck_.addActionListener(previewUpdateListener_);
		italicCheck_.addActionListener(previewUpdateListener_);

		fontNamesCombo_.setSelectedIndex(0);
		fontSizesCombo_.setSelectedIndex(DEFAULT_FONT_SIZE_INDEX);

		updateFontFromDialog();

		setVisible(true);
	}

	public String getText() {
		return previewLabel_.getText();
	}

	public Result getResult() {
		return result_;
	}

	public Font getFont() {
		return font_;
	}

	public Color getColor() {
		return colorChooser.getColor();
	}

	// Create preview panel, font options, color picker
	private void createUserInterface() {
		final JPanel content = new JPanel(new BorderLayout());
		setContentPane(content);
		content.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		content.add(createPreviewPanel(), BorderLayout.CENTER);

		JPanel optionsAndSubmit = new JPanel(new BorderLayout());
		optionsAndSubmit.add(createFontPanel(), BorderLayout.NORTH);
		optionsAndSubmit.add(createColorPanel(), BorderLayout.CENTER);
		optionsAndSubmit.add(createButtonsPanel(), BorderLayout.SOUTH);

		content.add(optionsAndSubmit, BorderLayout.SOUTH);

		pack();
		setResizable(true);
	}

	private JPanel createFontPanel() {
		JPanel fontPanel = new JPanel(new BorderLayout());
		fontPanel.setBorder(BorderFactory.createTitledBorder("Font Options"));

		JPanel topFontOptions = new JPanel(new FlowLayout(FlowLayout.LEFT));
		topFontOptions.add(boldCheck_);
		topFontOptions.add(italicCheck_);
		fontPanel.add(topFontOptions, BorderLayout.NORTH);

		JPanel bottomFontOptions = new JPanel(new BorderLayout());
		bottomFontOptions.add(fontNamesCombo_, BorderLayout.CENTER);
		bottomFontOptions.add(fontSizesCombo_, BorderLayout.EAST);

		fontPanel.add(bottomFontOptions, BorderLayout.SOUTH);

		return fontPanel;
	}

	private JPanel createColorPanel() {
		// TODO This works for now, but the entire font options UI looks 100%
		// better if the
		// initial width is about half the size of what the default color
		// chooser needs. We can mitigate this problem by defining a very simply
		// AbstractColorChooserPanel and setting it as the default, and then
		// never adding the color chooser itself, just adding selection options
		// to switch to other color choosers. This would allow us to manually
		// pull those other color choosers and display them, animating out the
		// widening of the window as we go. That would look a lot better.
		// Eventually it would be nice to allow users to set a default color
		// chooser that they would like to use, but that is a ways away
		JPanel colorPanel = new JPanel();
		colorChooser.setPreviewPanel(new JPanel(false));
		colorPanel.setBorder(BorderFactory.createTitledBorder("Color Options"));
		colorPanel.add(colorChooser);
		return colorPanel;
	}

	private JPanel createPreviewPanel() {
		final JPanel pnl = new JPanel(new BorderLayout());
		pnl.setBorder(BorderFactory.createTitledBorder("Preview"));

		Dimension prefSize = previewLabel_.getPreferredSize();
		// TODO - change previewLabel to a JTextArea or equivalent
		if (prefSize.width >= 300)
			prefSize.width = 300;
		previewLabel_.setPreferredSize(prefSize);

		pnl.add(previewLabel_, BorderLayout.CENTER);
		updatePreviewLabel();

		return pnl;
	}

	private JPanel createButtonsPanel() {
		JPanel pnl = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		JButton okBtn = new JButton("Accept");
		okBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				result_ = Result.OK;
				updateFontFromDialog();
				dispose();
			}
		});

		JButton cancelBtn = new JButton("Cancel");
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				dispose();
			}
		});

		pnl.add(cancelBtn);
		pnl.add(okBtn);

		getRootPane().setDefaultButton(okBtn);

		return pnl;
	}

	protected void updateFontFromDialog() {
		int size = 12;
		try {
			size = Integer.parseInt((String) fontSizesCombo_.getSelectedItem());
		} catch (Exception e) {
		}

		FontBuilder.family = (String) fontNamesCombo_.getSelectedItem();
		FontBuilder.size = size;
		FontBuilder.bold = boldCheck_.isSelected();
		FontBuilder.italic = italicCheck_.isSelected();
		font_ = FontBuilder.createFont();
	}

	private void updatePreviewLabel() {
		updateFontFromDialog();
		previewLabel_.setFont(font_);
		Color newColor = colorChooser.getColor();
		previewLabel_.setForeground(newColor);
	}

	private final class PreviewLabelUpdateListener implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			updatePreviewLabel();
		}
	}
}
