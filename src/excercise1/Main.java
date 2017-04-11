package excercise1;

import java.util.Random;

public class Main {

	private static IceCafe myIceCafe;
	private static int CustomerCtr = 0;

	public static void main(String[] args) {

		myIceCafe = new IceCafe(3, 3, 7);

		System.out.println("--------------------------Start morning shift at 8 AM! ---->Lasting 2 hours");
		// 10 groups every 7 minutes with 1 up to 3 group members 
		shiftGenerator(10, 7, 1 , 3); 
		
		System.out.println("--------------------------Start noon shift! ---->Lasting 2 hours");
		// 10 groups every 3 minutes with 3 up to 5 group members 
		shiftGenerator(10, 3, 3 , 5); 
		
		System.out.println("--------------------------Start evening shift! ---->Lasting 2 hours");
		// 10 groups every 10 minutes with 3 up to 6 group members 
		shiftGenerator(10, 10, 3 , 6); 
		System.out.println("################################Work some more hours to serve everyone!####################################################");

	}

	private static void shiftGenerator(int numberOfGroups, int groupIntervallMinutes, int groupMinSize,
			int groupMaxSize) {
		
		Random rand = new Random();
		int timeInMinutes = 0;
		while (timeInMinutes <= 120) {
			//in every group intervall generate  numberOfGroups groups
			if ((timeInMinutes % groupIntervallMinutes) == 0) {
				
				for (int i = 0; i < numberOfGroups; i++) {

					//genrate groupSize  and then generate so many customers
					
					
					int groupSize = rand.nextInt(groupMaxSize - groupMinSize + 1) + groupMinSize;
					

					for (int j = 0; j < groupSize; j++) {
						Thread customer = new Thread(new Customer(CustomerCtr + " Id ", myIceCafe));
						customer.start();
						CustomerCtr++;
					}
				}
			}
			
			//wait another Minute
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			//make sure Minute Counter is correct
			timeInMinutes++;
		}
	}

}