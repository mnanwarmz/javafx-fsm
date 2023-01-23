package com.example.javafxfsm.feature;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.example.javafxfsm.models.DFA;
import com.example.javafxfsm.models.NFA;

class DFATest {
	@Test
	public void testConvertToDFA() {
		// Create an NFA with 3 states and 2 symbols
		NFA nfa = new NFA(3, 2);
		// Set the start state to 0
		nfa.setStartState(0);
		// Add a transition from state 0 to state 1 on symbol 0
		nfa.addTransition(0, 0, 1);
		// Add a transition from state 0 to state 2 on symbol 1
		nfa.addTransition(0, 1, 2);
		// Add a transition from state 1 to state 2 on symbol 1
		nfa.addTransition(1, 1, 2);
		// Add state 2 as a final state
		nfa.addFinalState(2);
		// Convert the NFA to a DFA
		DFA dfa = nfa.convertToDFA(nfa);
		// Verify that the DFA has the correct number of states
		assertEquals(3, dfa.getNumStates());
		// Verify that the DFA has the correct start state
		assertEquals(new HashSet<>(Arrays.asList(0)), dfa.getStartState());
		// Verify that the DFA has the correct final states
		assertEquals(new HashSet<>(Arrays.asList(2)), dfa.getFinalStates());
		// Verify that the DFA has the correct transitions
		Map<Integer, Set<Integer>> expectedTransitions = new HashMap<>();
		expectedTransitions.put(0, new HashSet<>(Arrays.asList(1)));
		expectedTransitions.put(1, new HashSet<>(Arrays.asList(2)));
		assertEquals(expectedTransitions, dfa.getTransitions().get(0));
	}
}