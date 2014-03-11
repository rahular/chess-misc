package com.rahul.trainer;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import com.rahul.bbgen.BitBoard;
import com.rahul.bbgen.RowData;

public class PrepareData {
	
	private static ArrayList<RowData> data;
	
	@SuppressWarnings("unchecked")
	private static void readData() {
		ObjectInputStream ois;
		try {
			ois = new ObjectInputStream(new FileInputStream("./data/bitboards.bb"));
			data = (ArrayList<RowData>) ois.readObject();
			ois.close();
		} catch(Exception e) {
		}
	}
	
	public static double[] inputToDoubleArray(String input) {
		double[] row = new double[BitBoard.BOARD_LENGTH * 12];
		char[] temp = input.toCharArray();
		for(int i=0; i < temp.length; i++) {
			row[i] = Double.parseDouble(temp[i] + "");
		}
		return row;
	}
	
	public static double[] expectedOutputToDoubleArray(double expectedOutput) {
		double[] evalScore = new double[1];
		evalScore[0] = expectedOutput;
		return evalScore;
	}
	
	private static void printData() {
		int i = 0;
		for(RowData row : data) {
			System.out.print(row.getInput() + " ");
			System.out.println(row.getExpectedOutput());
			i++;
		}
		System.out.println("Total rows : " + i);
	}
	
	public static ArrayList<RowData> getData() {
		readData();
		return data;
	}

	public static void main(String[] args) {
		PrepareData.readData();
		PrepareData.printData();
	}

}
