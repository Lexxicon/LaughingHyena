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

	public static final Color[] heatColor = Color.createGradiant(
			new Color(255, 255, 255, 255),
			new Color(050, 250, 250, 015),
			10);

	public static int DIAMITER = 50;

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
		this.size = new Point(DIAMITER, DIAMITER);
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
	}

	public void drawOpinionLines() {
		Color fill = heatColor[Math.min(distance, heatColor.length - 1)];
		parrent.stroke(fill.r, fill.b, fill.g, fill.a);
		for (Person child : getOpinions().keySet()) {
			int r = (int) PApplet.map(opinions.get(child).getApproval(), -1, 1, 255, 0);
			int b = (int) PApplet.map(opinions.get(child).getApproval(), -1, 1, 0, 255);
			float a = PApplet.map(PApplet.constrain(opinions.get(child).getAwareness(), 1, 7), 1, 7, 255, 0);

			parrent.stroke(r, Math.min(r, b), b, a);
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
		if (Util.distance(location.x, location.y, x, y) < DIAMITER / 2) {
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
