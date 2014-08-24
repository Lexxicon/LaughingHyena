/**
 * 
 */
package com.biotech.bastard.actions;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.biotech.bastard.cards.Action;
import com.biotech.bastard.people.Mood;
import com.biotech.bastard.people.Person;

/**
 * Created: Aug 24, 2014
 * 
 * @author Brian Holman
 *
 */
public class ChangeMood extends Action {

	private static transient final Logger LOGGER = LoggerFactory.getLogger(ChangeMood.class);

	Mood mood;

	/*
	 * @see
	 * com.biotech.bastard.cards.Action#performAction(com.biotech.bastard.people
	 * .Person)
	 */
	@Override
	public void performAction(Person person) {
		if (mood == null) {
			person.setMood(Mood.values()[new Random().nextInt(Mood.values().length)]);
		} else {
			person.setMood(mood);
		}
	}

	/*
	 * @see
	 * com.biotech.bastard.cards.Action#validAction(com.biotech.bastard.people
	 * .Person)
	 */
	@Override
	public boolean validAction(Person person) {
		return person != null;
	}

	/*
	 * @see com.biotech.bastard.cards.Action#getName()
	 */
	@Override
	public String getName() {
		return mood == null ? "Mood Swing" : "Change mood to " + mood;
	}
}
