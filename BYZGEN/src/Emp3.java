public class Emp3 {
	public static void main(String[] args) throws InterruptedException {
		// Number of iterations for constant ratio t = n/4
		//
		// (t,n) = (4,1), (8, 2), ..., (99*4, 99)
		System.out.println("n,t,iterations");
		for (int t = 1; t < 100; t++) {
			int n = (t)*4;
			System.out.println(n + "," + t + "," + Main.run(n, t));
		}
	}
}
