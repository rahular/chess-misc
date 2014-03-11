package com.rahul.bbgen;

import java.io.Serializable;


public class RowData implements Serializable {

	private static final long serialVersionUID = 1L;
	private String input;
	private double expectedOutput;
	
	public RowData(String input, double expectedOutput) {
		this.setInput(input);
		this.setExpectedOutput(expectedOutput);
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public double getExpectedOutput() {
		return expectedOutput;
	}

	public void setExpectedOutput(double expectedOutput) {
		this.expectedOutput = expectedOutput;
	}

}
