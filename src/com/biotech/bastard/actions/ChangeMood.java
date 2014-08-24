/**
 * 
 */
package com.biotech.bastard.actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import processing.core.PApplet;

import com.biotech.bastard.Color;
import com.biotech.bastard.animations.Animation;
import com.biotech.bastard.animations.SelectionPing;
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
			person.setMood(Mood.weightedRandom());
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

	/*
	 * @see
	 * com.biotech.bastard.cards.Action#getAnimation(processing.core.PApplet)
	 */
	@Override
	public Animation getAnimation(PApplet p, Person person) {
		Color start = new Color(255, 255, 255, 255);
		Color target;

		switch (person.getMood()) {
		case ANGRY:
			target = new Color(200, 128, 128, 255);
			break;
		case HAPPY:
			target = new Color(0, 255, 0, 255);
			break;
		case HOMICIDAL:
			target = new Color(255, 0, 0, 255);
			break;
		case NEUTRAL:
			target = new Color(128, 128, 128, 255);
			break;
		default:
			target = new Color(128, 128, 128, 255);
			break;

		}
		SelectionPing pg = new SelectionPing(p, 40, Person.DIAMITER, Person.DIAMITER + 10, start, target);
		pg.setLocation(person.getLocation());
		return pg;

	}
}
