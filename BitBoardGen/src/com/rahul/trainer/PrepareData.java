package com.rahul.trainer;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import com.rahul.bbgen.MyDataSetRow;

public class PrepareData {
	
	private static ArrayList<MyDataSetRow> data = new ArrayList<MyDataSetRow>();
	
	private static void readData() {
		ObjectInputStream ois;
		MyDataSetRow dataSetRow;
		try {
			ois = new ObjectInputStream(new FileInputStream("./data/game4.bb"));
			while((dataSetRow = (MyDataSetRow) ois.readObject()) != null) {
				data.add(dataSetRow);
			}
			ois.close();
		} catch(Exception e) {
		}
	}
	
	private static void printData() {
		int i = 0;
		for(MyDataSetRow dataSetRow : data) {
			prettyPrint(dataSetRow.getRow());
			i++;
		}
		System.out.println("Total rows : " + i);
	}

	private static void prettyPrint(double[] row) {
		for(double d : row)
			System.out.printf("%.0f", d);
		System.out.println();
	}
	
	public static ArrayList<MyDataSetRow> getData() {
		readData();
		return data;
	}

	public static void main(String[] args) {
		PrepareData.readData();
		PrepareData.printData();
	}

}
