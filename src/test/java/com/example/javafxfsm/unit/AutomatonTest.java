package com.example.javafxfsm.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;

import com.example.javafxfsm.models.Automaton;

public class AutomatonTest {
	@Test
	public void testAutomaton() {
		Automaton automaton = new Automaton(3, 2);
		automaton.setTransition(0, 0, 1);
		automaton.setTransition(1, 1, 2);

		assertEquals(3, automaton.getNumStates());
		assertEquals(2, automaton.getNumSymbols());
		assertEquals(1, automaton.getTransition(0, 0));
		assertEquals(2, automaton.getTransition(1, 1));
	}

	@Test
	public void testDisplayTransitionTable() {
		// Create an instance of the Automaton class
		Automaton automaton = new Automaton(3, 2);
		automaton.setTransition(0, 0, 1);
		automaton.setTransition(1, 1, 2);

		// Create a ByteArrayOutputStream to capture the output of the
		// displayTransitionTable method
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));

		// Call the displayTransitionTable method
		automaton.displayTransitionTable();

		// Assert that the output is as expected
		String expectedOutput = "  0 1\n0 1 0 \n1 0 2 \n2 0 0 \n";
		assertEquals(expectedOutput, outContent.toString());
	}
}
