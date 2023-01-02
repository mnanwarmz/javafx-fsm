import java.util.Set;

import com.example.javafxfsm.models.RegularGrammar;

import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;

public class RegularGrammarParser {
	public static RegularGrammar parse(String input) {
		// Split the input string on newlines
		String[] lines = input.split("\n");

		// Parse the nonterminal symbols from the first line
		Set<Character> nonterminalSymbols = new HashSet<>();
		for (char c : lines[0].toCharArray()) {
			nonterminalSymbols.add(c);
		}

		// Parse the terminal symbols from the second line
		Set<Character> terminalSymbols = new HashSet<>();
		for (char c : lines[1].toCharArray()) {
			terminalSymbols.add(c);
		}

		// Parse the start symbol from the third line
		char startSymbol = lines[2].charAt(0);

		// Parse the production rules from the remaining lines
		Map<Character, String> productionRules = new HashMap<>();
		for (int i = 3; i < lines.length; i++) {
			char nonterminal = lines[i].charAt(0);
			String rightHandSide = lines[i].substring(3);
			productionRules.put(nonterminal, rightHandSide);
		}

		// Create a new RegularGrammar object and return it
		return new RegularGrammar(terminalSymbols, nonterminalSymbols, productionRules, startSymbol);
	}
}
