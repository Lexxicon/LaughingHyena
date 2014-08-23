/**
 * 
 */
package com.biotech.bastard;

import java.awt.Point;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import processing.core.PApplet;

/**
 * Created: Aug 23, 2014
 * 
 * @author Brian Holman
 *
 */
public class Person {

	private static transient final Logger LOGGER = LoggerFactory.getLogger(Person.class);

	public static final Color border = new Color(255, 255, 255);

	public static final Color[] heatColor = {
			new Color(255, 255, 255, 255), // bright red
			new Color(255, 255, 255, 200),
			new Color(255, 255, 255, 150),
			new Color(255, 255, 255, 100),
			new Color(255, 255, 255, 075),
			new Color(255, 255, 255, 050),
			new Color(255, 255, 255, 025),
			new Color(255, 255, 255, 010) // gray
	};

	public static int RADIUS = 30;

	public static AtomicInteger counter = new AtomicInteger(0);

	PApplet parrent;

	Point location, size;

	final private Map<Person, Opinion> opinions;

	private int distance;

	private final String name;
	private Mood mood = Mood.NEUTRAL;
	private EnumMap<Item, Integer> inventory;

	public Person(PApplet parrent) {
		opinions = new LinkedHashMap<>();
		location = new Point();
		this.inventory = new EnumMap<>(Item.class);
		this.size = new Point(RADIUS, RADIUS);
		this.parrent = parrent;
		this.name = Constants.names[counter.incrementAndGet()];

		for (Item item : Item.values()) {
			inventory.put(item, 0);
		}
	}

	public void addRelationship(Person person) {
		getOpinions().put(person, new Opinion());
	}

	public void draw(int degrees) {

		Color fill = heatColor[Math.min(degrees, heatColor.length - 1)];

		parrent.fill(fill.r, fill.g, fill.b, fill.a);
		parrent.stroke(border.r, border.b, border.g, border.a);
		parrent.ellipse(location.x, location.y, size.x, size.y);

		parrent.fill(255, 255, 255);

		// parrent.text(degrees, location.x, location.y);
	}

	public void lineToChildren() {
		Color fill = heatColor[Math.min(distance, heatColor.length - 1)];
		parrent.stroke(fill.r, fill.b, fill.g, fill.a);
		for (Person child : getOpinions().keySet()) {
			parrent.line(location.x, location.y, child.location.x, child.location.y);
		}
	}

	public String getName() {
		return name;
	}

	public Mood getMood() {
		return mood;
	}

	public EnumMap<Item, Integer> getInventory() {
		return inventory;
	}

	public String[] getDegree(int degree) {
		String[] names = new String[getOpinions().size()];
		int index = 0;
		for (Person p : getOpinions().keySet()) {
			names[index] = p.getName();
			index++;
		}
		return names;
	}

	public boolean isPointWithin(int x, int y) {
		if (Math.sqrt(Math.pow(location.x - x, 2) + Math.pow(location.y - y, 2)) < RADIUS / 2) {
			return true;
		}
		return false;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public Map<Person, Opinion> getOpinions() {
		return opinions;
	}
}
