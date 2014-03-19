package com.rahul.bitboard;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import com.rahul.stockfish.Stockfish;

public class Generator {

	private BitBoard WRooks;
	private BitBoard WBishops;
	private BitBoard WPawns;
	private BitBoard WKnights;
	private BitBoard WKing;
	private BitBoard WQueen;

	private BitBoard BRooks;
	private BitBoard BBishops;
	private BitBoard BPawns;
	private BitBoard BKnights;
	private BitBoard BKing;
	private BitBoard BQueen;

	private Stockfish stockfish;
	private double evalScore;

	private ArrayList<RowData> data;

	private ObjectOutputStream oos;
	private int turnChecker;

	// Left out purposefully
	// private double turn;

	public Generator() {
		stockfish = new Stockfish();
		turnChecker = -1;

		data = new ArrayList<RowData>();
	}

	private void initBoards() {
		WRooks = new BitBoard();
		WBishops = new BitBoard();
		WPawns = new BitBoard();
		WKnights = new BitBoard();
		WKing = new BitBoard();
		WQueen = new BitBoard();

		BRooks = new BitBoard();
		BBishops = new BitBoard();
		BPawns = new BitBoard();
		BKnights = new BitBoard();
		BKing = new BitBoard();
		BQueen = new BitBoard();
	}

	public void createBoards(String fen, boolean scorePresent) {
		initBoards();

		int bitIndex = 0;
		char[] fenChars = fen.toCharArray();

		for (int i = 0; i < fenChars.length && bitIndex < BitBoard.BOARD_LENGTH; i++) {
			switch (fenChars[i]) {
			case 'R':
				WRooks.flipBit(bitIndex++);
				break;
			case 'N':
				WKnights.flipBit(bitIndex++);
				break;
			case 'B':
				WBishops.flipBit(bitIndex++);
				break;
			case 'P':
				WPawns.flipBit(bitIndex++);
				break;
			case 'K':
				WKing.flipBit(bitIndex++);
				break;
			case 'Q':
				WQueen.flipBit(bitIndex++);
				break;

			case 'r':
				BRooks.flipBit(bitIndex++);
				break;
			case 'n':
				BKnights.flipBit(bitIndex++);
				break;
			case 'b':
				BBishops.flipBit(bitIndex++);
				break;
			case 'p':
				BPawns.flipBit(bitIndex++);
				break;
			case 'k':
				BKing.flipBit(bitIndex++);
				break;
			case 'q':
				BQueen.flipBit(bitIndex++);
				break;
			case '/':
				break;
			default:
				bitIndex += (int) fenChars[i] - 48;
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

	public String prettyPrintBoards() {
		StringBuilder builder = new StringBuilder();

		builder.append("White Rooks : ");
		builder.append(WRooks.getPrettyBitBoard() + "\n");
		builder.append("White Knights : ");
		builder.append(WKnights.getPrettyBitBoard() + "\n");
		builder.append("White Bishops : ");
		builder.append(WBishops.getPrettyBitBoard() + "\n");
		builder.append("White Pawns : ");
		builder.append(WPawns.getPrettyBitBoard() + "\n");
		builder.append("White King : ");
		builder.append(WKing.getPrettyBitBoard() + "\n");
		builder.append("White Queen : ");
		builder.append(WQueen.getPrettyBitBoard() + "\n");

		builder.append("Black Rooks : ");
		builder.append(BRooks.getPrettyBitBoard() + "\n");
		builder.append("Black Knights : ");
		builder.append(BKnights.getPrettyBitBoard() + "\n");
		builder.append("Black Bishops : ");
		builder.append(BBishops.getPrettyBitBoard() + "\n");
		builder.append("Black Pawns : ");
		builder.append(BPawns.getPrettyBitBoard() + "\n");
		builder.append("Black King : ");
		builder.append(BKing.getPrettyBitBoard() + "\n");
		builder.append("Black Queen : ");
		builder.append(BQueen.getPrettyBitBoard() + "\n");
		builder.append("Evaluation score : ");
		builder.append(evalScore + "\n");

		return builder.toString();
	}

	/**
	 * scorePresent : Pass true if evaluation scores are present in the input
	 * file. False otherwise. linesToRead : Number of lines to read or -1 for
	 * reading the whole file
	 */
	public void buildBoards(boolean scorePresent, int linesToRead) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(
					"./data/thinking.log")));
			String fen;
			int linesRead = 0;

			if (linesToRead == -1)
				linesToRead = Integer.MAX_VALUE;

			if (!scorePresent)
				stockfish.startEngine();
			while ((fen = br.readLine()) != null && linesRead++ < linesToRead) {
				createBoards(fen, scorePresent);
				turnChecker *= -1;
				createRow();
			}
			if (!scorePresent)
				stockfish.stopEngine();
			br.close();

			storeData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void storeData() {
		try {
			oos = new ObjectOutputStream(new FileOutputStream(new File(
					"./data/bitboards_thinking_1000.bb")));
			oos.writeObject(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void createRow() {
		StringBuilder builder = new StringBuilder();

		builder.append(WRooks.getPrettyBitBoard());
		builder.append(WKnights.getPrettyBitBoard());
		builder.append(WBishops.getPrettyBitBoard());
		builder.append(WPawns.getPrettyBitBoard());
		builder.append(WKing.getPrettyBitBoard());
		builder.append(WQueen.getPrettyBitBoard());

		builder.append(BRooks.getPrettyBitBoard());
		builder.append(BKnights.getPrettyBitBoard());
		builder.append(BBishops.getPrettyBitBoard());
		builder.append(BPawns.getPrettyBitBoard());
		builder.append(BKing.getPrettyBitBoard());
		builder.append(BQueen.getPrettyBitBoard());

		data.add(new RowData(builder.toString(), evalScore));
	}

	public static void main(String[] args) {
		Generator generator = new Generator();
		generator.buildBoards(true, 1000);
	}
}
