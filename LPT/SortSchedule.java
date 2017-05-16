package LPT;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import scheduler.Processor;
import scheduler.Scheduler;
import scheduler.Task;

public class SortSchedule {
	
	private static abstract class SortTask<E extends Comparable<? super E>> extends Task {
		protected LinkedList<E> data;
		
		public LinkedList<E> getData() {
			return data;
		}
		
		public SortTask(LinkedList<E> list) {
			super(list.size());
			data = list;
		}
	}
	
	private static class Sorter<E extends Comparable<? super E>> extends Processor<SortTask<E>> {}
	
	private static class BubbleSort<E extends Comparable<? super E>> extends SortTask<E> {
		
		public BubbleSort(LinkedList<E> list) {
			super(list);
		}

		@Override
		public void run() {
			ArrayList<E> array = new ArrayList<>(data);
			int length = array.size();
			data.clear();
			while(length > 0) {
				for(int i=0; i < length-1; i++) {
					E first = array.get(i);
					E second = array.get(i+1);
					if(first.compareTo(second) > 0) {
						array.set(i, second); 
						array.set(i+1, first);
					}
				}
				data.addFirst(array.get(--length));
			}	
		}		

	}
	
	public static void main(String[] args) {
		Scheduler<Sorter<Integer>> sortSchedule = new LPT_Scheduler<>();
		
		Random rnd = new Random(42);
		PrintWriter pw;
		System.out.println("Starting...");
		try {
			pw = new PrintWriter(args[0],"UTF-8");

			pw.println("Nr,Tid");
			for(int k=0; k < Integer.parseInt(args[1]); k++) {
				//System.out.println("Creating tasks...");
				
				List<SortTask<Integer>> tasks = new LinkedList<>();
				final int N = Integer.parseInt(args[2]);
				
				for(int i=0; i < N; i++) {
					LinkedList<Integer> toSort = new LinkedList<>();
					for(int j = 0; j < rnd.nextInt(10000)+100; j++) toSort.add(rnd.nextInt(100000));
					BubbleSort<Integer> bsTask = new BubbleSort<Integer>(toSort);
					//bsTask.meassureTask();
					tasks.add(bsTask);
				}
				
				List<Sorter<Integer>> sorters = new LinkedList<>();
				
				final int M = Integer.parseInt(args[3]);
				//System.out.println("Distributing "+N+" lists over "+M+" sorters...");
				
				for(int i=0; i < M; i++) sorters.add(new Sorter<Integer>());
				sortSchedule.schedule(sorters, tasks);
				
				//System.out.println("Sorting starts...");
				long start = System.nanoTime();
				for(Sorter<Integer> s : sorters) s.start();
				Sorter.waitForAll();
				long stop = System.nanoTime() - start;		
		
				//System.out.println("Done! It took "+ Math.round(stop/1000000.0d) + " milliseconds!");
				pw.println((k+1)+","+stop);
				//if(k%10 == 0) System.out.println(k);
			}
			
			pw.close();
			System.out.println("Done!");
		} catch (FileNotFoundException | UnsupportedEncodingException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*System.out.println("Here are the first 10 elements of the first list");
		int counter = 0;
		for(Integer i : tasks.get(0).getData()) {
			System.out.println(i);
			if(++counter >= 10) break;
		}
		System.out.println("⋮");*/
	}
}
