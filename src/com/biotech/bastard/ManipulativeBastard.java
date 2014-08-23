/**
 * 
 */
package com.biotech.bastard;

import java.awt.Point;
import java.util.HashSet;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import processing.core.PApplet;

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

	/*
	 * @see processing.core.PApplet#setup()
	 */
	@Override
	public void setup() {
		LOGGER.info("Setting up application");
		size(1200, 800);
		center = new Point(600, 400);
		people = makePeople(45);
		targetPerson = people[0];
		noLoop();
		smooth();
	}

	private Person[] makePeople(int numberToMake) {

		Person[] newPeople = new Person[numberToMake];

		for (int i = 0; i < newPeople.length; i++) {
			newPeople[i] = new Person(this);
			float rads = radians(map(i, 0, newPeople.length, 0, 359));
			newPeople[i].location.x = (int) (Math.sin(rads) * radius + center.x);
			newPeople[i].location.y = (int) (Math.cos(rads) * radius + center.y);
		}

		for (int i = 0; i < newPeople.length; i++) {
			newPeople[i].getOpinions().put(newPeople[(i + rInt(5) + 1) % newPeople.length],
					new Opinion());
			newPeople[i].getOpinions().put(newPeople[(i + newPeople.length - rInt(5) - 1) % newPeople.length],
					new Opinion());
		}

		return newPeople;
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
				targetPerson = pep;
				break;
			}
		}
		redraw();
	}

	int i = 0;

	/*
	 * @see processing.core.PApplet#draw()
	 */
	@Override
	public void draw() {
		background(0);
		drawnPeople.clear();

		drawPerson(targetPerson);

		drawSelectedInfo(width - 200, 0);

	}

	private void drawPerson(Person person) {

		for (Person pep : people) {
			pep.setDistance(Integer.MAX_VALUE);
		}
		updateDistance(person);
		for (Person pep : people) {
			pep.lineToChildren();
		}

		for (Person pep : people) {
			pep.draw(pep.getDistance());
		}
	}

	public void drawSelectedInfo(int x, int y) {
		int padding = 15;
		int dataPadding = 100;
		int w = 300;
		int h = 400;
		String[] names = {};

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

		text("First Dregree", 0, 0);

		translate(padding, 0);
		names = targetPerson.getDegree(1);
		for (int i = 0; i < names.length; i++) {
			translate(0, padding);
			text(names[i], 0, 0);
		}

		popMatrix();
		popMatrix();
	}

	private void renderData(String name, String value) {
		int dataPadding = 100;
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
