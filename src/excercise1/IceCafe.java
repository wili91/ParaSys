package excercise1;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class IceCafe {

	// Define Locks
	private final ReentrantLock lock = new ReentrantLock();
	private final Condition condition = lock.newCondition();

	// Default Counter for the number of free Employees
	private int employeeFreeCtr = 3;

	private int minServingTime = 3;
	private int maxServingTime = 7;

	public IceCafe(int currentEmployes, int minServingTime, int maxServingTime) {
		this.employeeFreeCtr = currentEmployes;
		if (maxServingTime >= minServingTime) {
			this.minServingTime = minServingTime;
			this.maxServingTime = maxServingTime;
		} else {
			System.out.println("The max ServingTime has to be less than the min Serving Time");
		}
	}

	/**
	 * Make sure only three employees may be served at a time.
	 * 
	 * @param customer
	 */
	public void isEntering(Customer customer) {
		System.out.println("Customer " + customer.getName() + " is entering the ice cafe. ");

		lock.lock();
		try {
			while (employeeFreeCtr == 0) {
				try {
					System.out.println("Customer " + customer.getName() + " Is waiting to be served!");
					condition.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			employeeFreeCtr--;
		} finally {
			lock.unlock();
		}

	}

	public void isBeeingServed(Customer customer) {

		Random random = new Random();
		// time it takes to serve the customer
		int waitingTime = random.nextInt(maxServingTime - minServingTime + 1) + minServingTime;

		customer.waitingForIceCream(waitingTime);
		System.out.println("Customer " + customer.getName() + " receives serving!");
	}

	public void isLeaving(Customer customer) {
		lock.lock();

		try {
			System.out.println("Customer " + customer.getName() + " is leaving!");
			this.employeeFreeCtr++;
			condition.signal();
		} finally {
			lock.unlock();
		}
	}

}
