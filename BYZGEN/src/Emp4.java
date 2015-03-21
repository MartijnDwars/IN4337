public class Emp4 {
	public static void main(String[] args) throws InterruptedException {
		// How likely is it that the algorithm succeeds
		//
		// (n = 400, t = 100, 120, ..., 380, 400, each time 10 trials)
		System.out.println("n,t,failing");
		for (int n = 80, t = 10, trials = 20; t < 60; t++) {
			int failing = 0;
			for (int i = 0; i < trials; i++) {
				if (Main.run(n, t) == -1) {
					failing++;
				}
			}
			System.out.println(n + "," + t + "," + failing);
		}
	}
}
