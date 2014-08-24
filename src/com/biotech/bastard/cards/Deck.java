/**
 * 
 */
package com.biotech.bastard.cards;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import processing.core.PApplet;

/**
 * Created: Aug 24, 2014
 * 
 * @author Brian Holman
 *
 */
public class Deck {
	private static transient final Logger LOGGER = LoggerFactory.getLogger(Deck.class);

	public Class<?>[] cardTypes = {
			GossipCard.class,
			GiveItemCard.class,
			IntroduceCard.class,
			MoodChangeCard.class };

	public ArrayList<Card> cards;

	/**
	 * 
	 */
	public Deck(PApplet p) {
		cards = new ArrayList<Card>();
		for (int i = 0; i < 30; i++) {
			try {
				cards.add((Card) cardTypes[i % cardTypes.length].getConstructor(PApplet.class).newInstance(p));
			} catch (Exception e) {
				LOGGER.error("Failed to create {}", cardTypes[i % cardTypes.length]);
			}
		}
	}
}
