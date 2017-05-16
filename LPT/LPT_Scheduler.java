package LPT;

import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

import scheduler.Processor;
import scheduler.Scheduler;
import scheduler.Task;

public class LPT_Scheduler<E extends Processor<? extends Task>> implements Scheduler<E> {

	@Override
	public void schedule(List<E> procs, List<? extends Task> tasks) {
		PriorityQueue<E> pq = new PriorityQueue<>();
		pq.addAll(procs);
		Collections.sort(tasks);
		for(Task task : tasks) {
			E p = pq.poll();
			p.addTask(task);
			pq.offer(p);
		}
	}
}
