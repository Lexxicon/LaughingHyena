/**
 * 
 */
package com.biotech.bastard.cards;

import processing.core.PApplet;

import com.biotech.bastard.actions.ChangeMood;

/**
 * Created: Aug 24, 2014
 * 
 * @author Brian Holman
 *
 */
public class MoodChangeCard extends Card {

	ChangeMood mc = new ChangeMood();

	/**
	 * 
	 */
	public MoodChangeCard(PApplet p) {
		super(p);
	}

	/**
	 * @see com.biotech.bastard.cards.Card#getAction()
	 */
	@Override
	public Action getAction() {
		return mc;
	}

	/*
	 * @see com.biotech.bastard.cards.Card#getName()
	 */
	@Override
	public String getName() {
		return "Mood Change";
	}

	/*
	 * @see com.biotech.bastard.cards.Card#getImagePath()
	 */
	@Override
	protected String getImagePath() {
		// TODO Auto-generated method stub
		return "moodChangeRed.png";
	}

}
