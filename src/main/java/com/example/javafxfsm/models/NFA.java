package com.example.javafxfsm.models;

import java.util.*;

public class NFA {
	private int numStates;
	private int numSymbols;
	private int startState;
	private Set<Integer> finalStates;
	private Set<Integer> startStates = new HashSet<>();

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

	private Set<Integer> move(Set<Integer> states, int symbol, NFA nfa) {
		Set<Integer> result = new HashSet<>();
		for (int state : states) {
			result.addAll(nfa.getTransitionTable().get(state).get(symbol));
		}
		return result;
	}

	public void setStartState(int startState) {
		this.startState = startState;
	}

	public int getStartState() {
		return startState;
	}

	public void addFinalState(int finalState) {
		finalStates.add(finalState);
	}

	public Set<Integer> getFinalStates() {
		return finalStates;
	}

	public int getNumStates() {
		return numStates;
	}

	public int getNumSymbols() {
		return numSymbols;
	}

	public void addTransition(int fromState, int symbol, int toState) {
		transitionTable.get(fromState).get(symbol).add(toState);

	}

	public void setTransitionTable(Map<Integer, Map<Integer, Set<Integer>>> transitionTable) {
		this.transitionTable = transitionTable;
	}

	public Map<Integer, Map<Integer, Set<Integer>>> getTransitionTable() {
		return transitionTable;
	}

	public Set<Integer> epsilonClosure(Set<Integer> states) {
		// Create a queue to hold the states to visit
		Queue<Integer> queue = new LinkedList<>();
		for (int state : states) {
			queue.offer(state);
		}

		// Create a set to hold the epsilon closure
		Set<Integer> closure = new HashSet<>(states);

		// Iterate through the queue
		while (!queue.isEmpty()) {
			int state = queue.poll();

			// Add all the states reachable from the current state with epsilon transitions
			for (int nextState : transitionTable.get(state).get(0)) {
				if (closure.add(nextState)) {
					queue.offer(nextState);
				}
			}
		}
		return closure;
	}

	public Set<Integer> epsilonClosure(int state) {
		// Create a queue to hold the states to visit
		Queue<Integer> queue = new LinkedList<>();
		queue.offer(state);

		// Create a set to hold the epsilon closure
		Set<Integer> closure = new HashSet<>();
		closure.add(state);

		// Iterate through the queue
		while (!queue.isEmpty()) {
			int currentState = queue.poll();

			// Add all the states reachable from the current state with epsilon transitions
			for (int nextState : transitionTable.get(currentState).get(0)) {
				if (closure.add(nextState)) {
					queue.offer(nextState);
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

	public void addFinalStateGroup(Set<Integer> states) {
		for (int state : states) {
			finalStates.add(state);
		}
	}

	public void addStartStateGroup(Set<Integer> states) {
		// for (int state : states) {
		// startState = state;
		// }
		for (int state : states) {
			startStates.add(state);
		}
	}

	public Set<Integer> getStartStateGroup() {
		return startStates;
	}

	public Set<Integer> getFinalStateGroup() {
		return finalStates;
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
		Map<Set<Integer>, Integer> groupIds = new HashMap<>();
		for (int i = 0; i < P.size(); i++) {
			groupIds.put(P.get(i), i);
		}
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
				List<Set<Integer>> P_copy = new ArrayList<>(P);
				for (Set<Integer> Y : P_copy) {
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
		for (int i = 0; i < P.size(); i++) {
			Set<Integer> group = P.get(i);
			if (group.contains(this.startState)) {
				minimizedDFA.setStartState(groupIds.get(group));
			}
			if (this.finalStates.stream().anyMatch(group::contains)) {
				minimizedDFA.addFinalState(groupIds.get(group));
			}
		}
		// Set the transition table for the minimized DFA
		for (int i = 0; i < P.size(); i++) {
			for (int a = 0; a < this.numSymbols; a++) {
				Set<Integer> nextStates = transitionTable.get(i).get(a);
				if (nextStates != null) {
					minimizedDFA.addTransition(i, a, groupIds.get(nextStates));
				}
			}
		}
		return minimizedDFA;
	}

	public NFA convertToNFA() {
		// Initialize the NFA with the same number of states and symbols
		NFA nfa = new NFA(numStates, numSymbols);

		// Set the start state and final states of the new NFA to be the epsilon-closure
		// of the start state and final states of the epsilon-NFA
		Set<Integer> startClosure = epsilonClosure(startState);
		nfa.addStartStateGroup(startClosure);
		for (int finalState : finalStates) {
			nfa.addFinalStateGroup(epsilonClosure(finalState));
		}

		// Create the transition table for the NFA
		Map<Integer, Map<Integer, Set<Integer>>> transitionTable = new HashMap<>();
		for (int currentState : epsilonClosure(startState)) {
			for (int symbol = 0; symbol < numSymbols; symbol++) {
				// Compute the set of states that can be reached from the epsilon-closure of the
				// current state using the symbol
				Set<Integer> nextStates = epsilonClosure(getNextStates(epsilonClosure(currentState), symbol));
				// If the transition table already contains the current state
				if (transitionTable.containsKey(currentState)) {
					// If the transition table for the current state already contains the symbol
					if (transitionTable.get(currentState).containsKey(symbol)) {
						// Add the next state to the set of states reachable from the current state with
						// the symbol
						transitionTable.get(currentState).get(symbol).addAll(nextStates);
					} else {
						// Create a new set for the next state and add it to the transition table for
						// the current state and symbol
						transitionTable.get(currentState).put(symbol, nextStates);
					}
				} else {
					// Create a new map for the symbol and add it to the transition table for the
					// current state
					Map<Integer, Set<Integer>> symbolMap = new HashMap<>();
					symbolMap.put(symbol, nextStates);
					transitionTable.put(currentState, symbolMap);
				}
			}
		}
		nfa.setTransitionTable(transitionTable);
		return nfa;
	}

	public DFA convertToDFA(NFA nfa) {
		// Initialize an empty set called Dstates, which will hold the states of the
		// DFA.
		Set<Set<Integer>> Dstates = new HashSet<>();
		// Initialize a queue called worklist, which will be used to keep track of the
		// states that still need to be processed.
		Queue<Set<Integer>> worklist = new LinkedList<>();
		// Create a new state in the DFA called the start state,
		// which contains the epsilon-closure of the initial state of the NFA,
		// and add it to Dstates and worklist.
		Set<Integer> start = nfa.epsilonClosure(nfa.getStartState());
		Dstates.add(start);
		worklist.add(start);
		// Initialize the DFA
		DFA dfa = new DFA();
		// While worklist is not empty:
		while (!worklist.isEmpty()) {
			// Dequeue a state S from worklist.
			Set<Integer> S = worklist.poll();
			// For each input symbol a:
			for (int a = 0; a < nfa.getNumSymbols(); a++) {
				// Compute the set of NFA states that can be reached from S by following the
				// input symbol a.
				Set<Integer> T = nfa.epsilonClosure(move(S, a, nfa));
				// If the epsilon-closure is not in Dstates, add it as a new state in the DFA
				// and add it to the worklist.
				if (!Dstates.contains(T)) {
					Dstates.add(T);
					worklist.add(T);
				}
				// Add a transition from S to the epsilon-closure state in the DFA for input
				// symbol a.
				dfa.addTransition(S, a, T);
			}
		}
		return dfa;
	}

}
