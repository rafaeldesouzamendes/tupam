package br.ufsc.tupam.planning.service;

import java.io.IOException;

public class SudokuSimulatedAnnealing {

	private static int backtrackingNodes;

	public static void main(final String[] args) throws IOException {

		for(int i = 1; i <= 11; ++i){
			final char[] inputBoard = new char[81];

			final String fileName = "H"+i;
			SudokuSimulatedAnnealing.readBoard(inputBoard, "/home/mendes/Documents/doc/disciplinas/AlgoritmosCombinatorios/a2data/a2data/" + fileName + ".txt");

			long time = 0;
			SudokuSimulatedAnnealing.backtrackingNodes = 0;
			final long startTime = System.currentTimeMillis();
			System.out.println(fileName);

			char[] outputBoard;
			outputBoard = SudokuSimulatedAnnealing.backtrack2(inputBoard);
			time = System.currentTimeMillis() - startTime;

			if(outputBoard != null)
				SudokuSimulatedAnnealing.printBoards(inputBoard,outputBoard);
			else
				SudokuSimulatedAnnealing.printNoOutput(inputBoard);

			time = System.currentTimeMillis() - startTime;

			System.out.println();
			System.out.println("Backtrack nodes:" +  SudokuSimulatedAnnealing.backtrackingNodes);
			System.out.println("Time:" +  (float)time/1000 + "s");
			System.out.println("---------------------");
			System.out.println();
		}
	}
}