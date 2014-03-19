package com.rahul.trainer;

import java.util.ArrayList;
import java.util.Arrays;

public class Normalizer {
	
	public static void normalize(int[][] trainData) {
		for(int i=0; i<trainData.length; i++) {
			normalize(trainData[i]);
		}
	}
	
	public static void normalize(ArrayList<com.rahul.numboard.RowData> trainData) {
		for(int i=0; i<trainData.size(); i++) {
			normalize(trainData.get(i).getInput());
		}
	}

	private static void normalize(int[] array) {
		int[] temp = Arrays.copyOf(array, array.length);
		Arrays.sort(temp);
		int max = temp[temp.length - 1];
		
		for(int i=0; i<array.length; i++)
			array[i] /= max;
	}
	
	private static void normalize(double[] array) {
		double[] temp = Arrays.copyOf(array, array.length);
		Arrays.sort(temp);
		double max = temp[temp.length - 1];
		
		for(int i=0; i<array.length; i++)
			array[i] /= max;
	}
}
