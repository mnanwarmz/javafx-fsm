package com.example.javafxfsm.models;

import java.util.Set;

import javafx.util.Pair;

import java.util.Map;

public class DFA {
	private Set<Integer> states;
	private Set<Character> inputSymbols;
	private Map<Pair<Integer, Character>, Integer> transitionFunction;
	private int startState;
	private Set<Integer> acceptStates;

	public DFA(Set<Integer> states, Set<Character> inputSymbols,
			Map<Pair<Integer, Character>, Integer> transitionFunction,
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

	public Map<Pair<Integer, Character>, Integer> getTransitionFunction() {
		return transitionFunction;
	}

	public void setTransitionFunction(Map<Pair<Integer, Character>, Integer> transitionFunction) {
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

	// public MinimizedDFA minimize() {
	// // Minimize the DFA using Hopcroft's algorithm
	// // and return the resulting minimized DFA
	// }

	// public TransitionTable generateTransitionTable() {
	// // Generate a transition table for the DFA
	// // and return the resulting transition table
	// }
}
