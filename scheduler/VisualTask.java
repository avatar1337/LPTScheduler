package scheduler;

public class VisualTask extends Task {
	
	public VisualTask(long t) {
		super(t);
	}
	
	@Override
	public void run() {
		for(int i = 0; i < getTime(); i++) {
			System.out.print("O");
		}
	}

}
