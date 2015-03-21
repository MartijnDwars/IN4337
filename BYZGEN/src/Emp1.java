public class Emp1 {
	public static void main(String[] args) throws InterruptedException {
		// Number of iterations for increasing n. Expectation: should be constant.
		//
		// n = 1*8,2*8,...,100*8
		// t = 0
		System.out.println("n,t,iterations");
		for (int n = 1; n <= 100; n++) {
			System.out.println(n + ",0," + Main.run(n, 0));
		}
	}
}
