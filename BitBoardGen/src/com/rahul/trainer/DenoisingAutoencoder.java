package com.rahul.trainer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.rahul.deeplearning.dA.dA;

public class DenoisingAutoencoder {

	private static float getError(int[] input, double[] output) {
		int error = 0;

		for (int i = 0; i < input.length; i++) {
			if (input[i] != Math.round(output[i]))
				error++;
		}

		return (float) error / input.length;
	}

	private static void prettyPrint(int[] input, double[] output) {
		System.out.println();
		for (int i = 0; i < input.length; i++) {
			System.out.print(input[i]);
		}
		System.out.println();
		for (int i = 0; i < output.length; i++) {
			System.out.print(Math.round(output[i]));
		}
		System.out.println();

	}

	private static void test_dA(int[][] train_X) {
		Random rng = new Random(123);

		double learning_rate = 0.1;
		double corruption_level = 0.0;
		int training_epochs = 100;

		int train_N = train_X.length;
		int test_N = 100;
		int n_visible = train_X[0].length;
		int n_hidden = 200;

		dA da = new dA(train_N, n_visible, n_hidden, null, null, null, rng);

		// train
		for (int epoch = 0; epoch < training_epochs; epoch++) {
			for (int i = 0; i < train_N; i++) {
				da.train(train_X[i], learning_rate, corruption_level);
			}
		}

		// test data
		int[][] test_X = new int[test_N][];
		for (int i = 0; i < test_N; i++) {
			int randomIndex = (int) (Math.random() * train_N);
			test_X[i] = train_X[randomIndex];
		}

		double[][] reconstructed_X = new double[test_N][n_visible];

		// test
		for (int i = 0; i < test_N; i++) {
			da.reconstruct(test_X[i], reconstructed_X[i]);

			prettyPrint(test_X[i], reconstructed_X[i]);
			System.out.println(getError(test_X[i], reconstructed_X[i]));
		}

	}
	
	public static void main(String[] args) {
		// int[][] trainData =
		// PrepareData.inputToIntMatrix("./data/bitboards_game4.bb");
		int[][] trainData = PrepareData.doubleToIntMatrix(PrepareData
				.getNumBoardData("./data/numboards_game4.bb"));
		Normalizer.normalize(trainData);
		test_dA(trainData);
	}
}
