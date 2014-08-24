/**
 * 
 */
package com.biotech.bastard.actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import processing.core.PApplet;

import com.biotech.bastard.animations.Animation;
import com.biotech.bastard.cards.Action;
import com.biotech.bastard.people.Item;
import com.biotech.bastard.people.Mood;
import com.biotech.bastard.people.Person;

/**
 * Created: Aug 24, 2014
 * 
 * @author Brian Holman
 *
 */
public class Murder extends Action {

	private static transient final Logger LOGGER = LoggerFactory.getLogger(Murder.class);

	/*
	 * @see
	 * com.biotech.bastard.cards.Action#performAction(com.biotech.bastard.people
	 * .Person)
	 */
	@Override
	public void performAction(Person person) {
		if (!validAction(person)) {
			return;
		}
		Person target = null;
		float min = Float.MAX_VALUE;
		for (Person relation : person.getOpinions().keySet()) {
			if (person.getOpinions().get(relation).isTouchable()) {
				if (min < person.getOpinions().get(relation).getApproval() || target == null) {
					target = relation;
					min = person.getOpinions().get(relation).getApproval();
				}
			}
		}

		if (target == null) {
			target = person;
		}

		LOGGER.info("MURDER {} by {}", target.getName(), person.getName());
		person.getInventory().put(Item.WEAPON, person.getInventory().get(Item.WEAPON) - 1);
		target.setMood(Mood.DEAD);
	}

	/*
	 * @see
	 * com.biotech.bastard.cards.Action#validAction(com.biotech.bastard.people
	 * .Person)
	 */
	@Override
	public boolean validAction(Person person) {
		LOGGER.info("Request for murder!");
		if (person == null) {
			LOGGER.info("Failed because null");
			return false;
		}
		if (person.getInventory().get(Item.WEAPON) <= 0) {
			LOGGER.info("Failed because no weapon");
			return false;
		}
		if (person.getMood() != Mood.HOMICIDAL) {
			LOGGER.info("Failed because not homicidal");
			return false;
		}
		return true;
	}

	/*
	 * @see
	 * com.biotech.bastard.cards.Action#getAnimation(processing.core.PApplet,
	 * com.biotech.bastard.people.Person)
	 */
	@Override
	public Animation getAnimation(PApplet p, Person person) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * @see com.biotech.bastard.cards.Action#getName()
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Murder";
	}
}
