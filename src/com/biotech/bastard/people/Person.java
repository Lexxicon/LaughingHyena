/**
 * 
 */
package com.biotech.bastard.people;

import java.awt.Point;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import processing.core.PApplet;

import com.biotech.bastard.Color;
import com.biotech.bastard.Constants;
import com.biotech.bastard.Util;
import com.biotech.bastard.cards.Action;

/**
 * Created: Aug 23, 2014
 * 
 * @author Brian Holman
 *
 */
public class Person {

	@SuppressWarnings("unused")
	private static transient final Logger LOGGER = LoggerFactory.getLogger(Person.class);

	public static final Color border = new Color(255, 255, 255);

	public static final Color[] heatColor = Color.createGradiant(
			new Color(255, 255, 255, 255),
			new Color(250, 250, 250, 000),
			10);

	public static int DIAMITER = 50;

	public static AtomicInteger counter = new AtomicInteger(0);

	PApplet parrent;

	private Point location;

	Point size;

	final private Map<Person, Opinion> opinions;

	private final String name;
	private Mood mood = Mood.NEUTRAL;
	private EnumMap<Item, Integer> inventory;
	private Stack<Action> actionStack;

	public Person(PApplet parrent) {
		opinions = new LinkedHashMap<>();
		setLocation(new Point());
		actionStack = new Stack<Action>();
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

	public void addAction(Action action) {
		actionStack.addElement(action);
	}

	public void update() {
		if (actionStack.size() > 0) {
			Action action = actionStack.pop();

			if (action.validAction(this)) {
				action.performAction(this);
			}
		}
	}

	public void draw(int degrees) {

		Color fill = heatColor[Math.min(degrees, heatColor.length - 1)];

		parrent.fill(fill.r, fill.g, fill.b, fill.a);
		parrent.stroke(border.r, border.b, border.g, border.a);
		parrent.ellipse(getLocation().x, getLocation().y, size.x, size.y);
	}

	public void drawOpinionLines() {
		for (Person child : getOpinions().keySet()) {
			int r = (int) PApplet.map(opinions.get(child).getApproval(), -1, 1, 255, 0);
			int b = (int) PApplet.map(opinions.get(child).getApproval(), -1, 1, 0, 255);
			float a = 255;

			parrent.stroke(r, Math.min(r, b), b, a);
			parrent.line(getLocation().x, getLocation().y, child.getLocation().x, child.getLocation().y);
		}
	}

	public String getName() {
		return name;
	}

	public Mood getMood() {
		return mood;
	}

	public void setMood(Mood mood) {
		this.mood = mood;
	}

	public EnumMap<Item, Integer> getInventory() {
		return inventory;
	}

	public Stack<Action> getActionStack() {
		return actionStack;
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
		if (Util.distance(getLocation().x, getLocation().y, x, y) < DIAMITER / 2) {
			return true;
		}
		return false;
	}

	public Map<Person, Opinion> getOpinions() {
		return opinions;
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}
}
