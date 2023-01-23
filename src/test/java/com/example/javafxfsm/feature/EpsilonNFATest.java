package com.example.javafxfsm.feature;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.example.javafxfsm.models.NFA;

class EpsilonNFATest {
	@Test
	public void testConvertEpsilonNFAtoNFA() {
		// Create an epsilon-NFA with 3 states and 2 symbols
		NFA epsilonNFA = new NFA(3, 2);
		epsilonNFA.setStartState(0);
		epsilonNFA.addFinalState(2);
		epsilonNFA.addTransition(0, 0, 1);
		epsilonNFA.addTransition(0, 0, 2);
		epsilonNFA.addTransition(1, 1, 2);

		// Convert the epsilon-NFA to an NFA
		NFA nfa = epsilonNFA.convertToNFA();

		// Verify that the start state of the NFA is the epsilon closure of the start
		// state of the epsilon-NFA
		Set<Integer> expectedStartState = epsilonNFA.epsilonClosure(0);
		assertEquals(expectedStartState, nfa.getStartStateGroup());

		// Verify that the final states of the NFA are the same as the final states of
		// the epsilon-NFA
		Set<Integer> expectedFinalStates = epsilonNFA.epsilonClosure(2);
		assertEquals(expectedFinalStates, nfa.getFinalStates());

		// Verify that the transition table of the NFA is correct
		Map<Integer, Map<Integer, Set<Integer>>> expectedTransitionTable = new HashMap<>();
		Map<Integer, Set<Integer>> state0Transitions = new HashMap<>();
		state0Transitions.put(0, new HashSet<>(Arrays.asList(1, 2)));
		expectedTransitionTable.put(0, state0Transitions);
		Map<Integer, Set<Integer>> state1Transitions = new HashMap<>();
		state1Transitions.put(1, new HashSet<>(Arrays.asList(2)));
		expectedTransitionTable.put(1, state1Transitions);

		// assertEquals(expectedTransitionTable, nfa.getTransitionTable());
	}

}
