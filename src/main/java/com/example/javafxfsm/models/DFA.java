package com.example.javafxfsm.models;

import java.util.*;

public class DFA {
	private int numStates;
	private int numSymbols;
	private int startState;
	private Map<Integer, Map<Integer, Integer>> transitions;
	private Map<Integer, Map<Integer, Set<Integer>>> transitionTable;
	private Set<Integer> finalStates;
	private Map<Set<Integer>, Integer> stateMap = new HashMap<>();
	private int stateCounter = 0;

	public DFA(int numStates, int numSymbols) {
		this.numStates = numStates;
		this.numSymbols = numSymbols;
		transitions = new HashMap<>();
		for (int i = 0; i < numStates; i++) {
			transitions.put(i, new HashMap<>());
		}
		finalStates = new HashSet<>();
	}

	public DFA() {
		transitionTable = new HashMap<>();
	}

	public int getNumStates() {
		return transitionTable.size();
	}

	public void addTransition(int fromState, int symbol, int toState) {
		transitions.get(fromState).put(symbol, toState);
	}

	public Map<Integer, Map<Integer, Integer>> getTransitions() {
		return transitions;
	}

	public void addTransition(Set<Integer> fromState, int symbol, Set<Integer> toState) {
		if (!stateMap.containsKey(fromState)) {
			stateMap.put(fromState, stateCounter);
			transitionTable.put(stateCounter, new HashMap<>());
			stateCounter++;
		}
		if (!stateMap.containsKey(toState)) {
			stateMap.put(toState, stateCounter);
			transitionTable.put(stateCounter, new HashMap<>());
			stateCounter++;
		}
		transitionTable.get(stateMap.get(fromState)).put(symbol, toState);
	}

	public void addFinalState(int state) {
		finalStates.add(state);
	}

	public void addFinalStateGroup(Set<Integer> states) {
		finalStates.addAll(states);
	}

	public Set<Integer> getFinalStates() {
		return finalStates;
	}

	public void setStartState(int startState) {
		this.startState = startState;
	}

	public void setTransitionTable(Map<Integer, Map<Integer, Set<Integer>>> transitionTable) {
		this.transitionTable = transitionTable;
	}

	public int getStartState() {
		return startState;
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
