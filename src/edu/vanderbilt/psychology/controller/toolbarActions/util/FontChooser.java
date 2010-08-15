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
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
	
	private JComboBox fontNamesCombo_;
	private final JComboBox fontSizesCombo_ = new JComboBox(new String[] { "8",
			"9", "10", "12", "14", "16", "18", "20", "22", "24", "26", "28",
			"36", "48", "72" });
	private final JCheckBox boldCheck_ = new JCheckBox("Bold");
	private final JCheckBox italicCheck_ = new JCheckBox("Italic");
	private final JLabel previewLabel_ = new JLabel("PreviewText");
	final JColorChooser colorChooser = new JColorChooser();

	private Font font_;

	private PreviewLabelUpdateListener previewUpdateListener_;

	public FontChooser() {
		super((JFrame) null, "Font Chooser", true);
		createUserInterface();

		previewUpdateListener_ = new PreviewLabelUpdateListener();
		fontNamesCombo_.addActionListener(previewUpdateListener_);
		fontSizesCombo_.addActionListener(previewUpdateListener_);
		boldCheck_.addActionListener(previewUpdateListener_);
		italicCheck_.addActionListener(previewUpdateListener_);

		fontNamesCombo_.setSelectedIndex(0);
		fontSizesCombo_.setSelectedIndex(0);
		boldCheck_.setSelected(false);
		italicCheck_.setSelected(false);

		updateFontFromDialog();
		
		setVisible(true);
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

	private void createUserInterface() {
		final JPanel content = new JPanel(new GridBagLayout());
		final GridBagConstraints gbc = new GridBagConstraints();

		setContentPane(content);
		content.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(2, 2, 2, 2);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		gbc.gridx = gbc.gridy = 0;
		content.add(new JLabel("Font"), gbc);

		++gbc.gridx;
		content.add(new JLabel("Size"), gbc);

		colorChooser.setPreviewPanel(new JPanel());

		++gbc.gridx;
		content.add(new JLabel("Style"), gbc);

		++gbc.gridy;
		gbc.gridx = 0;
		fontNamesCombo_ = new JComboBox(GraphicsEnvironment
				.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
		content.add(fontNamesCombo_, gbc);

		++gbc.gridx;
		fontSizesCombo_.setEditable(true);
		content.add(fontSizesCombo_, gbc);

		++gbc.gridx;
		content.add(boldCheck_, gbc);
		++gbc.gridy;
		content.add(italicCheck_, gbc);

		gbc.gridx = 0;
		++gbc.gridy;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		content.add(createPreviewPanel(), gbc);

		++gbc.gridy;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.LAST_LINE_START;
		content.add(createButtonsPanel(), gbc);

		// gbc.anchor = GridBagConstraints.SOUTH;
		// content.add(createColorPanel(), gbc);

		pack();
		setResizable(true);
	}

	private JPanel createPreviewPanel() {
		final JPanel pnl = new JPanel(new BorderLayout());
		pnl.setBorder(BorderFactory.createTitledBorder("Preview"));
		Dimension prefSize = previewLabel_.getPreferredSize();
		prefSize.height = 50;
		previewLabel_.setPreferredSize(prefSize);
		pnl.add(previewLabel_, BorderLayout.CENTER);
		updatePreviewLabel();

		ColorSelectionModel model = colorChooser.getSelectionModel();
		ChangeListener changeListener = new ChangeListener() {
			public void stateChanged(ChangeEvent changeEvent) {
				Color newForegroundColor = colorChooser.getColor();
				previewLabel_.setForeground(newForegroundColor);
			}
		};
		model.addChangeListener(changeListener);

		return pnl;
	}

	private JPanel createButtonsPanel() {
		JPanel pnl = new JPanel();
		pnl.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		JButton okBtn = new JButton("OK");
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
				FontChooser.this.font_ = null;
				dispose();
			}
		});

		c.weightx = 0.5;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(10, 0, 0, 0);
		pnl.add(okBtn, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 1;
		c.gridy = 1;
		c.insets = new Insets(10, 0, 0, 0);
		pnl.add(cancelBtn, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 40; // make this component tall
		c.weightx = 0.0;
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 0;
		pnl.add(colorChooser, c);

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
