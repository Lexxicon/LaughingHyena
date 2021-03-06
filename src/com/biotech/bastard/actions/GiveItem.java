/**
 * 
 */
package com.biotech.bastard.actions;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import processing.core.PApplet;

import com.biotech.bastard.Color;
import com.biotech.bastard.animations.Animation;
import com.biotech.bastard.animations.SelectionPing;
import com.biotech.bastard.cards.Action;
import com.biotech.bastard.people.Item;
import com.biotech.bastard.people.Person;

/**
 * Created: Aug 24, 2014
 * 
 * @author Brian Holman
 *
 */
public class GiveItem extends Action {

	private static transient final Logger LOGGER = LoggerFactory.getLogger(GiveItem.class);

	Item item = Item.values()[new Random().nextInt(Item.values().length)];

	/*
	 * @see
	 * com.biotech.bastard.cards.Action#performAction(com.biotech.bastard.people
	 * .Person)
	 */
	@Override
	public void performAction(Person person) {
		person.getInventory().put(item, person.getInventory().get(item) + 1);
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
		return "Give " + item.toString();
	}

	public void setItem(Item item) {
		this.item = item;
	}

	/*
	 * @see
	 * com.biotech.bastard.cards.Action#getAnimation(processing.core.PApplet,
	 * com.biotech.bastard.people.Person)
	 */
	@Override
	public Animation getAnimation(PApplet p, Person person) {
		Color start = new Color(255, 255, 255, 255);
		Color target = new Color(129, 200, 239, 255);

		SelectionPing pg = new SelectionPing(p, 40, Person.DIAMITER, Person.DIAMITER + 10, start, target);
		pg.setLocation(person.getLocation());
		return pg;
	}
}
