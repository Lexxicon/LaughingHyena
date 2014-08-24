/**
 * 
 */
package com.biotech.bastard.cards;

import processing.core.PApplet;

import com.biotech.bastard.people.Person;

/**
 * Created: Aug 24, 2014
 * 
 * @author Brian Holman
 *
 */
public class MoodChangeCard extends Card {

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
		// TODO Auto-generated method stub
		return new Action() {

			@Override
			public boolean validAction(Person person) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void performAction(Person person) {
				// TODO Auto-generated method stub

			}

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "Mood Change";
			}
		};
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
