package com.wbillingsley.tutorials.classdiags.village;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.junit.*;
import org.mockito.Mockito;

import com.wbillingsley.tutorials.classdiags.*;


/**
 * Welcome to The Village...
 *
 * This tutorial is loosely inspired by a 1960s tv show (yes, before I was born too) called The Prisoner, in which
 * a spy is kidnapped and put into a strange seaside village that is also an unusual prison. Nobody knows who is a
 * guard and who is a prisoner, and nobody can leave. Everyone is known by their number instead of their name. And the
 * prison uses all sorts of (progressively stranger) psychological trickery to get its prisoners to comply and to get
 * whatever information it wanted from them.
 *
 * This tutorial just has a little puzzle about an escape attempt. It's not based on any of the episodes.
 *
 * The main purpose of the tutorial, though, is to give you a small gradle project, some tests, and a reason to
 * explore it (without needing to edit the code). That way, hopefully, you'll gain a passing familiarity with
 * exploring gradle projects and tests in an IDE before we release the (more complex) project code.
 *
 * John Drake (Number 6)'s plan in this story is to find out who the mysterious Number One is -- the person who runs
 * the village.
 *
 * In the code, we have a class for Person. We then have specialised subclasses for three unusual kinds of people:
 * Wardens, NumberSix and NumberOne himself.
 *
 * We also have the Village, that if you try to escape (call leave() on its only instance) will refuse to let you go,
 * instead throwing the UnsupportedOperationException("Be seeing you")
 *
 * In this code, if you find out who NumberOne is, and challenge him with his number ("youAreNumberOne()") and proof
 * that you've worked it out and aren't just guessing, he'll throw you out of the village and you'll be free. Only
 * it never happens...
 *
 * NumberSix's plan involves the Notepad class -- remembering everyone's number. Because surely NumberOne will lie, and
 * if he can be caught in a lie, you've got him.
 *
 * Along the way, there are comments explaining JUnit that runs the tests, and also comments in the code for those
 * who are still learning Java.
 *
 */
public class TestVillage {

	/**
	 * John Drake, formerly Dangerman, now Prisoner number 6.
	 * This comment has two asterisks, and is a JavaDoc comment -- that means the javadoc task will take this comment
	 * and put it in the html documentation it generates.
	 */
	private Person johnDrake = new NumberSix();

	/** Number One, the mysterious ruler of The Village */
	private Person numberOne = new NumberOne();

	/**
	 * This is set-up code for the tests. The Before annotation means that JUnit will run it before each test
	 */
	@Before 
	public void before() {
		/*
		 *  Start with an empty village, so that when the tests populate the village they are not adding duplicate
		 *  villagers to a Village already full from the last test.
		 *
		 *  We can only call this because the test is in the same package as the village.
		 *  The prisoners can't call it.
		 */
		Village.INSTANCE.clear();
	}		
	
	
	/**
	 * This is our first test -- notice the Test annotation to mark it as a test. In this case, the test is marked
	 * that it is expected to throw an exception. That is, JUnit will only consider the test to have passed if when
	 * it calls the code, that exception is thrown rather than the function returning successfully.
	 *
	 * John Drake first just tries to leave the Village by walking out.  It won't work...
	 * (If the test passes, John Drake did NOT make it out.)
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testLeaveVillage() {
		// Set this up with Drake in the Village.
		johnDrake.enterVillage();		
		
		// Now try to leave
		// In your IDE, follow the source-code to see what this does.
		// (Control-click on the function call usually works for IDEs on Windows, or Apple-click on Mac).
		// If you click through the code, eventually you should find yourself exploring Village.leave(Person p),
		// which will throw UnsupportedOperationException with the creepy village farewell "Be seeing you."
		johnDrake.leaveVillage();
	}
	
	
	/*
	 * John Drake's next plan is to find out who is Number One. Number One is the only villager
	 * who will lie about his number (to keep his identity secret). So Drake must get all the 
	 * villagers to ask each other their numbers, write them down, and then compare notes.
	 *
	 */	
	
	
	/**
	 * Test that a notepad would help us spot someone lying about their number to us twice
	 */
	@Test(expected = LiarException.class)
	public void testNotepad() {
		Notepad n1 = new Notepad();
		
		// Check that a notepad would help us detect someone lying about their number;
		Person p = Mockito.mock(Person.class);
		n1.addPerson(6, p);
		n1.addPerson(5, p);
	}

	/**
	 * Our next test
	 *
	 * Test that sharing notepads would help us spot someone lying about their number to two
	 * different people.
	 */
	@Test(expected = LiarException.class)
	public void testSharingNotepad() {
		Notepad n1 = new Notepad();
		Notepad n2 = new Notepad();

		// We'll introduce Mockito in a later week. For now, this line create "a mock person". A pretend person that
		// meets the same class definition as Person (has all the same methods available) but none of the behaviour
		// (all the methods are effectively empty).
		//
		// It's just here so you'll have seen "Mockito.mock" before the tutorial where we introduce it properly.
		// In this test, we could have just done new Person()
		Person p = Mockito.mock(Person.class);

		// Let's add the same (mock) person to two notebooks, but with different numbers
		n1.addPerson(6, p);
		n2.addPerson(5, p);

		// Then let's copy everything from notebook 2 into notebook 1. This should cause the LiarException to be
		// thrown, that the test is looking for.
		n1.addAll(n2);
	}	
	
	
	/**
	 * To keep his plan secret from Number One, John Drake tells the villagers they are looking for Number Six
	 * (himself).  As he always refuses to give his own number, they'd end up asking every person
	 * in the village.  But when they share notes at the end, surely they'll find out who the liar is.
	 *
	 * If this test succeeds, Drake's plan SUCCEEDS in theory
	 */
	@Test
	public void testEscapeAttemptInTheory() {
				
		// Set this up with Drake, Number One, and 300 Villagers in the Village.
		for (int i = 0; i < 300; i++) {
			Person p = new Person();
			p.enterVillage();
		}		
		johnDrake.enterVillage();		
		numberOne.enterVillage();
		
		// Drake asks all the Villagers to find Number Six
		for (Person p : Village.INSTANCE.getOccupants()) {
			// "Could you look for Number Six please, I've got an important message about his family..."
			p.find(6);
		}
		
		// Now surely if Drake collects the villagers' notepads, he'll find one liar in their notes...

		// Let's keep a record of all the liars we find when copying the notepads. As the LiarException contains
		// the numbers they used, let's keep this as a map from person to LiarException
		HashMap<Person, LiarException> liars = new HashMap<Person, LiarException>();

		// Now let's copy all of the notepads into John Drake's notepad, and also remember the last lie we saw
		LiarException lastLie = null;
		for (Person p : Village.INSTANCE.getOccupants()) {
			try {
				p.shareNotepad(johnDrake);	
			} catch (LiarException ex) {
				if (!liars.containsKey(ex.getPerson())) {
					liars.put(ex.getPerson(), ex);
					lastLie = ex;
				}
			}			
		}

		// If our plan has worked, then in theory we have found exactly one liar (Number One)
		// Let's confront him with the evidence, and in theory, he will throw Number Six out of the village before
		// he can tell anyone else
		numberOne.youAreNumberOne(lastLie.getFirst(), lastLie.getSecond(), johnDrake);
		
		// Check that in theory, John Drake would discover Number One and be kicked out of the Village
		// This test does not expect an exception. Instead, it has an assert at the end. This test passes if the
		// following assertion is true -- if we ask if the village still contains johnDrake, we should get false as
		// the result.
		Assert.assertEquals("The plan won't work", 
				false,
				Village.INSTANCE.getOccupants().contains(johnDrake)
		);
	}
	
	/**
	 * Little does he know, the wardens almost always foil his plan.  (See the Warden class for details).
	 * 
	 * If the test succeeds, Drake did NOT make it out.
	 */
	@Test
	public void testEscapeAttemptInPractice() {
		
		/*
		 *  Really, the village has some wardens too.
		 *  Set this test up with Drake, Number One, 150 Villagers, and 150 Wardens in the village.
		 *  (These are high value prisoners, they need a lot of wardens!)
		 */		
		for (int i = 0; i < 150; i++) {
			Warden w = new Warden();
			w.enterVillage();
		}		
		for (int i = 0; i < 150; i++) {
			Person p = new Person();
			p.enterVillage();
		}		
		johnDrake.enterVillage();	
		numberOne.enterVillage();
		
		// Drake asks everyone in the village to look for him. He doesn't know who the wardens are.
		for (Person p : Village.INSTANCE.getOccupants()) {
			// "Could you look for Number Six please, I've got an important message about his family..."
			p.find(6);
		}
		
		// Now surely if Drake collects the villagers' notepads, he'll find one liar in their notes...		
		HashMap<Person, LiarException> liars = new HashMap<Person, LiarException>();
		LiarException lastLie = null;
		for (Person p : Village.INSTANCE.getOccupants()) {
			try {
				p.shareNotepad(johnDrake);	
			} catch (LiarException ex) {
				if (!liars.containsKey(ex.getPerson())) {
					liars.put(ex.getPerson(), ex);
					lastLie = ex;
				}
			}			
		}	
		
		try {
			lastLie.getPerson().youAreNumberOne(lastLie.getFirst(), lastLie.getSecond(), johnDrake);
		} catch (IllegalArgumentException ex) {
			// John Drake got it wrong. The assertion will pick that up.
		}
				
		// Check that in practice, John Drake did not discover who is Number One
		Assert.assertEquals("Unexpected - Drake escaped!", 
				true,
				Village.INSTANCE.getOccupants().contains(johnDrake)
		);

		// To find out why, explore the Warden.notePerson method. Uh-oh, wardens lie too. And they only lie
		// sometimes, making it really hard to identify because sometimes they are lying and sometimes they are not.
		// This is a subtle introduction to one of the most annoying things in debugging -- when you have a test that
		// sometimes passes and sometimes fails, and you need to work out why.

		// But for now, it's enough that it's got you to explore JUnit tests, explore existing code using your IDE
		// and run tests in a gradle project
	}

	// To finish our story, though, let's show you the escape route John Drake never found. By default, this test
	// is not run -- it's marked Ignore. But if you enable it, you'll see John Drake escape.
	@Test
	@Ignore("What a pity John Drake didn't try this")
	public void wouldHaveWorked() {
		// let's put the villagers and Drake in the village as before
		for (int i = 0; i < 150; i++) {
			Warden w = new Warden();
			w.enterVillage();
		}
		for (int i = 0; i < 150; i++) {
			Person p = new Person();
			p.enterVillage();
		}
		johnDrake.enterVillage();
		numberOne.enterVillage();

		// Now let's just ask The Village for its occupants list
		Set<Person> occupants = Village.INSTANCE.getOccupants();

		// It's given us its occupants list, but it's mutable! It has remove methods on it!
		occupants.remove(johnDrake);

		// Crikey, getting out was easier than we thought
		Assert.assertEquals(false, Village.INSTANCE.getOccupants().contains(johnDrake));
	}
	
	
}
