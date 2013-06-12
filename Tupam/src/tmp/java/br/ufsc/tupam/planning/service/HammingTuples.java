package br.ufsc.tupam.planning.service;

public class HammingTuples {

	private static int backtrackNodes;

	public static void main(final String[] args) {
		for(int n = 4; n <= 8; ++n){
			final long startTime = System.currentTimeMillis();
			HammingTuples.backtrackNodes = 0;
			final int nTuples = HammingTuples.a(n,4);
			final float stopTime = (float)startTime/System.currentTimeMillis();
			System.out.println("n: " + n + "; d: " + 4);
			System.out.println("result: " + nTuples);
			System.out.println("backtrack nodes: " + HammingTuples.backtrackNodes);
			System.out.println("time: " + stopTime);
			System.out.println();
		}
	}


	private static int a(final int n, final int d) {
		final double range = Math.pow(2,n);
		final int[] tuple = new int[n];
		int ret = 0;

		int k = 0;
		int i = 0;
		while(!(k == 0 && i >= range)){

			tuple[k] = i;
			int j = 0;
			for(; j < k; ++j)
				if(HammingTuples.dist(tuple[j],tuple[k]) < d){
					++i;
					break;
				}

			if(j == k){
				++k; //advance
				++HammingTuples.backtrackNodes;
			}

			if(k == 3 || i >= range){ //backtrack
				if(k == 3)
					++ret;

				tuple[k] = 0;
				--k;
				i = tuple[k] + 1;
			}

		}

		return ret;
	}

	private static int dist(final int a, final int b) {
		int t = a ^ b;

		int ret = 0;
		for(int i = 0; i < Integer.SIZE; ++i){
			if((t & 1) == 1)
				++ret;
			t >>= 1;
		}

		return ret;
	}
}
