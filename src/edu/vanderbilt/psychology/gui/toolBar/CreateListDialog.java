package edu.vanderbilt.psychology.gui.toolBar;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

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

		JButton cont = new JButton("Continue");
		cont.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				dispose();

				JFrame listDialog = new JFrame("List Dialog");
				listDialog.setLayout(new FlowLayout());

				JPanel ui = new JPanel(new BorderLayout());
				listDialog.add(ui);
				listDialog.setContentPane(ui);
				listDialog.pack();

				listDialog.setVisible(true);

				JPanel listEntry = new JPanel(new FlowLayout());

				JTextField typeListEntry = new JTextField(
						"Type strings and hit enter to put them in the list");
				JButton enter = new JButton("Enter");
				listEntry.add(typeListEntry);
				listEntry.add(enter, FlowLayout.RIGHT);

				ui.add(listEntry, BorderLayout.NORTH);

				JPanel listArea = new JPanel(new FlowLayout());

				JList list = new JList();
				list.setLayoutOrientation(JList.VERTICAL);
				JScrollPane listPane = new JScrollPane(list);
				JButton remove = new JButton("Remove String");
				listArea.add(listPane, FlowLayout.LEFT);
				listArea.add(remove, FlowLayout.RIGHT);

				ui.add(listArea, BorderLayout.CENTER);

				JPanel cancelOk = new JPanel(new FlowLayout());

				JButton ok = new JButton("Ok");
				ok.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						dispose();
					}
				});
				JButton cancel = new JButton("Cancel");
				cancel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						dispose();
					}

				});

				cancelOk.add(cancel, FlowLayout.CENTER);
				cancelOk.add(ok, FlowLayout.RIGHT);

				ui.add(cancelOk, BorderLayout.SOUTH);
			}
		});

		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				dispose();
			}
		});

		// @Seth, this is where you should register button listeners for the
		// continue and cancel buttons. Please have the listeners perform the
		// appropriate actions e.g. the cancel button should close the create
		// list dialog and the continue button should close the create list
		// dialog and then open a dialog that will allow a user to input some
		// list data

		continueCancel.add(cancel);
		continueCancel.add(cont);

		ui.add(continueCancel, BorderLayout.SOUTH);

		return ui;
	}
}
