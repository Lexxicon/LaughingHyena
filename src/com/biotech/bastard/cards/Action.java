/**
 * 
 */
package com.biotech.bastard.cards;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import processing.core.PApplet;

import com.biotech.bastard.animations.Animation;
import com.biotech.bastard.people.Person;

/**
 * Created: Aug 23, 2014
 * 
 * @author Brian Holman
 *
 */
public abstract class Action {

	private static transient final Logger LOGGER = LoggerFactory.getLogger(Action.class);

	/**
	 * 
	 */
	public abstract void performAction(Person person);

	public abstract boolean validAction(Person person);

	public abstract Animation getAnimation(PApplet p, Person person);

	public boolean needsTarget() {
		return true;
	}

	public Person[] targets() {
		return new Person[0];
	}

	/**
	 * @return
	 */
	public abstract String getName();

	public String getDescription() {
		return getName() + " has no description";
	};
}
