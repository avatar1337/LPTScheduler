package scheduler;

public class Printer extends Processor<PrintTask> {

	@Override
	public void run() {
		System.out.print(this);
		PrintTask.restore();
		for(PrintTask t : getTasks()) {
			t.run();
			PrintTask.shift();
		}
		System.out.println();
	}
}
