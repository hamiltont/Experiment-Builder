package edu.vanderbilt.psychology;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import edu.vanderbilt.psychology.TrialLayout.TargetOrder;

public class ThreeDimensionalStrategy {
	private List<Card> targetCards_;
	private List<Card> testCards_;
	private Card distractor_;
	private static final String shape_instructions = "We are playing the shape game";
	private static final String size_instructions = "We are playing the size game";
	private static final String color_instructions = "We are playing the color game";

	private Random random_;
	private ExcelWriter excel_;

	private enum Game {
		SHAPE, SIZE, COLOR
	};

	private ArrayList<Game> remainingGames = new ArrayList<Game>();

	public void prepare(List<Card> images, Card distractor) {
		random_ = new Random();
		distractor_ = distractor;
		findTargets(images);
		findTestCards(images);

		remainingGames.add(Game.SHAPE);
		remainingGames.add(Game.COLOR);
		remainingGames.add(Game.SIZE);
	}
	
	public void setFilename(String s) {
		excel_ = new ExcelWriter(s);
	}

	public void playNextGame(JFrame frame) {
		if (frame != null)
			frame_ = frame;

		if (remainingGames.size() == 0) {
			// We are done, do something
			frame_.dispose();
			excel_.finish();
			return;
		}
		int next = random_.nextInt(remainingGames.size());
		Game nextGame = remainingGames.remove(next);

		currentTrial_ = 0;
		runGame(frame_, nextGame);
	}

	public void gameNext(Card trialCard, Card selectedTarget,
			long millisecondsToClick, List<TargetOrder> targetOrder,
			List<Card> targetsInOrder) {

		boolean correct = false;
		switch (currentGame_) {
		case COLOR:
			correct = trialCard.getColor_() == selectedTarget.getColor_() ? true
					: false;
			break;
		case SIZE:
			correct = trialCard.getSize_() == selectedTarget.getSize_() ? true
					: false;
			break;
		case SHAPE:
			correct = trialCard.getShape_() == selectedTarget.getShape_() ? true
					: false;
			break;
		}

		excel_.addRow(currentGame_.toString(), currentTrial_ + 1, correct,
				millisecondsToClick, trialCard, selectedTarget, targetsInOrder);

		if (currentTrial_ == 5) {
			frame_.remove(tl_);
			frame_.invalidate();
			frame_.repaint();
			frame_.validate();

			playNextGame(null);
			return;
		}
		
		++currentTrial_;
		Card nextTextCard = testCards_.get(currentTrial_);

		TrialLayout tl = new TrialLayout(targetCards_, nextTextCard,
				distractor_, "", this, targetOrder, false);

		frame_.remove(tl_);
		frame_.add(tl);
		tl_ = null;
		tl_ = tl;

		frame_.invalidate();
		frame_.repaint();
		frame_.validate();

	}

	public void resumeGame() {
		sp_.setVisible(false);
		frame_.remove(sp_);
		frame_.add(tl_);

		frame_.invalidate();
		frame_.repaint();
		frame_.validate();
	}

	private JFrame frame_;
	private TrialLayout tl_;
	private StartPanel sp_;
	private int currentTrial_ = 0;
	private String instructions;
	private Game currentGame_;

	private void runGame(final JFrame frame, Game g) {
		frame_ = frame;

		// show start

		currentGame_ = g;
		switch (g) {
		case COLOR:
			instructions = color_instructions;
			break;
		case SHAPE:
			instructions = shape_instructions;
			break;
		case SIZE:
		default:
			instructions = size_instructions;
			break;
		}

		final StartPanel sp = new StartPanel(instructions, this);
		sp_ = sp;
		final TrialLayout tl = new TrialLayout(targetCards_, testCards_.get(0),
				distractor_, "", this, null, true);
		tl_ = tl;

		frame.add(sp);
		frame.invalidate();
		frame.repaint();
		frame.validate();

		// run game

	}

	/**
	 * Post: testCards contains 6 cards that are compatible with the selected
	 * target cards. In order to be compatible, the test card must match the
	 * target card on one and only one feature.
	 * 
	 * Right now there is no handling of the case where 6 usable cards cannot be
	 * found
	 * 
	 * @param images
	 */
	private void findTestCards(List<Card> availableCards) {
		testCards_ = new ArrayList<Card>();

		while (testCards_.size() != 6 && !availableCards.isEmpty()) {
			Card c = returnRandomCard(availableCards);
			boolean ok = verifyTestCard(c);
			if (ok)
				testCards_.add(c);

			// We want to remove it either b/c we can use it as a test, or we
			// cannot use it and there is no reason to keep it
			availableCards.remove(c);
		}

		if (testCards_.size() != 6)
			throw new IllegalStateException(
					"Unable to find 6 test cards that match the targets");
	}

	/**
	 * Given a card, checks to see if it matches each target card on one and
	 * only one feature. If so, returns true. Else, returns false
	 */
	private boolean verifyTestCard(Card c) {

		int matchedProperties;
		for (Card target : targetCards_) {
			matchedProperties = 0;

			if (target.getColor_() == c.getColor_())
				++matchedProperties;
			if (target.getSize_() == c.getSize_())
				++matchedProperties;
			if (target.getShape_() == c.getShape_())
				++matchedProperties;

			if (matchedProperties != 1)
				return false;
		}

		return true;
	}

	/**
	 * Post: targetCards contains 3 cards, each with a different shape,
	 * different color, different size. Cards used are removed from the
	 * availableCards parameter
	 * 
	 * Works on a simple principle: 1 - get all combinations of cards 2 - pick a
	 * random combo 3 - verify that combo 4 - repeat 2-3 until a valid
	 * combination is found or we are out of combos
	 */
	private void findTargets(List<Card> availableCards) {

		// 1
		CombinationGenerator cn = new CombinationGenerator(availableCards
				.size(), 3);
		ArrayList<Tuple> combinations = new ArrayList<Tuple>();
		int[] next, copy;
		while (cn.hasMore()) {
			next = cn.getNext();
			copy = new int[3];
			copy[0] = next[0];
			copy[1] = next[1];
			copy[2] = next[2];
			combinations.add(new Tuple(copy));
		}

		// 2 - 4
		while (combinations.size() != 0) {
			int randomCombo = random_.nextInt(combinations.size());
			Tuple combo = combinations.get(randomCombo);

			boolean ok = verifyCombination(combo, availableCards);
			if (ok) {
				targetCards_ = new ArrayList<Card>();

				List<Card> cards = availableCards;
				Card first = cards.get(combo.getFirst()), second = cards
						.get(combo.getSecond()), third = cards.get(combo
						.getThird());

				targetCards_.add(first);
				targetCards_.add(second);
				targetCards_.add(third);

				availableCards.remove(first);
				availableCards.remove(second);
				availableCards.remove(third);

				combinations = null;
				break;
			} else
				// don't try this one again
				combinations.remove(combo);
		}

		if (targetCards_ == null)
			throw new IllegalStateException(
					"There is no possible set of target cards!");

	}

	private boolean verifyCombination(Tuple combo, List<Card> cards) {
		Card first = cards.get(combo.getFirst()), second = cards.get(combo
				.getSecond()), third = cards.get(combo.getThird());

		boolean ok = verifyTargetCards(first, second);
		if (!ok)
			return false;
		ok = verifyTargetCards(second, third);
		if (!ok)
			return false;
		ok = verifyTargetCards(first, third);
		if (!ok)
			return false;

		return ok;
	}

	/**
	 * Given a target card, returns true if it is compatible with the other
	 * target cards. Aka shares no properties(size/shape/color) with any of the
	 * current target cards
	 * 
	 * @param one
	 * @return
	 */
	private boolean verifyTargetCards(Card one, Card two) {

		if (two.getColor_() == one.getColor_()
				|| two.getShape_() == one.getShape_()
				|| two.getSize_() == one.getSize_()) {

			return false;
		}

		return true;
	}

	private Card returnRandomCard(List<Card> cardSet) {
		final int position = random_.nextInt(cardSet.size());
		return cardSet.get(position);
	}

}
