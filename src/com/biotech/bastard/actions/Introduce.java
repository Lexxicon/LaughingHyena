/**
 * 
 */
package com.biotech.bastard.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import processing.core.PApplet;

import com.biotech.bastard.animations.Animation;
import com.biotech.bastard.cards.Action;
import com.biotech.bastard.people.Opinion;
import com.biotech.bastard.people.Person;
import com.biotech.bastard.people.PersonManager;

/**
 * Created: Aug 24, 2014
 * 
 * @author Brian Holman
 *
 */
public class Introduce extends Action {
	public static enum Type {
		BAD("Enemy"), FRIEND("Friend"), NEUTRAL("Neutral");

		final String name;

		/**
		 * 
		 */
		private Type(String name) {
			this.name = name;
		}
	}

	private static transient final Logger LOGGER = LoggerFactory.getLogger(Introduce.class);

	Type type = Type.FRIEND;

	boolean listenerLocked, subjectLocked;
	Person listener, subject;

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(Type type) {
		this.type = type;
	}

	/**
	 * @param listener
	 *            the listener to set
	 */
	public void setListener(Person listener) {
		listenerLocked = true;
		this.listener = listener;
	}

	/**
	 * @param subject
	 *            the subject to set
	 */
	public void setSubject(Person subject) {
		subjectLocked = true;
		this.subject = subject;
	}

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
		LOGGER.trace("{} is introducing {} to {}", person.getName(), subject.getName(), listener.getName());

		PersonManager manager = new PersonManager();
		manager.introduce(listener, person, subject);

	}

	/*
	 * @see
	 * com.biotech.bastard.cards.Action#validAction(com.biotech.bastard.people
	 * .Person)
	 */
	@Override
	public boolean validAction(Person person) {
		if (person == null) {
			return false;
		}
		if (!listenerLocked) {
			listener = null;
		}
		if (!subjectLocked) {
			subject = null;
		}
		if (listener == null) {
			int offset = new Random().nextInt(person.getOpinions().size());
			List<Person> keys = new ArrayList<Person>(person.getOpinions().keySet());
			for (int i = 0; i < keys.size(); i++) {
				Person p = keys.get((i + offset) % keys.size());
				Opinion o = person.getOpinions().get(p);
				if (o.getAwareness() < 4) {
					if (listener == null || o.getApproval() > 0) {
						listener = p;
						if (o.getApproval() > 0) {
							break;
						}
					}
				}
			}
			if (listener == null) {
				LOGGER.trace("Failed to inform, doesn't know anyone");
				return false;
			}
		}
		if (subject == null) {
			int offset = new Random().nextInt(person.getOpinions().size());
			List<Person> keys = new ArrayList<Person>(person.getOpinions().keySet());
			switch (type) {
			case BAD:
				for (int i = 0; i < keys.size(); i++) {
					Person p = keys.get((i + offset) % keys.size());
					Opinion o = person.getOpinions().get(p);
					if (o.getApproval() < 0 && o.getAwareness() < 4 && listener != p) {
						subject = p;
						break;
					}
				}
				if (subject == null) {
					LOGGER.trace("Failed to gossip about someone, no one disliked");
					return false;
				}
				break;
			case FRIEND:
				for (int i = 0; i < keys.size(); i++) {
					Person p = keys.get((i + offset) % keys.size());
					Opinion o = person.getOpinions().get(p);
					if (o.getApproval() > 0 && o.getAwareness() < 4 && listener != p) {
						subject = p;
						break;
					}
				}
				if (subject == null) {
					LOGGER.trace("Failed to praise someone, no one praiseworthy");
					return false;
				}
				break;
			default:
				subject = keys.get(offset);
				break;

			}
		}
		return true;
	}

	/*
	 * @see com.biotech.bastard.cards.Action#getName()
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Introduce to " + type;
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
}
