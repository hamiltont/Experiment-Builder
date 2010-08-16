package edu.vanderbilt.psychology.controller.toolbarActions.util;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

@SuppressWarnings("serial")
public class CreateListDialog extends JDialog {

	public static final int RESULT_OK = 1 << 0;
	public static final int RESULT_CANCEL = 1 << 1;

	public CreateListDialog() {
		super((JFrame) null, "Create List", true);

		JPanel ui = buildStepOneUI();
		setContentPane(ui);
		pack();
		
		setVisible(true);
	}

	private JPanel buildStepOneUI() {
		JPanel ui = new JPanel(new BorderLayout());
		JLabel instructions = new JLabel(
				"What kind of data will this list hold?");
		ui.add(instructions, BorderLayout.NORTH);

		ButtonGroup bg = new ButtonGroup();
		JRadioButton files = new JRadioButton(
				"Files (Image files, Audio files, etc)");
		JRadioButton strings = new JRadioButton("Strings");
		JRadioButton numbers = new JRadioButton("Numbers");
		JRadioButton lists = new JRadioButton("Other lists");

		bg.add(files);
		bg.add(strings);
		bg.add(numbers);
		bg.add(lists);
		bg.setSelected(files.getModel(), true);

		JPanel buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));
		buttons.add(files);
		buttons.add(strings);
		buttons.add(numbers);
		buttons.add(lists);

		ui.add(buttons, BorderLayout.CENTER);

		JPanel continueCancel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton cancel = new JButton("Cancel");
		JButton cont = new JButton("Continue");
		continueCancel.add(cancel);
		continueCancel.add(cont);
		
		ui.add(continueCancel, BorderLayout.SOUTH);

		return ui;
	}
}
