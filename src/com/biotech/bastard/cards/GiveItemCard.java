/**
 * 
 */
package com.biotech.bastard.cards;

import processing.core.PApplet;

import com.biotech.bastard.actions.GiveItem;

/**
 * Created: Aug 24, 2014
 * 
 * @author Brian Holman
 *
 */
public class GiveItemCard extends Card {

	GiveItem i = new GiveItem();

	/**
	 * @param owner
	 */
	public GiveItemCard(PApplet owner) {
		super(owner);
		// TODO Auto-generated constructor stub
	}

	/*
	 * @see com.biotech.bastard.cards.Card#getImagePath()
	 */
	@Override
	protected String getImagePath() {
		// TODO Auto-generated method stub
		return "itemRed.png";
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
		// TODO Auto-generated method stub
		return "Give Item";
	}

}
