/**
 * 
 */
package com.biotech.bastard.cards;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.biotech.bastard.Person;

/**
 * Created: Aug 23, 2014
 * 
 * @author Brian Holman
 *
 */
public abstract class Action {

	private static transient final Logger LOGGER = LoggerFactory.getLogger(Action.class);

	private String name;

	private String description;

	/**
	 * 
	 */
	public abstract void performAction();

	public abstract boolean validAction(Person person);

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
