package scheduler;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class Processor<E extends Task> implements Runnable, Comparable<Processor<E>> {
	private static int ID = 0;
	private long processingTime = 0;
	private List<E> tasks;	
	private int id;
	private static LinkedList<Thread> processes = new LinkedList<>();
	
	public Processor() {
		tasks = new ArrayList<E>();
		id=ID++;
	}
	
	public void clearProcTime() {
		processingTime = 0;
	}
	
	public long getProcTime() {
		return processingTime;
	}
	
	public int getID() {
		return id;
	}
	
	public void addTask(Task task) {
		E t = (E)task;
		processingTime += t.getTime();
		tasks.add(t);
	}
	
	public List<E> getTasks() {
		return tasks;
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("Processor ID: ");
		str.append(id);
		int subtract = (id < 1)? 0 : (int)Math.log10(id);
		for(int i = 0; i < 6-subtract; i++) str.append(" ");
		str.append(" processing time: ");
		str.append(processingTime);
		str.append(" \t");
		return str.toString();
	}
	
	@Override
	public int compareTo(Processor<E> p) {
		return Long.compare(processingTime, p.getProcTime());
	}
	
	@Override
	public void run() {
		for(Task t : tasks) t.run();
	}
	
	synchronized public void start() {
		Thread p = new Thread(this);
		p.start();
		processes.add(p);
	}
	
	public static void waitForAll() throws InterruptedException {
		for(Thread t : processes)
			t.join();
	}
}
