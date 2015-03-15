import java.util.Arrays;
import java.util.Random;

/**
 * This class is based on Main. However, whereas Main probes the processes to
 * check if they're done, this code loops until all processors indicate that
 * they're done themselves.
 */
public class Ex20 {
	public static void main(String[] args) throws InterruptedException {
		// NOTE: Do not use n = 8, t = 1. This may not terminate.
		int n = 16;
		int t = 1;

		Actor[] processes = new Actor[n];

		// Create n adversaries that send random votes
		for (int i = 0; i < t; i++) {
			processes[i] = new Adversary(i, n);
		}

		// Create n process with *random* initial value
		for (int i = t; i < n; i++) {
			processes[i] = new HaltingProcess(i, n, new Random().nextInt(2));
		}

		// Register
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				processes[i].setProcess(j, processes[j]);
			}
		}

		// Number of iterations before ALL processes are done
		int iterations = 0;

		while (!allHalted(processes)) {
			// Provide all processes with a global coin toss
			int coin = new Random().nextInt(2);

			System.err.println("Coin = " + coin);

			// Phase 1: broadcast vote
			for (Actor p : processes) {
				p.broadcast();
			}

			// Phase 2: act upon votes
			for (Actor p : processes) {
				p.coordinate(coin);
			}

			iterations++;
		}

		System.err.println("Iterations = " + iterations);
	}

	protected static boolean allHalted(Actor[] processes) {
		return Arrays.stream(processes).allMatch(Actor::isHalted);
	}
}
