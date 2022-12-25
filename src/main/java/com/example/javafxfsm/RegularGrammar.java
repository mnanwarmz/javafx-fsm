package com.example.javafxfsm;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class RegularGrammar {
	private Set<Character> alphabet;
	private List<Rule> rules;

	public RegularGrammar(Set<Character> alphabet, List<Rule> rules) {
		this.alphabet = alphabet;
		this.rules = rules;
	}

	public Set<Character> getAlphabet() {
		return alphabet;
	}

	public List<Rule> getRules() {
		return rules;
	}

	public void addRule(Rule rule) {
		rules.add(rule);
	}

	public void removeRule(Rule rule) {
		rules.remove(rule);
	}

	public boolean parse(String input) {
		// initialize a stack for storing the current state of the parse
		Stack<Character> stack = new Stack<>();
		stack.push('S'); // start with the initial state 'S'

		// initialize a pointer to the current character in the input string
		int pointer = 0;

		while (!stack.isEmpty()) {
			// get the current character at the top of the stack
			Character current = stack.pop();

			// check if the current character is a terminal symbol (i.e., in the alphabet)
			if (alphabet.contains(current)) {
				// if it is, check if it matches the next character in the input string
				if (current == input.charAt(pointer)) {
					// if it does, move on to the next character in the input string
					pointer++;
				} else {
					// if it doesn't, the input string is not accepted by the grammar
					return false;
				}
			} else {
				// if the current character is not a terminal symbol, look for a rule that
				// derives it and push the right-hand side of the rule onto the Stackx
				for (Rule rule : rules) {
					if (rule.getLeftHandSide() == current) {
						for (int i = rule.getRightHandSide().length() - 1; i >= 0; i--) {
							stack.push(rule.getRightHandSide().charAt(i));
						}
						break;
					}
				}
			}
		}

		// if the stack is empty and the pointer has reached the end of the input
		// string,
		// the input string is accepted by the grammar
		return (input.length() == pointer);
	}
}