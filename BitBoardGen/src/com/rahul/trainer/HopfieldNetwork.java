package com.rahul.trainer;

import java.util.ArrayList;
import java.util.List;

import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.Hopfield;

import com.rahul.bitboard.BitBoard;
import com.rahul.numboard.NumBoard;

public class HopfieldNetwork {

	/**
	 * TRUE if training on bitboards FALSE if training on numboards
	 */
	private static boolean isBit = false;

	public static void main(String args[]) {

		DataSet trainingSet;

		// create training set
		if (isBit) {
			ArrayList<com.rahul.bitboard.RowData> data;
			trainingSet = new DataSet(BitBoard.BOARD_LENGTH * 12, 1);
			data = PrepareData.getBitBoardData("./data/bitboards_game4.bb");
			for (com.rahul.bitboard.RowData row : data) {
				trainingSet.addRow(PrepareData.inputToDoubleArray(row
						.getInput()), PrepareData
						.expectedOutputToDoubleArray(row.getExpectedOutput()));
			}
		} else {
			ArrayList<com.rahul.numboard.RowData> data;
			trainingSet = new DataSet(NumBoard.BOARD_SIZE, 1);
			data = PrepareData.getNumBoardData("./data/numboards_game4.bb");
			Normalizer.normalize(data);
			for (com.rahul.numboard.RowData row : data) {
				trainingSet.addRow(row.getInput(), row.getExpectedOutput());
			}
		}

		// create hopfield network
		Hopfield myHopfield;
		if (isBit)
			myHopfield = new Hopfield(BitBoard.BOARD_LENGTH * 12);
		else {
			myHopfield = new Hopfield(NumBoard.BOARD_SIZE);
		}
		// learn the training set
		myHopfield.learn(trainingSet);

		// test hopfield network
		System.out.println("Testing network");

		// create test set
		DataSet testData = trainingSet;
		/*
		 * data = PrepareData.getData("./data/bitboards_game4.bb"); DataSet
		 * testData = new DataSet(BitBoard.BOARD_LENGTH * 12, 1); for (RowData
		 * row : data) {
		 * testData.addRow(PrepareData.inputToDoubleArray(row.getInput()),
		 * PrepareData.expectedOutputToDoubleArray(row .getExpectedOutput())); }
		 */

		// print network output
		int counter = 0;
		List<DataSetRow> testSet = testData.getRows();
		for (DataSetRow testSetRow : testSet) {
			myHopfield.setInput(testSetRow.getInput());
			myHopfield.calculate();
			myHopfield.calculate();
			myHopfield.calculate();
			myHopfield.calculate();
			myHopfield.calculate();
			myHopfield.calculate();
			myHopfield.calculate();
			myHopfield.calculate();
			myHopfield.calculate();
			double[] networkOutput = myHopfield.getOutput();

			printArray(testSetRow.getInput());
			printArray(networkOutput);
			printPairwiseError(testSetRow.getInput(), networkOutput);

			counter++;
		}
		System.out.println("Total rows : " + counter);
	}

	private static void printArray(double[] a) {
		for (int i = 0; i < a.length; i++)
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