public class Emp3 {
	public static void main(String[] args) throws InterruptedException {
		// Number of iterations for constant ratio n/t
		//
		// (t,n) = (1*8,1), (2*8, 2), ..., (100*8, 99)
		System.out.println("n,t,iterations");
		for (int t = 1; t < 100; t++) {
			int n = (t)*4;
			System.out.println(n + "," + t + "," + Main.run(n, t));
		}
	}
}
