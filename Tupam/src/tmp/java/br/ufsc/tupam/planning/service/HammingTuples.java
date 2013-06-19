package br.ufsc.tupam.planning.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HammingTuples {

	private static int backtrackNodes;

	public static void main(final String[] args) {

		for(int n = 4; n<= 11; ++n){
			float estimative = 0;
			int p;
			for(p = 10; p <= 100; ++p){
				estimative = HammingTuples.estimate(n, 4, p);
				System.out.println("n: " + n + "; amostras: " + p + "; estimative: " + estimative);
			}
			//estimative /= p;
			//estimative = Math.round(estimative);
		}

		for(int n = 4; n <= 28; ++n){
			final long startTime = System.currentTimeMillis();
			HammingTuples.backtrackNodes = 0;
			final List<Integer> maxTuple = HammingTuples.a(n,4);
			final float stopTime = (System.currentTimeMillis() - startTime) / 1000f;
			System.out.println("n: " + n + "; d: " + 4);
			System.out.println("result: " + maxTuple.size());
			System.out.print("(");
			for(int i = 0; i < maxTuple.size(); ++i)
				System.out.print(maxTuple.get(i) + ",");
			System.out.println(")");
			System.out.println("backtrack nodes: " + HammingTuples.backtrackNodes);
			System.out.println("time: " + stopTime);
			System.out.println();
		}
	}


	private static List<Integer> a(final int n, final int d) {
		/*
		 * Initialize
		 */
		final int range = (int)Math.pow(2	, n);
		final List<Integer> tuple = new ArrayList<Integer>();
		List<Integer> maxTuple = new ArrayList<Integer>();
		tuple.add(0);

		int i = 0;
		int k = 0;
		while(i >= 0){

			while(k < range){
				/*
				 * compute sucessors
				 */
				++k;

				int j = tuple.size() - 1;

				for(; j >=0; --j)
					if(HammingTuples.dist(n, k, tuple.get(j)) < d)
						break;


				if(j == -1){ 	//have all successors tried?
					/*
					 * advance
					 */
					++HammingTuples.backtrackNodes;
					tuple.add(k);
					++i;

					if(tuple.size() > maxTuple.size())
						maxTuple = new ArrayList<Integer>(tuple);
					break;
				}
			}

			if(k >= range){ //have all successor tried and Sk is empty
				/*
				 * backtrack
				 */
				k = tuple.remove(i);
				--i;

				if(i == 0)
					k = tuple.set(0,tuple.get(0) + 1);
			}
		}

		return maxTuple;
	}

	private static int estimate(final int n, final int d, final int p) {
		final int range = (int)Math.pow(2	, n);
		final Random random = new Random();
		final List<Integer> sampleSize = new ArrayList<Integer>();
		final List<Float> sampleProb = new ArrayList<Float>();
		for(int k = 0; k < p; ++k){

			final List<Integer> maxTuple = new ArrayList<Integer>();
			maxTuple.add(random.nextInt(range));
			final List<Integer> childrenByLevel = new ArrayList<Integer>();
			childrenByLevel.add(1);

			int i = maxTuple.get(0);
			while(i < range){

				//register all possible children of maxTuple.get(i)
				final List<Integer> children = new ArrayList<Integer>();
				while(i < range){
					for(int j = 0; j < maxTuple.size(); ++j)
						if(HammingTuples.dist(n, i, maxTuple.get(j)) >= d)
							children.add(i);
					++i;
				}

				final int s = children.size();

				if(s == 0)
					break;

				final int r = random.nextInt(s);
				maxTuple.add(children.get(r));
				childrenByLevel.add(children.size());

				i = children.get(r) + 1;
			}

			final int cs = childrenByLevel.size();
			//update children levels
			for(int t = 1; t < cs; ++t)
				childrenByLevel.set(t, childrenByLevel.get(t-1) * childrenByLevel.get(t));

			//register sample
			int N = 0;
			for(int t = 0; t < cs; ++t)
				N += childrenByLevel.get(t);

			final float P = 1f / childrenByLevel.get(cs - 1);

			sampleSize.add(N);
			sampleProb.add(P);
		}

		float ret = 0;
		for(int t = 0; t < p; ++t)
			ret += sampleSize.get(t);

		ret /= p;

		return Math.round(ret);
	}


	private static int dist(final int n, final int a, final int b) {
		int t = a ^ b;

		int ret = 0;
		for(int i = 0; i < n; ++i){
			if((t & 0x00000001) != 0)
				++ret;
			t >>= 1;
		}

		return ret;
	}
}
