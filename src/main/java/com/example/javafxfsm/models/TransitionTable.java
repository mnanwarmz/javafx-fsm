package com.example.javafxfsm.models;

import java.util.List;

public class TransitionTable {
	private List<Integer> states;
	private List<Character> inputSymbols;
	private Integer[][] transitions;

	public TransitionTable(List<Integer> states, List<Character> inputSymbols) {
		this.states = states;
		this.inputSymbols = inputSymbols;
		this.transitions = new Integer[states.size()][inputSymbols.size()];
	}

	public List<Integer> getStates() {
		return states;
	}

	public void setStates(List<Integer> states) {
		this.states = states;
	}

	public List<Character> getInputSymbols() {
		return inputSymbols;
	}

	public void setInputSymbols(List<Character> inputSymbols) {
		this.inputSymbols = inputSymbols;
	}

	public Integer[][] getTransitions() {
		return transitions;
	}

	public void setTransitions(Integer[][] transitions) {
		this.transitions = transitions;
	}

	public void addTransition(int state, char inputSymbol, int nextState) {
		int stateIndex = states.indexOf(state);
		int inputSymbolIndex = inputSymbols.indexOf(inputSymbol);
		transitions[stateIndex][inputSymbolIndex] = nextState;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("  ");
		for (char inputSymbol : inputSymbols) {
			sb.append(inputSymbol + " ");
		}
		sb.append("\n");
		for (int i = 0; i < states.size(); i++) {
			sb.append(states.get(i) + " ");
			for (int j = 0; j < inputSymbols.size(); j++) {
				sb.append(transitions[i][j] + " ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
