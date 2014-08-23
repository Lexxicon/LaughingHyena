/**
 * 
 */
package com.biotech.bastard;

import java.awt.Point;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created: Aug 23, 2014
 * 
 * @author Brian Holman
 *
 */
public class PersonManager {

	private static transient final Logger LOGGER = LoggerFactory.getLogger(PersonManager.class);

	/**
	 * Cause the listener to become aware of the target at degree + 1 of the
	 * tellers relationship
	 * 
	 * @param listener
	 * @param teller
	 * @param target
	 */
	public void inform(Person listener, Person teller, Person target) {
		Opinion listenerOfTeller = listener.getOpinions().get(teller);
		Opinion listenerOfTarget = listener.getOpinions().get(target);
		Opinion tellerOfTarget = teller.getOpinions().get(target);
		if (listenerOfTarget == null) {
			listenerOfTarget = new Opinion();
			listener.getOpinions().put(target, listenerOfTarget);
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

		if (firstOfCommon == null || secondOfCommon == null) {
			LOGGER.debug("Failed to introduce, one does not know common");
			return;
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
	 * -1 if unreachable
	 * 
	 * @param start
	 * @param target
	 * @return
	 */
	public int distanceTo(Person start, Person target) {
		LOGGER.warn("not working");
		return -1;
	}

	public void removeOverlap(Person[] persons, int x, int y, int w, int h) {
		boolean stillOverlap = true;

		ArrayList<Person> overlapping = new ArrayList<>();
		int breakout = 0;
		while (stillOverlap && breakout < 15) {
			breakout++;
			stillOverlap = false;
			for (int i = 0; i < persons.length; i++) {
				overlapping.clear();
				Point firstPerson = persons[i].location;
				for (int j = i + 1; j < persons.length; j++) {
					Point secondPerson = persons[j].location;

					if (Util.distance(firstPerson.x, firstPerson.y, secondPerson.x, secondPerson.y) < Person.DIAMITER) {
						stillOverlap = true;
						overlapping.add(persons[j]);
					}
				}
				for (Person over : overlapping) {
					float deltaX, deltaY;
					deltaX = over.location.x - firstPerson.x;
					deltaY = over.location.y - firstPerson.y;

					float rads = (float) Math.atan2(deltaY, deltaX);
					if (!Float.isNaN(rads)) {
						float distace = Util.distance(firstPerson.x, firstPerson.y, over.location.x, over.location.y);
						distace *= 1.5;
						over.location.x = (int) (Util.clamp(over.location.x
								+ (float) (Math.cos(rads) * distace), x, x + w));
						over.location.y = (int) (Util.clamp(over.location.y
								+ (float) (Math.sin(rads) * distace), y, y + h));
					}
				}
			}
		}
		if (stillOverlap) {
			LOGGER.info("Broke out after Iterations {}", breakout);
		}
	}
}
