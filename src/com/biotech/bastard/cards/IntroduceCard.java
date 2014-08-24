/**
 * 
 */
package com.biotech.bastard.cards;

import processing.core.PApplet;

import com.biotech.bastard.actions.Introduce;

/**
 * Created: Aug 24, 2014
 * 
 * @author Brian Holman
 *
 */
public class IntroduceCard extends Card {
	Introduce i = new Introduce();

	/**
	 * @param owner
	 */
	public IntroduceCard(PApplet owner) {
		super(owner);
		i.setType(Introduce.Type.BAD);
	}

	/*
	 * @see com.biotech.bastard.cards.Card#getImagePath()
	 */
	@Override
	protected String getImagePath() {
		return "introduceRed.png";
	}

	/*
	 * @see com.biotech.bastard.cards.Card#getAction()
	 */
	@Override
	public Action getAction() {
		// TODO Auto-generated method stub
		return i;
	}

	/*
	 * @see com.biotech.bastard.cards.Card#getName()
	 */
	@Override
	public String getName() {
		return i.getName();
	}
}
