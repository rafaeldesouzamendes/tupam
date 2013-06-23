package br.ufsc.tupam.planning.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SudokuBacktracking2 {

	private static int backtrackingNodes;

	public static void main(final String[] args) throws IOException {

		for(int i = 1; i <= 11; ++i){
			final char[] inputBoard = new char[81];

			final String fileName = "H"+i;
			SudokuBacktracking2.readBoard(inputBoard, "/home/mendes/Documents/doc/disciplinas/AlgoritmosCombinatorios/a2data/a2data/" + fileName + ".txt");

			long time = 0;
			SudokuBacktracking2.backtrackingNodes = 0;
			final long startTime = System.currentTimeMillis();
			System.out.println(fileName);

			char[] outputBoard;
			outputBoard = SudokuBacktracking2.backtrack2(inputBoard);
			time = System.currentTimeMillis() - startTime;

			if(outputBoard != null)
				SudokuBacktracking2.printBoards(inputBoard,outputBoard);
			else
				SudokuBacktracking2.printNoOutput(inputBoard);

			time = System.currentTimeMillis() - startTime;

			System.out.println();
			System.out.println("Backtrack nodes:" +  SudokuBacktracking2.backtrackingNodes);
			System.out.println("Time:" +  (float)time/1000 + "s");
			System.out.println("---------------------");
			System.out.println();
		}
	}

	public static char[] backtrack2(final char[] board){
		final List<Character>[] possibilities = SudokuBacktracking2.computePossibilities(board);
		final Object[][] sortedPossibilities = new Object[2][81];

		//sort possibilities
		for(int i = 0; i < 81; ++i){
			sortedPossibilities[0][i] = possibilities[i];
			sortedPossibilities[1][i] = i;
		}


		final Sorter sorter = new Sorter();
		sorter.bubbleSort(sortedPossibilities);

		int i = 0;
		while(((List<Character>)sortedPossibilities[0][i]).size() <= 0)
			++i;

		return SudokuBacktracking2.backtrack2(board, i, sortedPossibilities);

	}

	private static char[] backtrack2(final char[] board, final int pos, final Object[][] possibilities){
		++SudokuBacktracking2.backtrackingNodes;

		if(pos == 81)
			return board;

		final int index = (Integer)possibilities[1][pos];

		if(board[index] == 0){
			if(((List)possibilities[0][pos]).isEmpty())
				return null;
		}else
			return SudokuBacktracking2.backtrack2(board, pos + 1, possibilities);


		//copy board and possibilities
		final char[] newBoard = new char[81];
		System.arraycopy(board, 0, newBoard, 0, 81);
		final Object[][] sPoss = SudokuBacktracking2.copy(possibilities);


		while(!((List<Character>)sPoss[0][pos]).isEmpty()){
			final char value = ((List<Character>)sPoss[0][pos]).remove(0);

			newBoard[index] = value;
			final Object[][] uPoss = SudokuBacktracking2.updatePossitilities(sPoss, index, value);
			((List<Character>)uPoss[0][pos]).clear();

			final char[] ret = SudokuBacktracking2.backtrack2(newBoard, pos + 1, uPoss);

			if(ret != null)
				return ret;
		}

		return null;
	}

	private static Object[][] copy(final Object[][] possibilities) {
		final Object[][] ret = new Object[2][81];
		System.arraycopy(possibilities[1], 0, ret[1], 0, 81);
		for(int j = 0; j < 81; ++j)
			ret[0][j] = new ArrayList<Character>((List<Character>)possibilities[0][j]);

		return ret;
	}

	private static Object[][] updatePossitilities(final Object[][] possibilities, final int pos, final char value) {
		final Object[][] ret = SudokuBacktracking2.copy(possibilities);

		final Set<Integer> positions = new HashSet<Integer>();

		final int col = pos % 9;

		//check column
		for(int i = col; i < 81; i+=9)
			positions.add(i);

		//check line
		final int stt = pos - col;
		final int stp = stt + 9;
		for(int i = stt; i < stp; ++i)
			positions.add(i);


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

		positions.add(pos + alfa + a);
		positions.add(pos + alfa + b);
		positions.add(pos + beta + a);
		positions.add(pos + beta + b);

		for(int i = 0; i < possibilities[1].length; ++i)
			if(positions.contains(possibilities[1][i])
					&& ((List)ret[0][i]).contains(value))
				((List)ret[0][i]).remove((Object)value);

		return ret;
	}

	private static List<Character>[] computePossibilities(final char[] board) {

		@SuppressWarnings("unchecked")
		final
		List<Character>[] ret = new ArrayList[81];

		for(int i = 0; i < 81; ++i){
			ret[i] = SudokuBacktracking2.computePossibilities(board,i);
		}
		return ret;
	}

	private static List<Character> computePossibilities(final char[] board, final int pos) {
		final ArrayList<Character> ret = new ArrayList<Character>();

		if(board[pos] != 0)
			return ret;

		for(char k = 1; k <= 9; ++k)
			ret.add(k);

		final int col = pos % 9;

		//check column
		for(int i = col; i < 81; i+=9)
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

	private static int getPos(final int col, final int lin) {
		return (lin * 9) + col;
	}

	private static void printBoards(final char[] inputBoard, final char[] outputBoard) {
		System.out.println("Input            \tOutput");
		for(int i = 0; i < 9; ++i){
			for(int j = 0; j < 9; ++j)
				System.out.print((char)('0' + inputBoard[SudokuBacktracking2.getPos(j,i)]) + " ");

			System.out.print("\t");

			for(int j = 0; j < 9; ++j)
				System.out.print((char)('0' + outputBoard[SudokuBacktracking2.getPos(j,i)]) + " ");

			System.out.println();
		}
	}

	private static void printNoOutput(final char[] inputBoard) {
		System.out.println("Input            \tOutput");
		for(int i = 0; i < 9; ++i){
			for(int j = 0; j < 9; ++j)
				System.out.print((char)('0' + inputBoard[SudokuBacktracking2.getPos(j,i)]) + " ");

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