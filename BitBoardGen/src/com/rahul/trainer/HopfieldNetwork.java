package com.rahul.trainer;

import java.util.ArrayList;

import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.Hopfield;

import com.rahul.bbgen.BitBoard;
import com.rahul.bbgen.RowData;

public class HopfieldNetwork {

	public static void main(String args[]) {

		ArrayList<RowData> data;

		// create training set
		data = PrepareData.getData();
		DataSet trainingSet = new DataSet(BitBoard.BOARD_LENGTH * 12, 1);
		for (RowData row : data) {
			trainingSet.addRow(PrepareData.inputToDoubleArray(row.getInput()),
					PrepareData.expectedOutputToDoubleArray(row
							.getExpectedOutput()));
		}

		// create hopfield network
		Hopfield myHopfield = new Hopfield(BitBoard.BOARD_LENGTH * 12);
		// learn the training set
		myHopfield.learn(trainingSet);

		// test hopfield network
		System.out.println("Testing network");

		// print network output for the each element from the specified training
		// set.
		int counter = 0;
		for (DataSetRow trainingSetRow : trainingSet.getRows()) {
			myHopfield.setInput(trainingSetRow.getInput());
			myHopfield.calculate();
			double[] networkOutput = myHopfield.getOutput();

			// printArray(trainingSetRow.getInput());
			// printArray(networkOutput);
			printPairwiseError(trainingSetRow.getInput(), networkOutput);
			
			counter++;
		}
		System.out.println("Total rows : " + counter);
	}

	private static void printArray(double[] a) {
		for(int i=0;i<a.length; i++)
			System.out.printf("%.0f", a[i]);
		System.out.println();
	}

	private static void printPairwiseError(double[] a, double[] b) {
		float error = 0f;
		for (int i = 0; i < a.length; i++) {
			if (a[i] != b[i])
				error++;
		}
		System.out.println(error / a.length);
	}
}