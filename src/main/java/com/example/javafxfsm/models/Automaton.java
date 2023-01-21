package com.example.javafxfsm.models;

public class Automaton {
	private int[][] transitionTable;
	private int numStates;
	private int numSymbols;

	public Automaton(int numStates, int numSymbols) {
		this.numStates = numStates;
		this.numSymbols = numSymbols;
		transitionTable = new int[numStates][numSymbols];
	}

	public void setTransition(int fromState, int symbol, int toState) {
		transitionTable[fromState][symbol] = toState;
	}

	public int getTransition(int fromState, int symbol) {
		return transitionTable[fromState][symbol];
	}

	public int getNumStates() {
		return numStates;
	}

	public int getNumSymbols() {
		return numSymbols;
	}

	public void displayTransitionTable() {
		System.out.print(" ");
		for (int i = 0; i < numSymbols; i++) {
			System.out.print(" " + i);
		}
		System.out.println();
		for (int i = 0; i < numStates; i++) {
			System.out.print(i + " ");
			for (int j = 0; j < numSymbols; j++) {
				System.out.print(transitionTable[i][j] + " ");
			}
			System.out.println();
		}
	}
}
