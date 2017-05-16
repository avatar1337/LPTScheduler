package LPT;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import scheduler.PrintTask;
import scheduler.Printer;

public class PrintSchedule {
	
	public static void main(String[] args) {
		Random rnd = new Random();
		LPT_Scheduler<Printer> printSchedule = new LPT_Scheduler<>();
		
		List<PrintTask> jobs = new LinkedList<>();
		for(int i=0; i < 100; i++)
			jobs.add(new PrintTask(rnd.nextInt(30)+1)); 
		
		List<Printer> machines = new LinkedList<>();
		for(int i = 0; i < 16; i++) machines.add(new Printer());
		printSchedule.schedule(machines, jobs);
		for(Printer p : machines) p.run(); //new Thread(p).start();
	}
}
