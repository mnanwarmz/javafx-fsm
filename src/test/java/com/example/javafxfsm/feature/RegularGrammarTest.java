package com.example.javafxfsm.feature;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.example.javafxfsm.models.NFA;
import com.example.javafxfsm.models.RegularGrammar;

class RegularGrammarTest {

	@Test
	public void testConvertToNFA() {
		// Create a new Regular Grammar with 2 states and 2 symbols
		RegularGrammar grammar = new RegularGrammar(2, 2);
		// Add production rules to the grammar
		grammar.addProductionRule(0, 0, 1);
		grammar.addProductionRule(0, 1, 1);
		grammar.addProductionRule(1, 0, 0);
		grammar.addProductionRule(1, 1, 0);
		// Set the start state
		grammar.setStartState(0);
		// Set the final state
		grammar.addFinalState(1);
		// Convert the grammar to an NFA
		NFA nfa = grammar.convertToNFA(grammar);
		// Test that the NFA has the correct number of states and symbols
		assertEquals(2, nfa.getNumStates());
		assertEquals(2, nfa.getNumSymbols());
		// Test that the NFA has the correct start state
		assertEquals(0, nfa.getStartState());
		// Test that the NFA has the correct final states
		Set<Integer> finalStates = nfa.getFinalStates();
		Set<Integer> expectedFinalStates = new HashSet<>();
		expectedFinalStates.add(1);
		assertEquals(expectedFinalStates, finalStates);
		// Test that the NFA has the correct transition table
		Map<Integer, Map<Integer, Set<Integer>>> transitionTable = nfa.getTransitionTable();
		Set<Integer> nextStates = transitionTable.get(0).get(0);
		assertTrue(nextStates.contains(1));
		nextStates = transitionTable.get(0).get(1);
		assertTrue(nextStates.contains(1));
		nextStates = transitionTable.get(1).get(0);
		assertTrue(nextStates.contains(0));
		nextStates = transitionTable.get(1).get(1);
		assertTrue(nextStates.contains(0));
	}
}
