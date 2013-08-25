# Instructions

This tutorial gives you:

* A simple code base, to explore and model using class diagrams
* A puzzle to solve, in why Number Six's plan fails
* A secondary puzzle, that corresponds to a common bug in Java programs
* A redesign task

Hopefully, this will give you a little experience modelling and redesigning an unfamiliar code base &mdash; a skill you'll need for your project work.


## Try the code

Read and run the tests. The tests contain the story for today. But don't worry too much about understanding the code yet.


## Class diagram exercise

Sketch out a class diagrm of the code.  Show how `Village`, `Person`, `NumberSix`, `NumberOne`, `Warden`, and `Notepad` relate to each other.

Include methods, visibility, annotations, and parameter types (but don't include getters and setters).

If you want a very quick solution to this, install ObjectAid into Eclipse, and create a new class diagram.  Drag the classes in, and it will sketch them out for you.


## The story

Having sketched looked at how the classes relate to each other, you should be in a better position to understand the code now.

Follow through the tests, and discover why Number Six's plan fails


## The second mystery

The way the code is written, there is a way that an occupant can escape.  Find the escape route hidden in the Village code.  How does it let villagers escape?

## Design patterns exercise

The Village builds a telephone exchange. You have been asked to restructure the program using a Mediator pattern so that villagers can text important messages to each other through the exchange, rather than have to find each other at all. Draw a class diagram of your revised design, but don’t write the code yet.(Omit the fields, methods, etc for classes that haven’t changed)
## Restructuring ExerciseModify the code to implement the Mediator pattern design from theprevious exercise.
