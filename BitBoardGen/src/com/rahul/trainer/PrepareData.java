package com.rahul.trainer;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import com.rahul.bitboard.BitBoard;
import com.rahul.numboard.NumBoard;

public class PrepareData {

	private static ArrayList<com.rahul.bitboard.RowData> bitBoardData;
	private static ArrayList<com.rahul.numboard.RowData> numBoardData;

	@SuppressWarnings("unchecked")
	private static void readBitBoardData(String path) {
		ObjectInputStream ois;
		try {
			ois = new ObjectInputStream(new FileInputStream(path));
			bitBoardData = (ArrayList<com.rahul.bitboard.RowData>) ois
					.readObject();
			ois.close();
		} catch (Exception e) {
		}
	}

	@SuppressWarnings("unchecked")
	private static void readNumBoardData(String path) {
		ObjectInputStream ois;
		try {
			ois = new ObjectInputStream(new FileInputStream(path));
			numBoardData = (ArrayList<com.rahul.numboard.RowData>) ois
					.readObject();
			ois.close();
		} catch (Exception e) {
		}
	}

	public static double[] inputToDoubleArray(String input) {
		double[] row = new double[BitBoard.BOARD_LENGTH * 12];
		char[] temp = input.toCharArray();
		for (int i = 0; i < temp.length; i++) {
			row[i] = Double.parseDouble(temp[i] + "");
		}
		return row;
	}

	public static double[] expectedOutputToDoubleArray(double expectedOutput) {
		double[] evalScore = new double[1];
		evalScore[0] = expectedOutput;
		return evalScore;
	}

	public static ArrayList<com.rahul.bitboard.RowData> getBitBoardData(
			String path) {
		readBitBoardData(path);
		return bitBoardData;
	}

	public static ArrayList<com.rahul.numboard.RowData> getNumBoardData(
			String path) {
		readNumBoardData(path);
		return numBoardData;
	}

	public static int[][] inputToIntMatrix(String path) {
		int[][] bitBoardDataSet;
		char[] temp;

		readBitBoardData(path);
		bitBoardDataSet = new int[bitBoardData.size()][BitBoard.BOARD_LENGTH * 12];
		for (int i = 0; i < bitBoardData.size(); i++) {
			temp = bitBoardData.get(i).getInput().toCharArray();
			for (int j = 0; j < BitBoard.BOARD_LENGTH * 12; j++) {
				bitBoardDataSet[i][j] = temp[j] - 48;
			}
		}

		return bitBoardDataSet;
	}

	public static int[][] doubleToIntMatrix(
			ArrayList<com.rahul.numboard.RowData> data) {
		int[][] numBoardDataSet;
		double[] temp;

		numBoardDataSet = new int[data.size()][NumBoard.BOARD_SIZE];
		for (int i = 0; i < data.size(); i++) {
			temp = data.get(i).getInput();
			for (int j = 0; j < NumBoard.BOARD_SIZE; j++) {
				numBoardDataSet[i][j] = (int) temp[j];
			}
		}

		return numBoardDataSet;
	}

	public static void main(String[] args) {
		readNumBoardData("./data/numboards_game4.bb");
		for (com.rahul.numboard.RowData row : numBoardData)
			System.out.println(row);
	}

}
