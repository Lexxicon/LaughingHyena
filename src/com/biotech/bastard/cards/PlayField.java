/**
 * 
 */
package com.biotech.bastard.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.biotech.bastard.Person;

/**
 * Created: Aug 23, 2014
 * 
 * @author Brian Holman
 *
 */
public class PlayField {

	private static transient final Logger LOGGER = LoggerFactory.getLogger(PlayField.class);

	private Stack<Card> deck;
	private Stack<Card> discard;
	private Stack<Card> removed;
	private List<Card> hand;

	/**
	 * 
	 */
	public PlayField() {
		deck = new Stack<>();
		discard = new Stack<>();
		removed = new Stack<>();
		hand = new ArrayList<>();
	}

	public void addCardToDeck(Card card) {
		deck.push(card);
	}

	public void shuffleDeck() {
		Collections.shuffle(deck);
	}

	public void shuffleDiscard() {
		Collections.shuffle(discard);
	}

	public void mergeDiscard() {
		deck.addAll(discard);
		discard.clear();
		shuffleDeck();
	}

	public boolean drawCard() {
		if (deck.size() == 0) {
			return false;
		}
		hand.add(deck.pop());
		return true;
	}

	public void playCard(Card card, Person target) {
		LOGGER.info("Playing card {} on {}", card.getName(), target.getName());
		target.addAction(card.getAction());
		hand.remove(card);
		discard.add(card);
	}

	public void removeCard(Card card) {
		deck.remove(card);
		discard.remove(card);
		hand.remove(card);
		removed.push(card);
	}

	public List<Card> getHand() {
		return hand;
	}

	public int getDeckCount() {
		return deck.size();
	}

	public int getDiscardCount() {
		return discard.size();
	}
}
