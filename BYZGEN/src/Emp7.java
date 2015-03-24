public class Emp7 {
	public static void main(String[] args) throws InterruptedException {
		// Number of iterations for t = 4, n = (4+1)*8 = 40
		System.out.println("n,t,iterations");
		for (int t = 4, n = 40, i = 0; i < 1000; i++) {
			System.out.println(n + "," + t + "," + Main.run(n, t));
		}
	}
}
