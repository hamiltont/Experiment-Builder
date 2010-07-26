package edu.vanderbilt.psychology;

import java.util.List;

public class XMLFile {
	private List<Card> cards_;
	private Card distractor_;
	
	public void setCards(List<Card> cards_) {
		this.cards_ = cards_;
	}

	public List<Card> getCards() {
		return cards_;
	}

	public void setDistractor(Card distractor_) {
		this.distractor_ = distractor_;
	}

	public Card getDistractor() {
		return distractor_;
	}

}
