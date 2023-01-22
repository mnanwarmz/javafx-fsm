package com.example.javafxfsm.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.example.javafxfsm.models.DFA;
import com.example.javafxfsm.models.NFA;

public class NFATest {
	@Test
	public void testEpsilonClosure() {
		NFA nfa = new NFA(4, 2);
		nfa.addTransition(0, 0, 1);
		nfa.addTransition(0, 0, 2);
		nfa.addTransition(1, 1, 3);
		nfa.addTransition(2, 0, 3);

		Set<Integer> states = new HashSet<>();
		states.add(0);
		Set<Integer> closure = nfa.epsilonClosure(states);
		Set<Integer> expectedClosure = new HashSet<>();
		expectedClosure.add(0);
		expectedClosure.add(1);
		expectedClosure.add(2);
		expectedClosure.add(3);
		assertEquals(expectedClosure, closure);

		states.clear();
		states.add(3);
		closure = nfa.epsilonClosure(states);
		expectedClosure.clear();
		expectedClosure.add(3);
		assertEquals(expectedClosure, closure);
	}

	@Test
	public void testGetNextStates() {
		NFA nfa = new NFA(4, 2);
		nfa.addTransition(0, 0, 1);
		nfa.addTransition(0, 0, 2);
		nfa.addTransition(1, 1, 3);
		nfa.addTransition(2, 0, 3);

		Set<Integer> states = new HashSet<>();
		states.add(0);
		Set<Integer> nextStates = nfa.getNextStates(states, 0);
		Set<Integer> expectedNextStates = new HashSet<>();
		expectedNextStates.add(1);
		expectedNextStates.add(2);
		assertEquals(expectedNextStates, nextStates);

		states.clear();
		states.add(1);
		nextStates = nfa.getNextStates(states, 1);
		expectedNextStates.clear();
		expectedNextStates.add(3);
		assertEquals(expectedNextStates, nextStates);
	}

	@Test
	public void testMinimizeDFA() {
		// Increase timeout to 10 seconds
		// JUnit 5 does not support this yet
		//

		NFA nfa = new NFA(4, 2);
		nfa.addTransition(0, 0, 1);
		nfa.addTransition(1, 1, 2);
		nfa.addTransition(2, 0, 3);
		nfa.addFinalState(2);
		nfa.addFinalState(3);

		DFA dfa = nfa.minimizeDFA();
		Set<Integer> finalStates = dfa.getFinalStates();
		Set<Integer> expectedFinalStates = new HashSet<>();
		expectedFinalStates.add(1);
		assertEquals(expectedFinalStates, finalStates);

		dfa.addTransition(1, 0, 1);
		dfa.addTransition(1, 1, 1);
		assertTrue(dfa.accepts("01"));
		assertFalse(dfa.accepts("00"));
	}
}
