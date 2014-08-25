/**
 * 
 */
package com.biotech.bastard.people;

import java.awt.Point;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import processing.core.PApplet;
import processing.core.PImage;

import com.biotech.bastard.Util;
import com.biotech.bastard.actions.ChangeMood;
import com.biotech.bastard.actions.GiveItem;
import com.biotech.bastard.actions.Inform;
import com.biotech.bastard.actions.Introduce;
import com.biotech.bastard.actions.Murder;
import com.biotech.bastard.cards.Action;

/**
 * Created: Aug 23, 2014
 * 
 * @author Brian Holman
 *
 */
public class PersonManager {

	private static transient final Logger LOGGER = LoggerFactory.getLogger(PersonManager.class);
	private Map<Mood, Action[]> availableActions = new EnumMap<>(Mood.class);
	Random r = new Random();

	/**
	 * 
	 */
	public PersonManager() {
		for (Mood m : Mood.values()) {
			availableActions.put(m, new Action[0]);
		}

		availableActions
				.put(Mood.ANGRY, new Action[] { new Inform(), new Introduce(), new ChangeMood() });
		availableActions
				.put(Mood.HAPPY, new Action[] { new Inform(), new Introduce(), new ChangeMood(), new GiveItem() });
		availableActions
				.put(Mood.DEAD, new Action[] {});
		availableActions
				.put(Mood.NEUTRAL, new Action[] { new Inform(), new Introduce(), new ChangeMood() });
		availableActions
				.put(Mood.HOMICIDAL, new Action[] { new Murder(), new ChangeMood() });
	}

	public void updatePeople(Person[] people) {
		for (Person p : people) {
			p.update();
			if (p.getMood() == Mood.DEAD) {
				continue;
			}
			int offset = new Random().nextInt(availableActions.get(p.getMood()).length);
			if (r.nextDouble() > .0) {
				p.addAction(availableActions.get(p.getMood())[offset]);
			}
		}
	}

	/**
	 * Cause the listener to become aware of the target at degree + 1 of the
	 * tellers relationship
	 * 
	 * @param listener
	 * @param teller
	 * @param target
	 */
	public void inform(Person listener, Person teller, Person target) {
		LOGGER.trace("informing {} about {}", listener.getName(), target.getName());
		Opinion listenerOfTeller = listener.getOpinions().get(teller);
		Opinion listenerOfTarget = listener.getOpinions().get(target);
		Opinion tellerOfTarget = teller.getOpinions().get(target);
		if (listenerOfTarget == null) {
			listenerOfTarget = new Opinion();
			listener.getOpinions().put(target, listenerOfTarget);
		}
		if (listenerOfTeller == null) {
			listenerOfTeller = new Opinion();
			listenerOfTeller.setApproval(tellerOfTarget.getApproval());
			listenerOfTeller.setAwareness(teller.getOpinions().get(listener).getAwareness() + 2);
			listener.getOpinions().put(teller, listenerOfTeller);
		}
		if (listenerOfTeller != null && tellerOfTarget != null) {
			listenerOfTarget.influence(listenerOfTeller, tellerOfTarget);
		}

	}

	/**
	 * Cause the first person and the second person to become aware of each
	 * other on a first degree
	 * 
	 * @param first
	 * @param common
	 * @param second
	 */
	public void introduce(Person first, Person common, Person second) {
		Opinion firstOfCommon = first.getOpinions().get(common);
		Opinion firstOfSecond = first.getOpinions().get(second);

		Opinion secondOfCommon = second.getOpinions().get(common);
		Opinion secondOfFirst = second.getOpinions().get(first);

		Opinion commonOfFirst = common.getOpinions().get(first);
		Opinion commonOfSecond = common.getOpinions().get(second);

		if (firstOfCommon == null) {
			firstOfCommon = new Opinion(0, 3);
			first.getOpinions().put(common, firstOfCommon);
		}

		if (secondOfCommon == null) {
			secondOfCommon = new Opinion(0, 3);
			second.getOpinions().put(common, secondOfCommon);
		}

		if (firstOfSecond == null) {
			firstOfSecond = new Opinion();
			first.getOpinions().put(second, firstOfSecond);
		}

		if (secondOfFirst == null) {
			secondOfFirst = new Opinion();
			second.getOpinions().put(first, secondOfFirst);
		}

		firstOfSecond.influence(firstOfCommon, commonOfSecond);
		secondOfFirst.influence(secondOfCommon, commonOfFirst);
	}

	/**
	 * Returns how many degrees of separation between the start and the target.
	 * MAX Integer if unreachable
	 * 
	 * @param start
	 * @param target
	 * @return
	 */
	public int distanceTo(Person start, Person target) {
		LOGGER.warn("not working");

		return Integer.MAX_VALUE;
	}

	public void drawPerson(PApplet parent, Person person) {
		person.draw(0);
		for (Person relation : person.getOpinions().keySet()) {
			int awareness = person.getOpinions().get(relation).getAwareness();
			if (awareness < Integer.MAX_VALUE) {
				relation.draw(awareness);
			}
		}
	}

	public void drawInfoPane(PApplet p, int x, int y, Person targetPerson) {
		int padding = 15;
		int w = 200;
		int h = 400;
		p.pushStyle();
		p.pushMatrix();
		{
			p.translate(x, y);
			p.stroke(255);
			p.fill(025);
			p.rect(0, 0, w, h);
			p.fill(255);
			p.pushMatrix();
			{
				p.translate(padding, padding);
				renderData(p, "Name", targetPerson.getName());
				p.translate(0, padding);
				renderData(p, "Mood", targetPerson.getMood().toString());

				for (Item item : targetPerson.getInventory().keySet()) {
					p.translate(0, padding);
					renderData(p, item.toString(), targetPerson.getInventory().get(item).toString());
				}

				p.translate(0, padding * 2);

				p.text("Relationship", 0, 0);

				Map<Person, Opinion> opinions = targetPerson.getOpinions();
				for (Person person : opinions.keySet()) {
					p.translate(0, padding);
					renderData(p, person.getName(), opinions.get(person).getRelation().toString());
				}

				p.translate(0, padding * 2);
				p.text("Action Queue", 0, 0);

				for (Action action : targetPerson.getActionStack()) {
					p.translate(0, padding);
					p.text(action.getName(), 0, 0);
				}
			}
			p.popMatrix();
			PImage image = targetPerson.hiRes.get(targetPerson.getMood());
			p.image(image, image.width / 4, h - image.height);
		}
		p.popStyle();
		p.popMatrix();
	}

	private void renderData(PApplet p, String name, String value) {
		int dataPadding = 70;
		p.text(name + ":", 0, 0);
		p.pushMatrix();
		p.translate(dataPadding, 0);
		p.text(value, 0, 0);
		p.popMatrix();
	}

	public boolean removeOverlap(Person[] persons, int x, int y, int w, int h) {
		boolean stillOverlap = true;

		ArrayList<Person> overlapping = new ArrayList<>();
		int breakout = 0;
		while (stillOverlap && breakout < 15) {
			breakout++;
			stillOverlap = false;
			for (int i = 0; i < persons.length; i++) {
				overlapping.clear();
				Point firstPerson = persons[i].getLocation();
				for (int j = i + 1; j < persons.length; j++) {
					Point secondPerson = persons[j].getLocation();

					if (Util.distance(firstPerson.x, firstPerson.y, secondPerson.x, secondPerson.y) < Person.DIAMITER) {
						stillOverlap = true;
						overlapping.add(persons[j]);
					}
				}
				for (Person over : overlapping) {
					float deltaX, deltaY;
					deltaX = over.getLocation().x - firstPerson.x;
					deltaY = over.getLocation().y - firstPerson.y;

					float rads = (float) Math.atan2(deltaY, deltaX);
					if (!Float.isNaN(rads)) {
						float distace = Util.distance(
								firstPerson.x,
								firstPerson.y,
								over.getLocation().x,
								over.getLocation().y);
						distace *= 1.5;
						over.getLocation().x = (int) (Util.clamp(over.getLocation().x
								+ (float) (Math.cos(rads) * distace), x, x + w));
						over.getLocation().y = (int) (Util.clamp(over.getLocation().y
								+ (float) (Math.sin(rads) * distace), y, y + h));
					}
				}
			}
		}
		if (stillOverlap) {
			LOGGER.info("Broke out after Iterations {}", breakout);
		}

		return stillOverlap;
	}
}
