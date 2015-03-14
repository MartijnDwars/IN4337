import java.util.Arrays;
import java.util.Random;

/**
 * This class is based on Main, but was adapted to indicate a trial in
 * which some nodes decide whereas others do not. (Exercise 12.21)
 */
public class Ex21 {
	public static void main(String[] args) throws InterruptedException {
		// NOTE: Do not use n = 8, t = 1. This may not terminate.
		int n = 16;
		int t = 1;

		Process[] processes = new Process[n];

		while (true) {
			// Create n adversaries that send random votes
			for (int i = 0; i < t; i++) {
				processes[i] = new Adversary(i, n);
			}

			// Create n process with *random* initial value
			for (int i = t; i < n; i++) {
				processes[i] = new Process(i, n, new Random().nextInt(2));
			}

			// Register
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					processes[i].setProcess(j, processes[j]);
				}
			}

			// Number of iterations before ALL processes are done
			int iterations = 0;

			while (true) {
				// Provide all processes with a global coin toss
				int coin = new Random().nextInt(2);

				System.out.println("Coin = " + coin);

				// Phase 1: broadcast vote
				for (Process p : processes) {
					p.broadcast();
				}

				// Phase 2: act upon votes
				for (Process p : processes) {
					p.coordinate(coin);
				}

				iterations++;

				if (anyDecided(processes) && !allDecided(processes)) {
					System.out.println("Dit is er een!");
				}

				// Terminate if all processes have decided
				if (Arrays.stream(processes).allMatch(Process::hasDecided)) {
					break;
				}
			}

			System.out.println("=== Finished after iterations: " + iterations);
		}
	}

	private static boolean anyDecided(Process[] processes) {
		return Arrays.stream(processes).anyMatch(p -> p.i > 0 && p.hasDecided());
	}

	private static boolean allDecided(Process[] processes) {
		return Arrays.stream(processes).allMatch(Process::hasDecided);
	}
}
