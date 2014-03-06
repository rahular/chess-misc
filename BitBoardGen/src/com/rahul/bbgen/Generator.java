package com.rahul.bbgen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectOutputStream;

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
	private double[] evalScore;
	
	private ObjectOutputStream oos;
	private int turnChecker;
	// Left out purposefully
	// private double turn;
	
	public Generator() {
		stockfish = new Stockfish();
		turnChecker = -1;
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
		
		evalScore = new double[1];
	}

	public void createBoards(String fen) {
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
		evalScore[0] = turnChecker * stockfish.getEvalScore(fen, 2000);
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
		builder.append(evalScore[0] + "\n");
		
		return builder.toString();
	}

	public void buildBoards() {
		try {
			MyDataSetRow dataSetRow;
			BufferedReader br = new BufferedReader(new FileReader(new File(
					"./data/game4.fen")));
			String fen;

			openObjectStream();
			stockfish.startEngine();
			while ((fen = br.readLine()) != null) {
				createBoards(fen);
				turnChecker *= -1;
				dataSetRow = buildInputData();
				storeInputData(dataSetRow);
			}
			closeObjectStream();
			stockfish.stopEngine();
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void closeObjectStream() {
		try {
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void openObjectStream() {
		try {
			oos = new ObjectOutputStream(new FileOutputStream("./data/game4.bb"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void storeInputData(MyDataSetRow dataSetRow) {
		
		try {
			oos.writeObject(dataSetRow);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private MyDataSetRow buildInputData() {
		MyDataSetRow dataSetRow = new MyDataSetRow();
		dataSetRow.setBBishops(BBishops.getBitBoard());
		dataSetRow.setBKing(BKing.getBitBoard());
		dataSetRow.setBKnights(BKnights.getBitBoard());
		dataSetRow.setBPawns(BPawns.getBitBoard());
		dataSetRow.setBQueen(BQueen.getBitBoard());
		dataSetRow.setBRooks(BRooks.getBitBoard());

		dataSetRow.setWBishops(WBishops.getBitBoard());
		dataSetRow.setWKing(WKing.getBitBoard());
		dataSetRow.setWKnights(WKnights.getBitBoard());
		dataSetRow.setWPawns(WPawns.getBitBoard());
		dataSetRow.setWQueen(WQueen.getBitBoard());
		dataSetRow.setWRooks(WRooks.getBitBoard());
		
		dataSetRow.setEvalScore(evalScore);

		return dataSetRow;
	}

	public static void main(String[] args) {
		Generator generator = new Generator();
		generator.buildBoards();
	}
}
