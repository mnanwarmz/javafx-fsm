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

	public int getStartState() {
		return startState;
	}

	public NFA convertToNFA(RegularGrammar grammar) {
		// Initialize the NFA with the number of states and symbols
		NFA nfa = new NFA(grammar.getNumStates(), grammar.getNumSymbols());

		// Set the start state for the NFA
		nfa.setStartState(grammar.getStartState());

		// Add the final states for the NFA
		for (int finalState : grammar.getFinalStates()) {
			nfa.addFinalState(finalState);
		}

		// Create the transition table for the NFA
		Map<Integer, Map<Integer, Set<Integer>>> transitionTable = new HashMap<>();

		// For each rule in the grammar's production rules
		for (int currentState : productionRules.keySet()) {
			Map<Integer, Set<Integer>> stateTransitions = productionRules.get(currentState);
			for (int symbol : stateTransitions.keySet()) {
				Set<Integer> nextStates = stateTransitions.get(symbol);
				for (int nextState : nextStates) {
					// If the transition table already contains the current state
					if (transitionTable.containsKey(currentState)) {
						// If the transition table for the current state already contains the symbol
						if (transitionTable.get(currentState).containsKey(symbol)) {
							// Add the next state to the set of states reachable from the current state with
							// the symbol
							transitionTable.get(currentState).get(symbol).add(nextState);
						} else {
							// Create a new set for the next state and add it to the transition table for
							// the current state and symbol
							Set<Integer> nextStatesTemp = new HashSet<>();
							nextStatesTemp.add(nextState);
							transitionTable.get(currentState).put(symbol, nextStatesTemp);
						}
					} else {
						// Create a new set for the next state and add it to the transition table for
						// the current state and symbol
						Set<Integer> nextStatesTemp = new HashSet<>();
						nextStatesTemp.add(nextState);
						Map<Integer, Set<Integer>> transitions = new HashMap<>();
						transitions.put(symbol, nextStatesTemp);
						transitionTable.put(currentState, transitions);
					}
				}
			}
		}

		// Set the transition table for the NFA
		nfa.setTransitionTable(transitionTable);

		return nfa;
	}

}
