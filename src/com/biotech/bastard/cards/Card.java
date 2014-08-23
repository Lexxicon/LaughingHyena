/**
 * 
 */
package com.biotech.bastard.cards;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created: Aug 23, 2014
 * 
 * @author Brian Holman
 *
 */
public class Card {

	private static transient final Logger LOGGER = LoggerFactory.getLogger(Card.class);

	private String name;

	private Action action;

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
