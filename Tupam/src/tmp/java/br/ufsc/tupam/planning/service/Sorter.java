package br.ufsc.tupam.planning.service;

import java.util.List;

public class Sorter{

	final private Object[][] aux = new Object[2][81];


	public void bubbleSort(final Object[][] values) {
		boolean changed = true;
		while (changed) {
			changed = false;
			for(int i = 1; i < values[0].length; ++i)
				if(((List)values[0][i]).size() < ((List)values[0][i-1]).size()){
					final Object t0 = values[0][i];
					final Object t1 = values[1][i];

					values[0][i] = values[0][i-1];
					values[1][i] = values[1][i-1];

					values[0][i-1] = t0;
					values[1][i-1] = t1;

					changed = true;
				}
		}
	}

	public void sort(final Object[][] values) {
		this.mergesort(values, 0, 80);
	}

	private void mergesort(final Object[][] values, final int low, final int high) {
		// Check if low is smaller then high, if not then the array is sorted
		if (low < high) {
			// Get the index of the element which is in the middle
			final int middle = low + (high - low) / 2;
			// Sort the left side of the array
			this.mergesort(values, low, middle);
			// Sort the right side of the array
			this.mergesort(values, middle + 1, high);
			// Combine them both
			this.merge(values, low, middle, high);
		}
	}

	private void merge(final Object[][] values, final int low, final int middle, final int high) {

		// Copy both parts into the helper array
		for (int i = low; i <= high; i++) {
			this.aux[0][i] = values[0][i];
			this.aux[1][i] = values[1][i];
		}

		int i = low;
		int j = middle + 1;
		int k = low;
		// Copy the smallest values from either the left or the right side back
		// to the original array
		while (i <= middle && j <= high) {
			if (((List)values[0][i]).size() <= ((List)this.aux[0][j]).size()) {
				values[0][k] = this.aux[0][i];
				values[1][k] = this.aux[1][i];
				i++;
			} else {
				values[0][k] = this.aux[0][j];
				values[1][k] = this.aux[1][j];
				j++;
			}
			k++;
		}
		// Copy the rest of the left side of the array into the target array
		while (i <= middle) {
			values[0][k] = this.aux[0][i];
			values[1][k] = this.aux[1][i];
			k++;
			i++;
		}

	}
}