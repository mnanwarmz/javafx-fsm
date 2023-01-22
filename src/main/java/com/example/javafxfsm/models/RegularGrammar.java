package com.example.javafxfsm.models;

import java.util.*;

public class RegularGrammar {
	private int numStates;
	private int numSymbols;
	private int startState;
	private Set<Integer> finalStates;
	private Map<Integer, Map<Integer, Set<Integer>>> productionRules;

	public RegularGrammar(int numStates, int numSymbols) {
		this.numStates = numStates;
		this.numSymbols = numSymbols;
		startState = 0;
		finalStates = new HashSet<>();
		productionRules = new HashMap<>();
		for (int i = 0; i < numStates; i++) {
			productionRules.put(i, new HashMap<>());
			for (int j = 0; j < numSymbols; j++) {
				productionRules.get(i).put(j, new HashSet<>());
			}
		}
	}

	public void setStartState(int startState) {
		this.startState = startState;
	}

	public void addFinalState(int finalState) {
		finalStates.add(finalState);
	}

	public void addProductionRule(int fromState, int symbol, int toState) {
		productionRules.get(fromState).get(symbol).add(toState);
	}

	public int getNumStates() {
		return numStates;
	}

	public int getNumSymbols() {
		return numSymbols;
	}

	public Set<Integer> getFinalStates() {
		return finalStates;
	}

	public Set<Integer> getNextStates(int fromState, int symbol) {
		return productionRules.get(fromState).get(symbol);
	}

}
