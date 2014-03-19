package com.rahul.numboard;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import com.rahul.stockfish.Stockfish;

public class NumBoard {

	public static final int BOARD_SIZE = 64;
	private static final int EmptySquare = 0;

	private static final int WRook = 2;
	private static final int WBishop = 3;
	private static final int WPawn = 5;
	private static final int WKnight = 7;
	private static final int WKing = 11;
	private static final int WQueen = 13;

	private static final int BRook = 17;
	private static final int BBishop = 19;
	private static final int BPawn = 23;
	private static final int BKnight = 29;
	private static final int BKing = 31;
	private static final int BQueen = 37;

	// private static final int BRook = -2;
	// private static final int BBishop = -3;
	// private static final int BPawn = -5;
	// private static final int BKnight = -7;
	// private static final int BKing = -11;
	// private static final int BQueen = -13;

	private double[] board;
	private double evalScore;
	private Stockfish stockfish;
	private int turnChecker;

	private ArrayList<RowData> data;

	private ObjectOutputStream oos;

	public NumBoard() {
		data = new ArrayList<RowData>();
		stockfish = new Stockfish();
		turnChecker = -1;
	}

	/**
	 * scorePresent : Pass true if evaluation scores are present in the input
	 * file. False otherwise. linesToRead : Number of lines to read or -1 for
	 * reading the whole file
	 */
	public void buildBoards(boolean scorePresent, int linesToRead) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(
					"./data/game4.log")));
			String fen;
			int linesRead = 0;

			if (linesToRead == -1)
				linesToRead = Integer.MAX_VALUE;

			if (!scorePresent)
				stockfish.startEngine();
			while ((fen = br.readLine()) != null && linesRead++ < linesToRead) {
				createBoard(fen, scorePresent);
				turnChecker *= -1;
				createRow();
			}
			if (!scorePresent)
				stockfish.stopEngine();
			br.close();

			printBoards();
			storeData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void printBoards() {
		for (RowData row : data)
			System.out.println(row);
	}

	private void createRow() {
		double[] expectedOutput = { evalScore };
		data.add(new RowData(board, expectedOutput));
	}

	private void createBoard(String fen, boolean scorePresent) {
		board = new double[BOARD_SIZE];

		int index = 0;
		char[] fenChars = fen.toCharArray();

		for (int i = 0; i < fenChars.length && index < BOARD_SIZE; i++) {
			switch (fenChars[i]) {
			case 'R':
				board[index++] = WRook;
				break;
			case 'N':
				board[index++] = WKnight;
				break;
			case 'B':
				board[index++] = WBishop;
				break;
			case 'P':
				board[index++] = WPawn;
				break;
			case 'K':
				board[index++] = WKing;
				break;
			case 'Q':
				board[index++] = WQueen;
				break;

			case 'r':
				board[index++] = BRook;
				break;
			case 'n':
				board[index++] = BKnight;
				break;
			case 'b':
				board[index++] = BBishop;
				break;
			case 'p':
				board[index++] = BPawn;
				break;
			case 'k':
				board[index++] = BKing;
				break;
			case 'q':
				board[index++] = BQueen;
				break;
			case '/':
				break;
			default:
				int emptySquares = (int) fenChars[i] - 48;
				while (emptySquares-- != 0)
					board[index++] = EmptySquare;

			}
		}
		try {
			if (scorePresent) {
				evalScore = Integer.parseInt(fen.split("\\[")[1].split("]")[0]) / 100.0f;
			} else {
				evalScore = turnChecker * stockfish.getEvalScore(fen, 2000);
			}
		} catch (Exception e) {
			evalScore = 0;
			return;
		}
	}

	private void storeData() {
		try {
			oos = new ObjectOutputStream(new FileOutputStream(new File(
					"./data/numboards_game4.bb")));
			oos.writeObject(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		NumBoard generator = new NumBoard();
		generator.buildBoards(false, -1);
	}

}
