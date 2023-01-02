package com.example.javafxfsm.models;

import java.util.Set;

import javafx.util.Pair;

import java.util.Map;

public class NFA {
	private Set<Integer> states;
	private Set<Character> inputSymbols;
	private Map<Pair<Integer, Character>, Set<Integer>> transitionFunction;
	private int startState;
	private Set<Integer> acceptStates;

	public NFA(Set<Integer> states, Set<Character> inputSymbols,
			Map<Pair<Integer, Character>, Set<Integer>> transitionFunction,
			int startState, Set<Integer> acceptStates) {
		this.states = states;
		this.inputSymbols = inputSymbols;
		this.transitionFunction = transitionFunction;
		this.startState = startState;
		this.acceptStates = acceptStates;
	}

	public Set<Integer> getStates() {
		return states;
	}

	public void setStates(Set<Integer> states) {
		this.states = states;
	}

	public Set<Character> getInputSymbols() {
		return inputSymbols;
	}

	public void setInputSymbols(Set<Character> inputSymbols) {
		this.inputSymbols = inputSymbols;
	}

	public Map<Pair<Integer, Character>, Set<Integer>> getTransitionFunction() {
		return transitionFunction;
	}

	public void setTransitionFunction(Map<Pair<Integer, Character>, Set<Integer>> transitionFunction) {
		this.transitionFunction = transitionFunction;
	}

	public int getStartState() {
		return startState;
	}

	public void setStartState(int startState) {
		this.startState = startState;
	}

	public Set<Integer> getAcceptStates() {
		return acceptStates;
	}

	public void setAcceptStates(Set<Integer> acceptStates) {
		this.acceptStates = acceptStates;
	}

	// public DFA toDFA() {
	// // Convert the NFA to a DFA using the powerset construction
	// // and return the resulting DFA
	// }

	// public MinimizedDFA minimize() {
	// // Minimize the DFA using Hopcroft's algorithm
	// // and return the resulting minimized DFA
	// }

	// public TransitionTable generateTransitionTable() {
	// // Generate a transition table for the NFA
	// // and return the resulting transition table
	// }
}
