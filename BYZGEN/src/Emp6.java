public class Emp6 {
	public static void main(String[] args) throws InterruptedException {
		// Number of iterations for t = 4, n = 40, ..., 80
		//
		System.out.println("n,t,iterations");
		for (int t = 4, n = 40; n < 80; n++) {
			System.out.println(n + "," + t + "," + Main.run(n, t));
		}
	}
}
