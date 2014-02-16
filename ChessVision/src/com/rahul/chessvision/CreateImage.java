package com.rahul.chessvision;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class CreateImage {

	private static String FILE_PATH = "/home/rahul/Documents/deep_workspace/DeepLearning/logs/game4.fen";

	private static void readAndConvert() {
		String fen;
		int counter = 0;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(new File(FILE_PATH)));
			while ((fen = br.readLine()) != null) {
				if (!DrawBoard.fenToPng(fen))
					System.out.println("Oops! Check code..");
				Thread.sleep(50);
				counter++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(counter + " images created.");
	}

	public static void main(String[] args) {
		readAndConvert();
	}
}
