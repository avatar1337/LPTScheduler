package scheduler;

public class PrintTask extends Task {
	private static boolean shift;
	
	public static void shift() {
		shift = !shift;
	}
	
	public static void restore() {
		shift = false;
	}
	
	public PrintTask(long t) {
		super(t);
		shift=false;
	}
	
	@Override
	public void run() {
		for(int i = 0; i < getTime(); i++) {
			if(shift)
				System.out.print("░");
			else
				System.out.print("▓");
		}
	}
}
