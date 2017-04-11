package excercise1;

public class Customer implements Runnable {

	private String cus_name;
	private IceCafe cafe;

	public Customer(String name, IceCafe cafe) {
		this.cus_name = name;
		this.cafe = cafe;
	}

	@Override
	public void run() {
		cafe.isEntering(this);
		cafe.isBeeingServed(this);
		cafe.isLeaving(this);
	}

	public void waitingForIceCream(int waitingTime) {
		System.out.println("Customer " + this.cus_name + " is waiting " + waitingTime + " minutes to be served!");
		try {
			Thread.sleep(waitingTime * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public String getName() {
		return this.cus_name;
	}

}
