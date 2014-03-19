package com.rahul.trainer;

import java.util.ArrayList;
import java.util.Arrays;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.core.events.LearningEvent;
import org.neuroph.core.events.LearningEventListener;
import org.neuroph.core.learning.LearningRule;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.BackPropagation;
import org.neuroph.nnet.learning.MomentumBackpropagation;
import org.neuroph.util.TransferFunctionType;

import com.rahul.bitboard.BitBoard;
import com.rahul.numboard.NumBoard;

public class MLP implements LearningEventListener {

	public static void main(String[] args) {
		new MLP().run(false);
	}

	/**
	 * isBit should be set to true for training the network on bitboard data and
	 * false for training from numboard data
	 * 
	 * @param isBit
	 */
	public void run(boolean isBit) {

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
			for (com.rahul.numboard.RowData row : data) {
				trainingSet.addRow(row.getInput(), row.getExpectedOutput());
			}
		}

		// trainingSet.normalize();

		// create multi layer perceptron
		MultiLayerPerceptron myMlPerceptron;

		if (isBit) {
			myMlPerceptron = new MultiLayerPerceptron(
					TransferFunctionType.TANH, BitBoard.BOARD_LENGTH * 12,
					BitBoard.BOARD_LENGTH * 24, BitBoard.BOARD_LENGTH * 12, 1);
		} else {
			myMlPerceptron = new MultiLayerPerceptron(
					TransferFunctionType.TANH, NumBoard.BOARD_SIZE,
					NumBoard.BOARD_SIZE * 4, NumBoard.BOARD_SIZE * 2,
					NumBoard.BOARD_SIZE, 1);
		}

		// enable batch if using MomentumBackpropagation
		if (myMlPerceptron.getLearningRule() instanceof MomentumBackpropagation)
			((MomentumBackpropagation) myMlPerceptron.getLearningRule())
					.setBatchMode(true);

		LearningRule learningRule = myMlPerceptron.getLearningRule();
		learningRule.addListener(this);

		// learn the training set
		System.out.println("Training neural network...");
		myMlPerceptron.learn(trainingSet);

		// test perceptron
		System.out.println("Testing trained neural network");
		testNeuralNetwork(myMlPerceptron, trainingSet);

		// save trained neural network
		myMlPerceptron.save("2Layer_try01.nnet");

		// load saved neural network
		NeuralNetwork loadedMlPerceptron = NeuralNetwork
				.load("2Layer_try01.nnet");

		// test loaded neural network
		System.out.println("Testing loaded neural network");
		testNeuralNetwork(loadedMlPerceptron, trainingSet);
	}

	public static void testNeuralNetwork(NeuralNetwork neuralNet,
			DataSet testSet) {

		for (DataSetRow testSetRow : testSet.getRows()) {
			neuralNet.setInput(testSetRow.getInput());
			neuralNet.calculate();
			double[] networkOutput = neuralNet.getOutput();

			System.out
					.print("Input: " + Arrays.toString(testSetRow.getInput()));
			System.out.println(" Output: " + Arrays.toString(networkOutput));
		}
	}

	@Override
	public void handleLearningEvent(LearningEvent event) {
		BackPropagation bp = (BackPropagation) event.getSource();
		System.out.println(bp.getCurrentIteration() + ". iteration : "
				+ bp.getTotalNetworkError());
	}

}
