package scheduler;
import java.util.List;
import java.util.PriorityQueue;

public interface Scheduler<E extends Processor<? extends Task>> {
	public void schedule(List<E> procs, List<? extends Task> tasks);
}
