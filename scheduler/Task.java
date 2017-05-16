package scheduler;

public abstract class Task implements Runnable, Comparable<Task> {
	private long time;
	
	public Task() {
		this.time = 0;
	}
	
	public Task(long time) {
		this.time = time;
	}
	
	public void setTime(long time) {
		this.time = time;
	}
	
	public long getTime() {
		return time;
	}
	
	public void meassureTask() {
		long start = System.nanoTime();
		run();
		time = System.nanoTime() - start;
	}
	
	@Override
	public String toString() {
		return ""+time;
	}
	
	@Override
	public int compareTo(Task t) {
		return Long.compare(t.time, time);
	}
}
