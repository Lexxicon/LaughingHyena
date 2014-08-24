/**
 * 
 */
package com.biotech.bastard.people;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created: Aug 24, 2014
 * 
 * @author Brian Holman
 *
 */
public class PeopleSearch {

	private static transient final Logger LOGGER = LoggerFactory.getLogger(PeopleSearch.class);

	private static class PeopleWrapper implements Comparable<PeopleWrapper> {
		/**
		 * @param source
		 * @param i
		 */
		public PeopleWrapper(Person source, int i) {
			p = source;
			cost = i;
		}

		Person p;
		int cost;

		/*
		 * @see java.lang.Comparable#compareTo(java.lang.Object)
		 */
		@Override
		public int compareTo(PeopleWrapper o) {
			return Integer.compare(cost, o.cost);
		}
	}

	HashSet<Person> checkedPeople;
	PriorityQueue<PeopleWrapper> nextPaths;
	Stack<PeopleWrapper> path;
	Person source;
	Person target;

	public PeopleSearch(Person source, Person target) {
		this.source = source;
		this.target = target;
		checkedPeople = new HashSet<>();
		nextPaths = new PriorityQueue<PeopleSearch.PeopleWrapper>();
		path = new Stack<>();
		nextPaths.add(new PeopleWrapper(source, 0));
		checkedPeople.add(source);
	}

	public void findNext() {
		PeopleWrapper pw = nextPaths.peek();
		PeopleWrapper next = null;
		boolean done = false;

		while (next == null && nextPaths.size() > 0) {
			for (Person person : pw.p.getOpinions().keySet()) {
				if (!checkedPeople.contains(person)) {
					int awareness = pw.p.getOpinions().get(person).getAwareness();
					if (next == null) {
						next = new PeopleWrapper(person, awareness);
					} else {
						if (next.cost > awareness) {
							next = new PeopleWrapper(person, awareness);
						}
					}
				}
			}
			if (next == null) {
				nextPaths.poll();
			}
		}

		if (next == null || next.p == target) {
			done = true;
		}
	}

	public Stack<Person> getPath() {
		return null;
	}

	public int getDistance() {
		return Integer.MAX_VALUE;
	}
}
