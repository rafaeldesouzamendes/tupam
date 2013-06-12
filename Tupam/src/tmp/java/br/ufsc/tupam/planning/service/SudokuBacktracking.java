package br.ufsc.tupam.planning.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class SudokuBacktracking {

	public class PossibilityComparator implements	 Comparator<List<Character>> {

		@Override
		public int compare(final List<Character> arg0, final List<Character> arg1) {
			return arg0.size() - arg1.size();
		}


	}

	private static int backtrackingNodes;

	public static void main(final String[] args) throws IOException {

		final char[] outputBoard = new char[81];

		for(int i = 1; i <= 11; ++i){
			final String fileName = "H"+i;
			SudokuBacktracking.readBoard(outputBoard, "/home/mendes/Documents/doc/disciplinas/AlgoritmosCombinatorios/a2data/a2data/" + fileName + ".txt");

			final char[] inputBoard = new char[81];

			System.arraycopy(outputBoard, 0, inputBoard, 0, 81);

			long time = 0;
			SudokuBacktracking.backtrackingNodes = 0;
			final long startTime = System.currentTimeMillis();
			System.out.println(fileName);

			try{
				SudokuBacktracking.backtrack1(outputBoard);
				time = System.currentTimeMillis() - startTime;

				SudokuBacktracking.printBoards(inputBoard,outputBoard);

			}catch(final ArrayIndexOutOfBoundsException e){
				SudokuBacktracking.printNoOutput(inputBoard);
				time = System.currentTimeMillis() - startTime;

			}finally{
				System.out.println();
				System.out.println("Backtrack nodes:" +  SudokuBacktracking.backtrackingNodes);
				System.out.println("Time:" +  (float)time/1000 + "s");
				System.out.println("---------------------");
				System.out.println();
			}
		}
	}

	public static void backtrack1(final char[] board){
		final boolean[] fixed = new boolean[81];
		boolean advance = true;

		//recover fixed positions
		for(int i = 0; i < 81; ++i)
			if(board[i] != 0)
				fixed[i] = true;

		//start at position 0
		int pos = 0;

		while(pos < 81){

			if(!fixed[pos]){

				char k = (char)(board[pos] + 1);

				for(; k <= 9; ++k)
					if(SudokuBacktracking.test(board, k, pos)){
						board[pos] = k;
						advance = true;
						++SudokuBacktracking.backtrackingNodes;
						break;
					}

				if(k > 9){ //enter in backtrack
					board[pos] = 0;
					advance = false;
				}
			}

			if(advance)
				++pos;
			else
				--pos;
		}
	}

	public static void backtrack2(final char[] board){
		final List<Character>[] possibilities = SudokuBacktracking.computePossibilities(board);

		//ordenar possibilidedades
		final SortedMap<List<Character>, Integer> sortedPossibilities = new TreeMap<List<Character>, Integer>(new PossibilityComparator());
		for(int i = 0; i < 81; ++i)
			sortedPossibilities.put(possibilities[i], i);
	}

	private static List<Character>[] computePossibilities(final char[] board) {

		@SuppressWarnings("unchecked")
		final
		List<Character>[] ret = new ArrayList[81];

		for(int i = 0; i < 81; ++i){
			ret[i] = SudokuBacktracking.computePossibilities(board,i);
		}
		return ret;
	}

	private static List<Character> computePossibilities(final char[] board, final int pos) {
		final ArrayList<Character> ret = new ArrayList<Character>();

		for(char k = 1; k <= 9; ++k)
			ret.add(k);

		final int col = pos % 9;

		//check column
		for(int i = col; i < 81; i+=9)
			if(i != pos)
				ret.remove(new Character(board[i]));

		//check line
		final int stt = pos - col;
		final int stp = stt + 9;
		for(int i = stt; i < stp; ++i)
			ret.remove(new Character(board[i]));


		//test block
		final int bCol = pos % 3;
		final int bStt = (pos % 27) - col; //a number equals 0, 9 or 18
		final int bLin = bStt / 9; //a number between 0 and 2

		int a = 0, b = 0;
		int alfa = 0, beta = 0;

		switch(bCol){
		case 0:
			a = 1;
			b = 2;
			break;
		case 1:
			a = -1;
			b = 1;
			break;
		case 2:
			a = -2;
			b = -1;
			break;
		}

		switch(bLin){
		case 0:
			alfa = 9;
			beta = 18;
			break;
		case 1:
			alfa = -9;
			beta = 9;
			break;
		case 2:
			alfa = -18;
			beta = -9;
			break;
		}

		ret.remove(new Character(board[pos + alfa + a]));
		ret.remove(new Character(board[pos + alfa + b]));
		ret.remove(new Character(board[pos + beta + a]));
		ret.remove(new Character(board[pos + beta + b]));

		return ret;
	}

	private static boolean test(final char[] board, final char k, final int pos) {

		final int col = pos % 9;

		//test column
		for(int i = col; i < 81; i+=9)
			if(i != pos)
				if(k == board[i])
					return false;

		//test line
		final int stt = pos - col;
		final int stp = stt + 9;
		for(int i = stt; i < stp; ++i)
			if(i != pos)
				if(k == board[i])
					return false;


		//test block

		final int bCol = pos % 3;
		final int bStt = (pos % 27) - col; //a number equals 0, 9 or 18
		final int bLin = bStt / 9; //a number between 0 and 2
		//final int hBlock = col / 3;
		//final int vBlock = stt / 27; //a number between 0 and 2;

		int a = 0, b = 0;
		int alfa = 0, beta = 0;

		switch(bCol){
		case 0:
			a = 1;
			b = 2;
			break;
		case 1:
			a = -1;
			b = 1;
			break;
		case 2:
			a = -2;
			b = -1;
			break;
		}

		switch(bLin){
		case 0:
			alfa = 9;
			beta = 18;
			break;
		case 1:
			alfa = -9;
			beta = 9;
			break;
		case 2:
			alfa = -18;
			beta = -9;
			break;
		}

		if(board[pos + alfa + a] == k) return false;
		if(board[pos + alfa + b] == k) return false;
		if(board[pos + beta + a] == k) return false;
		if(board[pos + beta + b] == k) return false;

		return true;
	}

	private static int getPos(final int col, final int lin) {
		return (lin * 9) + col;
	}

	private static void printBoards(final char[] inputBoard, final char[] outputBoard) {
		System.out.println("Input            \tOutput");
		for(int i = 0; i < 9; ++i){
			for(int j = 0; j < 9; ++j)
				System.out.print((char)('0' + inputBoard[SudokuBacktracking.getPos(j,i)]) + " ");

			System.out.print("\t");

			for(int j = 0; j < 9; ++j)
				System.out.print((char)('0' + outputBoard[SudokuBacktracking.getPos(j,i)]) + " ");

			System.out.println();
		}
	}

	private static void printNoOutput(final char[] inputBoard) {
		System.out.println("Input            \tOutput");
		for(int i = 0; i < 9; ++i){
			for(int j = 0; j < 9; ++j)
				System.out.print((char)('0' + inputBoard[SudokuBacktracking.getPos(j,i)]) + " ");

			System.out.print("\t");

			for(int j = 0; j < 9; ++j)
				System.out.print("X ");

			System.out.println();
		}
	}

	private static void readBoard(final char[] board, final String inputFile) throws IOException {
		final FileInputStream fis = new FileInputStream(inputFile);

		int read;
		int pos = 0;
		while((read = fis.read()) > 0 && pos < 81)
			if(read != '\r' && read != '\n')
				board[pos++] = (char)(read - '0');

	}
}
