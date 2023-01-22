package com.example.javafxfsm.models;

import java.util.*;

public class NFA {
	private int numStates;
	private int numSymbols;
	private int startState;
	private Set<Integer> finalStates;
	private Map<Integer, Map<Integer, Set<Integer>>> transitionTable;

	public NFA(int numStates, int numSymbols) {
		this.numStates = numStates;
		this.numSymbols = numSymbols;
		startState = 0;
		finalStates = new HashSet<>();
		transitionTable = new HashMap<>();
		for (int i = 0; i < numStates; i++) {
			transitionTable.put(i, new HashMap<>());
			for (int j = 0; j < numSymbols; j++) {
				transitionTable.get(i).put(j, new HashSet<>());
			}
		}
	}

	public void setStartState(int startState) {
		this.startState = startState;
	}

	public void addFinalState(int finalState) {
		finalStates.add(finalState);
	}

	public void addTransition(int fromState, int symbol, int toState) {
		transitionTable.get(fromState).get(symbol).add(toState);
	}

	public Set<Integer> epsilonClosure(Set<Integer> states) {
		// Create a stack to hold the states to visit
		Stack<Integer> stack = new Stack<>();
		for (int state : states) {
			stack.push(state);
		}

		// Create a set to hold the epsilon closure
		Set<Integer> closure = new HashSet<>(states);

		// Iterate through the stack
		while (!stack.isEmpty()) {
			int state = stack.pop();

			// Add all the states reachable from the current state with epsilon transitions
			for (int nextState : transitionTable.get(state).get(0)) {
				if (closure.add(nextState)) {
					stack.push(nextState);
				}
			}
		}
		return closure;
	}

	public Set<Integer> getNextStates(Set<Integer> states, int symbol) {
		Set<Integer> nextStates = new HashSet<>();
		for (int state : states) {
			nextStates.addAll(transitionTable.get(state).get(symbol));
		}
		return nextStates;
	}

	public NFA convertToNFA(RegularGrammar grammar) {
		NFA nfa = new NFA(grammar.getNumStates() * 2, grammar.getNumSymbols());
		Map<Integer, Set<Integer>> stateSets = new HashMap<>();

		// Initialize state sets
		for (int i = 0; i < grammar.getNumStates(); i++) {
			Set<Integer> set = new HashSet<>();
			set.add(i);
			stateSets.put(i, set);
		}

		// Implement subset construction algorithm here
		for (int i = 0; i < grammar.getNumStates(); i++) {
			for (int j = 0; j < grammar.getNumSymbols(); j++) {
				Set<Integer> nextStates = new HashSet<>();
				for (int state : stateSets.get(i)) {
					nextStates.addAll(grammar.getNextStates(state, j));
				}
				if (!nextStates.isEmpty()) {
					Set<Integer> epsilonClosure = epsilonClosure(nextStates);
					int nextStateSetId = -1;
					for (Map.Entry<Integer, Set<Integer>> entry : stateSets.entrySet()) {
						if (entry.getValue().equals(epsilonClosure)) {
							nextStateSetId = entry.getKey();
							break;
						}
					}
					if (nextStateSetId == -1) {
						nextStateSetId = stateSets.size();
						stateSets.put(nextStateSetId, epsilonClosure);
					}
					nfa.addTransition(i, j, nextStateSetId);
				}
			}
		}

		// Set start state and final states
		nfa.setStartState(0);
		for (int i = 0; i < stateSets.size(); i++) {
			for (int finalState : grammar.getFinalStates()) {
				if (stateSets.get(i).contains(finalState)) {
					nfa.addFinalState(i);
					break;
				}
			}
		}

		return nfa;
	}
}
