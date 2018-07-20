package com.wbillingsley.tutorials.classdiags;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Used for writing down identities.
 */
public class Notepad {

	/**
	 * For those who haven't seen them before, Maps in Java relate a key to a value.
	 * So, this lets you look up a Person by their number...
	 */
	private HashMap<Integer, Person> forwardMap = new HashMap<Integer, Person>();

	/**
	 * ...and this lets you look up a Person's number by using the Person themselves as the key.
	 */
	private HashMap<Person, Integer> reverseMap = new HashMap<Person, Integer>();
	
	/**
	 * Adds a person into the notebook. If the person was already in there, check they still claim to have the same number
	 */
	public void addPerson(int number, Person p) {
		// Put the person into the map from number to Person
		forwardMap.put(number, p);

		// Put the person into the map from Person to number.
		// When we call put, if there was already a value for that key (a number for that Person), it will return it
		// (that's just how the Java library writers wrote HashMap)
		Integer i = reverseMap.put(p, number);

		// If they already had a number and it was different, they have lied!
		if (i != null && i != number) {
			throw new LiarException(i, number, p);
		}
	}
	
	public HashMap<Integer, Person> getForwardMap() {
		return forwardMap;
	}
	
	public Map<Person, Integer> getReverseMap() {
		return this.reverseMap;
	}
	

	public void addAll(Notepad other) {
		if (other != null) {
			for (Entry<Integer, Person> e : other.getForwardMap().entrySet()) {
				this.addPerson(e.getKey(), e.getValue());
			}
		}
	}
	
	public void clear() {
		forwardMap.clear();
		reverseMap.clear();
	}
	
}
