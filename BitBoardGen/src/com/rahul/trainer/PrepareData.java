package com.rahul.trainer;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import com.rahul.bbgen.DataSetRow;

public class PrepareData {
	
	public static ArrayList<DataSetRow> data = new ArrayList<DataSetRow>();
	
	private static void readData() {
		ObjectInputStream ois;
		DataSetRow dataSetRow;
		try {
			ois = new ObjectInputStream(new FileInputStream("./data/game4.bb"));
			while((dataSetRow = (DataSetRow) ois.readObject()) != null) {
				data.add(dataSetRow);
			}
			ois.close();
		} catch(Exception e) {
		}
	}
	
	private static void printData() {
		int i = 0;
		for(DataSetRow dataSetRow : data) {
			prettyPrint(dataSetRow.getRow());
			System.out.println(" " + dataSetRow.getEvalScore()[0]);
			i++;
		}
		System.out.println("Total rows : " + i);
	}

	private static void prettyPrint(double[] row) {
		for(double d : row)
			System.out.printf("%.0f", d);
	}

	public static void main(String[] args) {
		PrepareData.readData();
		PrepareData.printData();
	}

}
