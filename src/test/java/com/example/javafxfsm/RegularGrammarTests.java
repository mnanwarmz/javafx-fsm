package com.example.javafxfsm;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;

public class RegularGrammarTests {
	// Test that the RegularGrammar constructor correctly initializes the alphabet
	// and rules.
	@Test
	public void testConstructor() {
		Set<Character> alphabet = new HashSet<Character>();
		alphabet.add('a');
		alphabet.add('b');
		List<Rule> rules = new ArrayList<Rule>();
		rules.add(new Rule('S', "aSb"));
		rules.add(new Rule('S', "bSa"));
		rules.add(new Rule('S', "a"));
		rules.add(new Rule('S', "b"));
		RegularGrammar grammar = new RegularGrammar(alphabet, rules);
		assertThat(grammar.getAlphabet()).isEqualTo(alphabet);
		assertThat(grammar.getRules()).isEqualTo(rules);
	}

	// Test that the addRule method correctly adds a rule to the grammar.
	@Test
	public void testAddRule() {
		Set<Character> alphabet = new HashSet<Character>();
		alphabet.add('a');
		alphabet.add('b');
		List<Rule> rules = new ArrayList<Rule>();
		rules.add(new Rule('S', "aSb"));
		rules.add(new Rule('S', "bSa"));
		rules.add(new Rule('S', "a"));
		rules.add(new Rule('S', "b"));
		RegularGrammar grammar = new RegularGrammar(alphabet, rules);
		grammar.addRule(new Rule('S', "ab"));
		rules.add(new Rule('S', "ab"));
		assertThat(grammar.getRules()).isEqualTo(rules);
	}

	// Test that the removeRule method correctly removes a rule from the grammar.
	@Test
	public void testRemoveRule() {
		Set<Character> alphabet = new HashSet<Character>();
		alphabet.add('a');
		alphabet.add('b');
		List<Rule> rules = new ArrayList<Rule>();
		rules.add(new Rule('S', "aSb"));
		rules.add(new Rule('S', "bSa"));
		rules.add(new Rule('S', "a"));
		rules.add(new Rule('S', "b"));
		RegularGrammar grammar = new RegularGrammar(alphabet, rules);
		grammar.removeRule(new Rule('S', "a"));
		rules.remove(new Rule('S', "a"));
		assertThat(grammar.getRules()).isEqualTo(rules);
	}

	// Test that the parse method correctly parses a string according to the rules
	// of the grammar.
	@Test
	public void testParse() {
		Set<Character> alphabet = new HashSet<Character>();
		alphabet.add('a');
		alphabet.add('b');
		List<Rule> rules = new ArrayList<Rule>();
		rules.add(new Rule('S', "aSb"));
		rules.add(new Rule('S', "bSa"));
		rules.add(new Rule('S', "a"));
		rules.add(new Rule('S', "b"));
		RegularGrammar grammar = new RegularGrammar(alphabet, rules);
		assertThat(grammar.parse("ab")).isEqualTo(true);
		assertThat(grammar.parse("ba")).isEqualTo(true);
		assertThat(grammar.parse("aab")).isEqualTo(true);
		assertThat(grammar.parse("baa")).isEqualTo(true);
		assertThat(grammar.parse("abab")).isEqualTo(true);
		assertThat(grammar.parse("baba")).isEqualTo(true);
		assertThat(grammar.parse("abba")).isEqualTo(false);
		assertThat(grammar.parse("babb")).isEqualTo(false);
		assertThat(grammar.parse("aabb")).isEqualTo(false);
		assertThat(grammar.parse("bbaa")).isEqualTo(false);
		assertThat(grammar.parse("aa")).isEqualTo(false);
		assertThat(grammar.parse("bb")).isEqualTo(false);
		assertThat(grammar.parse("")).isEqualTo(false);
	}
}