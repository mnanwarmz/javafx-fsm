package com.example.javafxfsm;

public class Rule {
	private Character leftHandSide;
	private String rightHandSide;

	public Rule(Character leftHandSide, String rightHandSide) {
		this.leftHandSide = leftHandSide;
		this.rightHandSide = rightHandSide;
	}

	public Character getLeftHandSide() {
		return leftHandSide;
	}

	public String getRightHandSide() {
		return rightHandSide;
	}
}