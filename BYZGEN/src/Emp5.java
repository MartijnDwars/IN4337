public class Emp5 {
	public static void main(String[] args) throws InterruptedException {
		// Number of iterations for constant ratio t = n/8-1
		//
		// (t,n) = (4,1), (8, 2), ..., (99*4, 99)
		System.out.println("n,t,iterations");
		for (int t = 1; t < 50; t++) {
			int n = (t+1)*8;
			System.out.println(n + "," + t + "," + Main.run(n, t));
		}
	}
}
