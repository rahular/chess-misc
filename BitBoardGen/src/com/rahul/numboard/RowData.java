package com.rahul.numboard;

import java.io.Serializable;
import java.util.Arrays;

public class RowData implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private double[] input;
	private double[] expectedOutput;

	public RowData(double[] input, double[] expectedOutput) {
		this.input = input;
		this.expectedOutput = expectedOutput;
	}

	public double[] getInput() {
		return input;
	}

	public void setInput(double[] input) {
		this.input = input;
	}

	public double[] getExpectedOutput() {
		return expectedOutput;
	}

	public void setExpectedOutput(double[] expectedOutput) {
		this.expectedOutput = expectedOutput;
	}

	public String toString() {
		return Arrays.toString(input) + " : " + Arrays.toString(expectedOutput);
	}
}
