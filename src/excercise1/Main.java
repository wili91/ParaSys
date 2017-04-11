package excercise1;

import java.util.Random;
//import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {

	
	private static IceCafe myIceCafe;
	private static int CustomerCtr = 0;

	// this is the new thread pool
	private static ExecutorService pool = Executors.newCachedThreadPool();
	private static ExecutorService pool2 = Executors.newCachedThreadPool();
	
	public static void main(String[] args) {

		myIceCafe = new IceCafe(3, 3, 7);
		
		System.out.println(
				"################################ Welcome to Ice Cafe Evil!####################################################");
		System.out.println("--------------------------Start morning shift at 8 AM! ---->Lasting 2 hours");
		// 10 groups every 7 minutes with 1 up to 3 group members
		shiftGenerator(10, 7, 1, 3, false);

		System.out.println("--------------------------Start noon shift! ---->Lasting 2 hours");
		// 10 groups every 3 minutes with 3 up to 5 group members
		shiftGenerator(10, 3, 3, 5, false);

		System.out.println("--------------------------Start evening shift! ---->Lasting 2 hours");
		// 10 groups every 10 minutes with 3 up to 6 group members
		shiftGenerator(10, 10, 3, 6, false);
		System.out.println(
				"################################Bye Suckers!####################################################");

		// stop all other Threads by force!
		pool2.shutdownNow();
		
		//Block for some time
		try {
			pool2.awaitTermination(5, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

		System.out.println(
				"################################ Welcome to Ice Cafe Nice!####################################################");

		System.out.println("--------------------------Start morning shift at 8 AM! ---->Lasting 2 hours");
		// 10 groups every 7 minutes with 1 up to 3 group members
		shiftGenerator(10, 7, 1, 3, true);

		System.out.println("--------------------------Start noon shift! ---->Lasting 2 hours");
		// 10 groups every 3 minutes with 3 up to 5 group members
		shiftGenerator(10, 3, 3, 5, true);

		System.out.println("--------------------------Start evening shift! ---->Lasting 2 hours");
		// 10 groups every 10 minutes with 3 up to 6 group members
		shiftGenerator(10, 10, 3, 6, true);
		System.out.println(
				"################################Work some more hours to serve everyone!####################################################");

		// stop all other Threads nicely
		pool.shutdown();

	}

	private static void shiftGenerator(int numberOfGroups, int groupIntervallMinutes, int groupMinSize,
			int groupMaxSize, boolean nice) {

		Random rand = new Random();
		int timeInMinutes = 0;
		while (timeInMinutes <= 30) {
			// in every group intervall generate numberOfGroups groups
			if ((timeInMinutes % groupIntervallMinutes) == 0) {

				for (int i = 0; i < numberOfGroups; i++) {

					// genrate groupSize and then generate so many customers

					int groupSize = rand.nextInt(groupMaxSize - groupMinSize + 1) + groupMinSize;

					for (int j = 0; j < groupSize; j++) {

						if (nice) {
							pool.execute(new Customer(CustomerCtr + " Id ", myIceCafe));
						} else {
							pool2.execute(new Customer(CustomerCtr + " Id ", myIceCafe));
						}
						CustomerCtr++;
					}
				}
			}

			// wait another Minute
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// make sure Minute Counter is correct
			timeInMinutes++;
		}

	}

}