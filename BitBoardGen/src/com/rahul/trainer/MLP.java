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

import com.rahul.bbgen.BitBoard;
import com.rahul.bbgen.RowData;

public class MLP implements LearningEventListener {

	private ArrayList<RowData> data;

	public static void main(String[] args) {
		new MLP().run();
	}

	public void run() {

		// create training set
		data = PrepareData.getData();
		DataSet trainingSet = new DataSet(BitBoard.BOARD_LENGTH * 12, 1);
		for (RowData row : data) {
			trainingSet.addRow(PrepareData.inputToDoubleArray(row.getInput()),
					PrepareData.expectedOutputToDoubleArray(row
							.getExpectedOutput()));
		}

		trainingSet.normalize();

		// create multi layer perceptron
		MultiLayerPerceptron myMlPerceptron = new MultiLayerPerceptron(
				TransferFunctionType.TANH, BitBoard.BOARD_LENGTH * 12,
				BitBoard.BOARD_LENGTH * 24, BitBoard.BOARD_LENGTH * 12, 1);

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
