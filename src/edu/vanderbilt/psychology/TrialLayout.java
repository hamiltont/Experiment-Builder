package edu.vanderbilt.psychology;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class TrialLayout extends JPanel {

	private JLabel cardOneContainer;
	private JLabel cardTwoContainer;
	private JLabel cardThreeContainer;
	private JLabel cardFourContainer;
	private JLabel trialCardContainer_;

	private Random random_;
	private List<TargetOrder> targetOrder_;
	private List<Card> targets_;
	private Card testCard_;
	private Card distractor_;

	private ThreeDimensionalStrategy tds_;

	public enum TargetOrder {
		ONE, TWO, THREE, DISTRACTOR
	}

	private long startTime_;
	

	long tempStartTime_;

	public TrialLayout(List<Card> targets, Card testCard, Card distractor,
			String instructions, ThreeDimensionalStrategy tds,
			List<TargetOrder> targetOrder, boolean hideTrialCardUntilClick) {
		super();

		tds_ = tds;
		targets_ = targets;
		testCard_ = testCard;
		distractor_ = distractor;

		random_ = new Random();
		if (targetOrder == null)
			fillTargetOrder();
		else
			targetOrder_ = targetOrder;

		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		add(Box.createVerticalGlue());

		ImageIcon test = testCard.getImage();
		JLabel testContainer = new JLabel(test);
		testContainer.setAlignmentX(CENTER_ALIGNMENT);
		testContainer.setAlignmentY(CENTER_ALIGNMENT);
		testContainer.setMaximumSize(testCard.getImagePreferredSize());
		testContainer.setMinimumSize(testCard.getImagePreferredSize());

		if (hideTrialCardUntilClick) {
			testContainer.setVisible(false);
			addMouseListener(new VisibilityListener());
		}
		trialCardContainer_ = testContainer;

		add(testContainer);

		add(Box.createVerticalGlue());

		String instruction;
		if (instructions == null)
			instruction = "";
		else
			instruction = instructions;
		JLabel instructionContainer = new JLabel(instruction);
		instructionContainer.setAlignmentX(CENTER_ALIGNMENT);
		instructionContainer.setAlignmentY(CENTER_ALIGNMENT);
		instructionContainer.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 36));
		add(instructionContainer);

		add(Box.createVerticalGlue());

		JPanel cardPane = new JPanel();
		cardPane.setLayout(new BoxLayout(cardPane, BoxLayout.LINE_AXIS));
		cardPane.setAlignmentX(CENTER_ALIGNMENT);
		cardPane.setAlignmentY(CENTER_ALIGNMENT);

		TargetOrder current = targetOrder_.get(0);
		ImageIcon ic = createCardImage(current, targets, distractor);
		cardOneContainer = new JLabel(ic);
		cardOneContainer.setMaximumSize(new Dimension(ic.getIconWidth(), ic
				.getIconHeight()));

		current = targetOrder_.get(1);
		ic = createCardImage(current, targets, distractor);
		cardTwoContainer = new JLabel(ic);
		cardTwoContainer.setMaximumSize(new Dimension(ic.getIconWidth(), ic
				.getIconHeight()));

		current = targetOrder_.get(2);
		ic = createCardImage(current, targets, distractor);
		cardThreeContainer = new JLabel(ic);
		cardThreeContainer.setMaximumSize(new Dimension(ic.getIconWidth(), ic
				.getIconHeight()));

		current = targetOrder_.get(3);
		ic = createCardImage(current, targets, distractor);
		cardFourContainer = new JLabel(ic);
		cardFourContainer.setMaximumSize(new Dimension(ic.getIconWidth(), ic
				.getIconHeight()));

		cardPane.add(Box.createHorizontalGlue());
		cardPane.add(cardOneContainer);
		cardPane.add(Box.createHorizontalGlue());
		cardPane.add(cardTwoContainer);
		cardPane.add(Box.createHorizontalGlue());
		cardPane.add(cardThreeContainer);
		cardPane.add(Box.createHorizontalGlue());
		cardPane.add(cardFourContainer);
		cardPane.add(Box.createHorizontalGlue());

		add(cardPane);

		add(Box.createVerticalGlue());

		if (hideTrialCardUntilClick == false) {
			TargetListener tl = new TargetListener();
			cardOneContainer.addMouseListener(tl);
			cardTwoContainer.addMouseListener(tl);
			cardThreeContainer.addMouseListener(tl);
			cardFourContainer.addMouseListener(tl);
			
			startTime_ = System.currentTimeMillis();
		} else
			tempStartTime_ = System.currentTimeMillis();

	}

	private ImageIcon createCardImage(TargetOrder current, List<Card> targets,
			Card distractor) {

		ImageIcon ic = null;

		switch (current) {
		case ONE:
			ic = targets.get(0).getImage();
			break;
		case TWO:
			ic = targets.get(1).getImage();
			break;
		case THREE:
			ic = targets.get(2).getImage();
			break;
		case DISTRACTOR:
			ic = distractor.getImage();
			break;
		default:
			ic = distractor.getImage();
			break;
		}

		return ic;
	}

	/**
	 * Post: targetOrder has a random order of length 4
	 */
	private void fillTargetOrder() {
		targetOrder_ = new ArrayList<TargetOrder>(4);
		List<TargetOrder> possibleValues = new ArrayList<TargetOrder>(4);
		possibleValues.add(TargetOrder.DISTRACTOR);
		possibleValues.add(TargetOrder.ONE);
		possibleValues.add(TargetOrder.TWO);
		possibleValues.add(TargetOrder.THREE);

		while (targetOrder_.size() != 4) {
			int next = random_.nextInt(possibleValues.size());
			TargetOrder to = possibleValues.remove(next);
			targetOrder_.add(to);
		}
	}

	private class TargetListener extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {

			long currentTime = System.currentTimeMillis();

			int selectedTarget;

			if (e.getSource().equals(cardOneContainer))
				selectedTarget = 0;
			else if (e.getSource().equals(cardTwoContainer))
				selectedTarget = 1;
			else if (e.getSource().equals(cardThreeContainer))
				selectedTarget = 2;
			else
				selectedTarget = 3;

			TargetOrder selected = targetOrder_.get(selectedTarget);
			Card targetSelected = null;
			switch (selected) {
			case ONE:
				targetSelected = targets_.get(0);
				break;
			case TWO:
				targetSelected = targets_.get(1);
				break;
			case THREE:
				targetSelected = targets_.get(2);
				break;
			case DISTRACTOR:
			default:
				targetSelected = distractor_;
				break;
			}

			long millisecondsToClick = currentTime - startTime_;

			ArrayList<Card> targetsInOrder = new ArrayList<Card>(4);
			for (TargetOrder order : targetOrder_) {
				switch (order) {
				case ONE:
					targetsInOrder.add(targets_.get(0));
					break;
				case TWO:
					targetsInOrder.add(targets_.get(1));
					break;
				case THREE:
					targetsInOrder.add(targets_.get(2));
					break;
				case DISTRACTOR:
				default:
					targetsInOrder.add(distractor_);
					break;
				}
			}

			tds_.gameNext(testCard_, targetSelected, millisecondsToClick,
					targetOrder_, targetsInOrder);

			/*
			 * // String results = "\nCard #" + cardNum + " Trial #" + trialNum
			 * + // " Block #" + blockNum; File file = new File("Results.txt");
			 * BufferedWriter out; try { out = new BufferedWriter(new
			 * FileWriter(file)); // OutputWriter writer = new OutputWriter();
			 * // writer.setContents(file, results); // FileWriter always
			 * assumes default encoding is OK! // out.write(results); } catch
			 * (IOException e2) { // TODO Auto-generated catch block
			 * e2.printStackTrace(); } // exp_.showNextTrial();
			 */

		}

	}

	private class VisibilityListener extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			
			TargetListener tl = new TargetListener();
			cardOneContainer.addMouseListener(tl);
			cardTwoContainer.addMouseListener(tl);
			cardThreeContainer.addMouseListener(tl);
			cardFourContainer.addMouseListener(tl);
			
			trialCardContainer_.setVisible(true);
			trialCardContainer_.setEnabled(true);
			TrialLayout.this.removeMouseListener(this);

			startTime_ = System.currentTimeMillis();
		}
	}
}
