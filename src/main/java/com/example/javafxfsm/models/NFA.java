package com.example.javafxfsm.models;

import java.util.*;

public class NFA {
	private int numStates;
	private int numSymbols;
	private int startState;
	private Set<Integer> finalStates;
	private Map<Integer, Map<Integer, Set<Integer>>> transitionTable;
	private Map<Integer, Map<Integer, Set<Integer>>> transitions;

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

		transitions = new HashMap<>();
		for (int i = 0; i < numStates; i++) {
			transitions.put(i, new HashMap<>());
			for (int j = 0; j < numSymbols; j++) {
				transitions.get(i).put(j, new HashSet<>());
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

	public DFA minimizeDFA() {
		// create a new DFA object with number of states and symbols
		DFA minimizedDFA = new DFA(this.numStates, this.numSymbols);
		// Initialize the sets of final and non-final states
		Set<Integer> finalStates = new HashSet<>(this.finalStates);
		Set<Integer> nonFinalStates = new HashSet<>();
		for (int i = 0; i < this.numStates; i++) {
			if (!finalStates.contains(i)) {
				nonFinalStates.add(i);
			}
		}
		// Create the partition P = {F, Q-F}
		List<Set<Integer>> P = new ArrayList<>();
		P.add(finalStates);
		P.add(nonFinalStates);
		// Initialize the set W
		Set<Set<Integer>> W = new HashSet<>(P);
		// Create the transition table for the minimized DFA
		Map<Integer, Map<Integer, Set<Integer>>> transitionTable = new HashMap<>();
		// While W is not empty
		while (!W.isEmpty()) {
			// Remove a set A from W
			Set<Integer> A = W.iterator().next();
			W.remove(A);
			// For each symbol a in the alphabet
			for (int a = 0; a < this.numSymbols; a++) {
				// Initialize the set X
				Set<Integer> X = new HashSet<>();
				// For each state in A
				for (int q : A) {
					// Add the set of states reachable from q with symbol a to X
					X.addAll(this.transitionTable.get(q).get(a));
				}
				// For each set Y in P
				for (Set<Integer> Y : P) {
					// If X is a proper subset of Y
					if (Y.containsAll(X) && !Y.equals(X)) {
						// Create a new set Z = Y - X
						Set<Integer> Z = new HashSet<>(Y);
						Z.removeAll(X);
						// Add Z to P
						P.add(Z);
						// Add Z to W
						W.add(Z);
						// Update the transition table for the minimized DFA
						for (int q : Y) {
							if (transitionTable.containsKey(q)) {
								transitionTable.get(q).put(a, Z);
							} else {
								Map<Integer, Set<Integer>> transitions = new HashMap<>();
								transitions.put(a, Z);
								transitionTable.put(q, transitions);
							}
						}
					}
				}
			}
		}
		// Set the start state and final states for the minimized DFA
		for (Set<Integer> group : P) {
			if (group.contains(this.startState)) {
				minimizedDFA.startState = group;
			}
			if (!Collections.disjoint(group, this.finalStates)) {
				minimizedDFA.finalStates.add(group);
			}
		}
		minimizedDFA.transitionTable = transitionTable;
		return minimizedDFA;
	}

}
