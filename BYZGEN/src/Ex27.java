import java.util.Arrays;
import java.util.Random;

/**
 * This is an example with processes that use FoilProcesses. A FoilProcess uses
 * different values for L, H, G. As this example shows, t = 1, n = 8 is doable.
 * The analysis shows that the expected number of rounds is still constant.
 */
public class Ex27 {
	public static void main(String[] args) {
		int n = 8;
		int t = 2;

		while (true) {
			Actor[] processes = new Actor[n];

			// Initial
			int[] initial = new int[n];

			for (int i = 0; i < n; i++) {
				initial[i] = new Random().nextInt(2);
			}

			// Create n adversaries that send random votes
			for (int i = 0; i < t; i++) {
				processes[i] = new Adversary(i, n);
			}

			// Create n process with *random* initial value
			for (int i = t; i < n; i++) {
				processes[i] = new FoilProcess(i, n, initial[i]);
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
				for (Actor a : processes) {
					a.broadcast();
				}

				// Phase 2: act upon votes
				for (Actor a : processes) {
					a.coordinate(coin);
				}

				iterations++;

				// Terminate if all processes have decided
				if (Arrays.stream(processes).allMatch(Actor::hasDecided)) {
					break;
				}

				if (iterations > 50) {
					System.out.println("It's going wrong..");
					System.exit(-1);
				}
			}

			// Sanity checking
			Object[] foilProcesses = Arrays.stream(processes).filter(p -> p instanceof FoilProcess).toArray();

			if (Arrays.stream(initial).allMatch(x -> x == initial[0])) {
				if (!Arrays.stream(foilProcesses).allMatch(x -> ((FoilProcess) x).getDecision() == initial[0])) {
					System.out.println("It's still going wrong.. (A)");
					System.exit(-1);
				}
			} else {
				if (!Arrays.stream(foilProcesses).allMatch(x -> ((FoilProcess) x).getDecision() == ((FoilProcess) foilProcesses[0]).getDecision())) {
					System.out.println("It's still going wrong.. (B)");
					System.exit(-1);
				}
			}

			System.out.println("Iterations: " + iterations);
		}
	}
}
