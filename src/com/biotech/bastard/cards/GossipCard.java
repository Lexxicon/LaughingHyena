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
public class GossipCard extends Card {

	Inform i = new Inform();

	/**
	 * @param owner
	 */
	public GossipCard(PApplet owner) {
		super(owner);
		i.setType(Type.GOSSIP);
	}

	/*
	 * @see com.biotech.bastard.cards.Card#getImagePath()
	 */
	@Override
	protected String getImagePath() {
		// TODO Auto-generated method stub
		return "informRed.png";
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
		// TODO Auto-generated method stub
		return "Inform";
	}

}
