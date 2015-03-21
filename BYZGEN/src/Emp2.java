public class Emp2 {
	public static void main(String[] args) throws InterruptedException {
		// Number of iterations for fixed n, increasing t (but still t < n/8)
		//
		// n = 20*8 = 240
		// t = 1,...,29
		System.out.println("n,t,iterations");
		for (int n = 240, t = 1; t < 30; t++) {
			System.out.println(n + "," + t + "," + Main.run(240, t));
		}
	}
}
