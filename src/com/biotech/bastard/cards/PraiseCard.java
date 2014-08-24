/**
 * 
 */
package com.biotech.bastard.cards;

import processing.core.PApplet;

import com.biotech.bastard.actions.Inform;
import com.biotech.bastard.actions.Inform.Type;

/**
 * Created: Aug 24, 2014
 * 
 * @author Brian Holman
 *
 */
public class PraiseCard extends Card {

	Inform i = new Inform();

	/**
	 * @param owner
	 */
	public PraiseCard(PApplet owner) {
		super(owner);
		i.setType(Type.PRAISE);
	}

	/*
	 * @see com.biotech.bastard.cards.Card#getImagePath()
	 */
	@Override
	protected String getImagePath() {
		return "informGreen.png";
	}

	/*
	 * @see com.biotech.bastard.cards.Card#getAction()
	 */
	@Override
	public Action getAction() {
		return i;
	}

	/*
	 * @see com.biotech.bastard.cards.Card#getName()
	 */
	@Override
	public String getName() {
		return "Inform";
	}
}
