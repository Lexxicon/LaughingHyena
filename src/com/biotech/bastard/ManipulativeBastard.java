/**
 * 
 */
package com.biotech.bastard;

import java.awt.Point;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Stack;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import processing.core.PApplet;
import processing.core.PImage;

import com.biotech.bastard.animations.Animation;
import com.biotech.bastard.animations.SelectionPing;
import com.biotech.bastard.cards.Card;
import com.biotech.bastard.cards.Deck;
import com.biotech.bastard.cards.PlayField;
import com.biotech.bastard.people.Mood;
import com.biotech.bastard.people.Opinion;
import com.biotech.bastard.people.Person;
import com.biotech.bastard.people.PersonManager;
import com.biotech.bastard.ui.ScreenHotspot;

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

	public static final Color WHITE = new Color(255, 255, 255, 255);
	public static final Color RED = new Color(255, 0, 0, 255);
	public static final Color GREEN = new Color(0, 255, 0, 255);
	public static final Color BLUE = new Color(0, 0, 255, 255);

	public static final Map<Mood, PImage> moodIcon = new EnumMap<>(Mood.class);

	// used to ensure mouse handling;
	boolean mPress, lPress, rPress;

	GameState state = GameState.INSPECT;

	Card selectedCard = null;
	boolean drawKnown = true;

	Person[] people;
	Person selectedPerson;
	Person targetPerson;
	HashSet<Person> drawnPeople = new HashSet<>();
	Point center;
	int radius = 300;

	PlayField field = new PlayField();
	PersonManager manager = new PersonManager();
	List<Animation> animations = new CopyOnWriteArrayList<>();
	List<ScreenHotspot> hostspots = new CopyOnWriteArrayList<>();

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
		selectedPerson = people[0];
		targetPerson = people[new Random().nextInt(people.length)];
		selectionPing = new SelectionPing(this,
				35, 50, 90,
				WHITE,
				new Color(200, 200, 150, 150));

		animations.add(selectionPing);
		for (Card c : buildDeck()) {
			field.addCardToDeck(c);
		}
		for (int i = 0; i < 5; i++) {
			field.drawCard();
		}
		loadMoodIcons();
		smooth();
	}

	private void loadMoodIcons() {
		moodIcon.put(Mood.ANGRY, loadImage("emoticonMad.png"));
		moodIcon.put(Mood.HAPPY, loadImage("emoticonHappy.png"));
		moodIcon.put(Mood.HOMICIDAL, loadImage("emoticonHOMICIDAL.png"));
		moodIcon.put(Mood.NEUTRAL, loadImage("emoticonNeutral.png"));
		moodIcon.put(Mood.DEAD, loadImage("emoticonDEAD.png"));
	}

	private List<Card> buildDeck() {
		return new Deck(this).cards;
	}

	private Person[] makePeople(int numberToMake) {

		Person[] newPeople = new Person[numberToMake];

		for (int i = 0; i < newPeople.length; i++) {
			newPeople[i] = new Person(this);
			setLocation(newPeople[i], i, newPeople.length, Loc.RANDOM);
		}
		for (int i = 0; i < newPeople.length; i++) {
			int knowns = (int) random(3, 8);
			for (int j = 0; j < knowns; j++) {
				int personId = rInt(newPeople.length);
				if (personId != i) {
					newPeople[i].getOpinions().put(newPeople[personId], new Opinion(random(-1, 1), rInt(6) + 1));
				}
			}
		}
		while (manager.removeOverlap(newPeople, center.x - radius, center.y - radius, radius * 2, radius * 2)) {
			for (int i = 0; i < newPeople.length; i++) {
				setLocation(newPeople[i], i, newPeople.length, Loc.RANDOM);
			}
		}

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
		switch (state) {
		case INSPECT:
			if (!selectPerson()) {
				if (selectCard()) {
					if (selectedCard.needsTarget()) {
						state = GameState.TARGET;
					}
				}
			}
			break;
		case TARGET:
			state = GameState.INSPECT;
			if (selectPerson() && selectedPerson.getMood() != Mood.DEAD) {
				field.playCard(selectedCard, selectedPerson);
				manager.updatePeople(people);
			}
			Card.selected = null;
			selectedCard = null;
			break;
		default:
			break;
		}
		handleHS();
	}

	/*
	 * @see processing.core.PApplet#mouseMoved()
	 */
	@Override
	public void mouseMoved() {
		handleHS();
	}

	private void handleHS() {
		for (ScreenHotspot hs : hostspots) {
			hs.handle(mouseX, mouseY, mousePressed && mouseButton == LEFT, mPress && mouseButton == RIGHT);
		}
	}

	/**
	 * 
	 */
	private boolean selectCard() {
		for (Card c : field.getHand()) {
			if (c.getFrame().contains(mouseX, mouseY)) {
				selectedCard = c;
				Card.selected = c;
				return true;
			}
		}
		return false;
	}

	public boolean selectPerson() {

		for (Person pep : people) {
			if (pep.isPointWithin(mouseX, mouseY)) {
				if (pep != selectedPerson) {
					selectionPing.setLocation(pep.getLocation());
					selectionPing.restart();
				}
				if (pep == selectedPerson) {
					drawKnown = !drawKnown;
				} else {
					drawKnown = true;
				}
				selectedPerson = pep;
				return true;
			}
		}
		return false;
	}

	/*
	 * @see processing.core.PApplet#mousePressed()
	 */
	@Override
	public void mousePressed() {
		// TODO Auto-generated method stub
		super.mousePressed();
		mPress = true;
		lPress = mouseButton == LEFT;
		rPress = mouseButton == RIGHT;
	}

	/*
	 * @see processing.core.PApplet#draw()
	 */
	@Override
	public void draw() {
		background(0);
		String known = "Showing who the selection knows";
		String unknown = "Showing who knows the selection ";
		if (drawKnown) {
			text(known, center.x - textWidth(known) / 2, 50);
		} else {
			text(unknown, center.x - textWidth(unknown) / 2, 50);
		}

		drawnPeople.clear();
		targetPerson.highlight();
		for (Animation a : animations) {
			a.draw();
		}

		if (drawKnown) {
			selectedPerson.drawOpinionLines();
		} else {
			selectedPerson.drawKnownBy(people);
		}
		for (Person person : people) {
			person.draw(Integer.MAX_VALUE);
		}

		fill(255, 255, 255, 128);
		stroke(255, 255, 255, 128);
		for (Person person : people) {
			person.drawName();
		}

		manager.drawPerson(this, selectedPerson);
		selectedPerson.drawNames();
		manager.drawInfoPane(this, width - 200, 0, selectedPerson);

		for (ScreenHotspot hs : hostspots) {
			hs.draw(this);
		}

		field.renderHand(this, 15, 100);

		mPress = false;
		lPress = false;
		rPress = false;
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

				// the person at index i is a distance of pass from the input
				// person

				// persons.get(i).setDistance(pass);
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
