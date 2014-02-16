package com.rahul.chessvision;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

public class DrawBoard {

	private static final String PRE_STRING_PATH = "./html/pre.txt";
	private static final String POST_STRING_PATH = "./html/post.txt";
	private static final String HTML_FILE_PATH = "./html/index.html";
	private static final String CMD = "./lib/wkhtmltoimage --crop-w 412 ./html/index.html ./img/";

	public static boolean fenToHtml(String fen) {
		String html = "", line;
		try {
			// Write the first part of HTML
			BufferedReader br = new BufferedReader(new FileReader(new File(
					PRE_STRING_PATH)));
			while ((line = br.readLine()) != null)
				html += line;
			br.close();

			html += fen;

			// Write second part of HTML
			br = new BufferedReader(new FileReader(new File(POST_STRING_PATH)));
			while ((line = br.readLine()) != null)
				html += line;
			br.close();

			// Create HTML
			PrintWriter out = new PrintWriter(HTML_FILE_PATH);
			out.println(html);
			out.close();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	private static boolean htmlToPng() {
		String fileName = System.currentTimeMillis() + ".png";
		try {
			Runtime.getRuntime().exec(CMD + fileName);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public static boolean fenToPng(String fen) {
		return fenToHtml(fen) & htmlToPng();
	}

	public static void main(String[] args) {
		String fen = "r1bqkbnr/pppp1ppp/2n5/1B2p3/4P3/5N2/PPPP1PPP/RNBQK2R";
		fenToPng(fen);
	}

}
