package com.example.javafxfsm.models;

public class Rule {
	private char leftHandSide;
	private String rightHandSide;

	public Rule(char leftHandSide, String rightHandSide) {
		this.leftHandSide = leftHandSide;
		this.rightHandSide = rightHandSide;
	}

	public char getLeftHandSide() {
		return leftHandSide;
	}

	public String getRightHandSide() {
		return rightHandSide;
	}

	public void setLeftHandSide(char leftHandSide) {
		this.leftHandSide = leftHandSide;
	}

	public void setRightHandSide(String rightHandSide) {
		this.rightHandSide = rightHandSide;
	}

	@Override
	public String toString() {
		return leftHandSide + " -> " + rightHandSide;
	}
}