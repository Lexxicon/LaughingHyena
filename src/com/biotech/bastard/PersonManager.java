/**
 * 
 */
package com.biotech.bastard;

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
		return -1;
	}
}
