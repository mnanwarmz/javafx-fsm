package com.example.javafxfsm.models;

import java.util.*;

public class DFA {
	private int numStates;
	private int numSymbols;
	private Map<Integer, Map<Integer, Integer>> transitions;
	private Set<Integer> finalStates;

	public DFA(int numStates, int numSymbols) {
		this.numStates = numStates;
		this.numSymbols = numSymbols;
		transitions = new HashMap<>();
		for (int i = 0; i < numStates; i++) {
			transitions.put(i, new HashMap<>());
		}
		finalStates = new HashSet<>();
	}

	public void addTransition(int fromState, int symbol, int toState) {
		transitions.get(fromState).put(symbol, toState);
	}

	public void addFinalState(int state) {
		finalStates.add(state);
	}

	public Set<Integer> getFinalStates() {
		return finalStates;
	}

	public boolean accepts(String input) {
		int state = 0;
		for (int i = 0; i < input.length(); i++) {
			int symbol = input.charAt(i) - '0';
			state = transitions.get(state).get(symbol);
		}
		return finalStates.contains(state);
	}
}
