public class Main {
	public static void main(String[] args) throws InterruptedException {
		System.out.println(run());
	}

	public static int run() {
		Register[] registers = new Register[] {
				new Register(0),
				new Register(0)
		};

		Process p0 = new Process(0, registers);
		Process p1 = new Process(1, registers);

		// Finished flags keep track of which process is finished (0 = finished)
		int f0 = -1;
		int f1 = -1;

		int iterations = 0;
		// While some process is not finished
		while (f0 != 0 || f1 != 0) {
			if (f0 != 0) {
				f0 = p0.coordinate();
			}

			if (f1 != 0) {
				f1 = p1.coordinate();
			}

			iterations++;
			//Thread.sleep(1000);
		}

		return iterations;
	}
}
