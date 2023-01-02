package com.example.javafxfsm.models;

import java.util.Set;
import java.util.Map;

public class RegularGrammar {
	private Set<Character> terminalSymbols;
	private Set<Character> nonTerminalSymbols;
	private Map<Character, String> productionRules;
	private char startSymbol;

	public RegularGrammar(Set<Character> terminalSymbols, Set<Character> nonTerminalSymbols,
			Map<Character, String> productionRules, char startSymbol) {
		this.terminalSymbols = terminalSymbols;
		this.nonTerminalSymbols = nonTerminalSymbols;
		this.productionRules = productionRules;
		this.startSymbol = startSymbol;
	}

	public Set<Character> getTerminalSymbols() {
		return terminalSymbols;
	}

	public void setTerminalSymbols(Set<Character> terminalSymbols) {
		this.terminalSymbols = terminalSymbols;
	}

	public Set<Character> getNonTerminalSymbols() {
		return nonTerminalSymbols;
	}

	public void setNonTerminalSymbols(Set<Character> nonTerminalSymbols) {
		this.nonTerminalSymbols = nonTerminalSymbols;
	}

	public Map<Character, String> getProductionRules() {
		return productionRules;
	}

	public void setProductionRules(Map<Character, String> productionRules) {
		this.productionRules = productionRules;
	}

	public char getStartSymbol() {
		return startSymbol;
	}

	public void setStartSymbol(char startSymbol) {
		this.startSymbol = startSymbol;
	}

	// public EpsilonNFA toEpsilonNFA() {
	// // Convert the regular grammar to an epsilon-NFA using the appropriate
	// algorithm
	// // and return the resulting epsilon-NFA
	// }
}
