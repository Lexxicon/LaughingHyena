/**
 * 
 */
package com.biotech.bastard;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import processing.core.PApplet;

import com.biotech.bastard.animations.Animation;
import com.biotech.bastard.animations.SelectionPing;
import com.biotech.bastard.cards.Action;

/**
 * Created: Aug 23, 2014
 * 
 * @author Brian Holman
 *
 */
public class ManipulativeBastard extends PApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1585546240501394051L;
	private static transient final Logger LOGGER = LoggerFactory.getLogger(ManipulativeBastard.class);

	public static void main(String[] args) {
		LOGGER.info("Launching Applet");
		PApplet.main(new String[] { "com.biotech.bastard.ManipulativeBastard" });
	}

	Person[] people;
	Person targetPerson;
	HashSet<Person> drawnPeople = new HashSet<>();
	Point center;
	int radius = 300;
	PersonManager manager = new PersonManager();
	List<Animation> animations = new ArrayList<>();

	SelectionPing selectionPing;

	/*
	 * @see processing.core.PApplet#setup()
	 */
	@Override
	public void setup() {
		LOGGER.info("Setting up application");
		size(1200, 800);
		frameRate(60);
		center = new Point(600, 400);
		people = makePeople(40);
		targetPerson = people[0];
		selectionPing = new SelectionPing(this,
				35, 50, 90,
				new Color(255, 255, 255, 255),
				new Color(200, 200, 150, 150));

		animations.add(selectionPing);
		updatePeople();
		smooth();
	}

	private Person[] makePeople(int numberToMake) {

		Person[] newPeople = new Person[numberToMake];

		for (int i = 0; i < newPeople.length; i++) {
			newPeople[i] = new Person(this);
			setLocation(newPeople[i], i, newPeople.length, Loc.RANDOM);
		}
		for (int i = 0; i < newPeople.length; i++) {
			newPeople[i].getOpinions().put(newPeople[(i + rInt(5) + 1) % newPeople.length],
					new Opinion(random(-1, 1), 1));

			newPeople[i].getOpinions().put(newPeople[(i + newPeople.length - rInt(5) - 1) % newPeople.length],
					new Opinion(random(-1, 1), 1));
		}
		manager.removeOverlap(newPeople, center.x - radius, center.y - radius, radius * 2, radius * 2);

		return newPeople;
	}

	private static enum Loc {
		CIRCLE, RANDOM
	}

	private void setLocation(Person p, int index, int max, Loc l) {
		switch (l) {
		case CIRCLE:
			setLocationCircle(p, index, max);
			break;
		case RANDOM:
			setLocationRandom(p);
			break;
		default:
			break;

		}
	}

	private void setLocationRandom(Person p) {
		p.getLocation().x = (int) (random(-radius, radius) + center.x);
		p.getLocation().y = (int) (random(-radius, radius) + center.y);
	}

	private void setLocationCircle(Person p, int index, int max) {
		float rads = radians(map(index, 0, max, 0, 359));
		p.getLocation().x = (int) (Math.sin(rads) * radius + center.x);
		p.getLocation().y = (int) (Math.cos(rads) * radius + center.y);
	}

	public int rInt(int upper) {
		return (int) random(upper);
	}

	/*
	 * @see processing.core.PApplet#mouseClicked()
	 */
	@Override
	public void mouseClicked() {
		for (Person pep : people) {
			if (pep.isPointWithin(mouseX, mouseY)) {
				if (pep != targetPerson) {
					selectionPing.setLocation(pep.getLocation());
					selectionPing.restart();
				}
				targetPerson = pep;
				break;
			}
		}
		updatePeople();
	}

	private void updatePeople() {
		for (Person pep : people) {
			pep.setDistance(Integer.MAX_VALUE);
		}
		updateDistance(targetPerson);
	}

	/*
	 * @see processing.core.PApplet#draw()
	 */
	@Override
	public void draw() {
		background(0);
		drawnPeople.clear();
		for (Animation a : animations) {
			a.draw();
		}

		targetPerson.drawOpinionLines();
		drawPerson(targetPerson);

		drawSelectedInfo(width - 200, 0);

	}

	private void drawPerson(Person person) {

		for (Person pep : people) {
			pep.draw(pep.getDistance());
		}
	}

	public void drawSelectedInfo(int x, int y) {
		int padding = 15;
		int w = 300;
		int h = 400;

		pushMatrix();
		translate(x, y);
		stroke(255);
		fill(025);
		rect(0, 0, w, h);
		fill(255);
		pushMatrix();
		translate(padding, padding);
		renderData("Name", targetPerson.getName());
		translate(0, padding);
		renderData("Mood", targetPerson.getMood().toString());

		for (Item item : targetPerson.getInventory().keySet()) {
			translate(0, padding);
			renderData(item.toString(), targetPerson.getInventory().get(item).toString());
		}

		translate(0, padding * 2);

		text("Relationship", 0, 0);

		Map<Person, Opinion> opinions = targetPerson.getOpinions();
		for (Person person : opinions.keySet()) {
			translate(0, padding);
			renderData(person.getName(), opinions.get(person).getRelation().toString());
		}

		translate(0, padding * 2);
		text("Action Queue", 0, 0);

		for (Action action : targetPerson.getActionStack()) {
			translate(0, padding);
			text(action.getName(), 0, 0);
		}

		popMatrix();
		popMatrix();
	}

	private void renderData(String name, String value) {
		int dataPadding = 70;
		text(name + ":", 0, 0);
		pushMatrix();
		translate(dataPadding, 0);
		text(value, 0, 0);
		popMatrix();
	}

	private void updateDistance(Person person) {
		Stack<Person> persons = new Stack<>();
		persons.push(person);
		int walkingPoint = 1;
		int finalPoint = 0;
		int pass = 0;

		// so long as these values don't equal eachother things are still being
		// added to the stack.
		while (walkingPoint != finalPoint) {
			// set the newley added persons.
			for (int i = finalPoint; i < walkingPoint; i++) {
				persons.get(i).setDistance(pass);
				// attempt to add all children if they haven't been added
				// already
				for (Person child : persons.get(i).getOpinions().keySet()) {
					if (!persons.contains(child)) {
						persons.add(child);
					}
				}
				// the person has their rank and their unadded children have
				// been added.
			}

			finalPoint = walkingPoint;
			walkingPoint = persons.size();
			pass++;
		}
	}
}
