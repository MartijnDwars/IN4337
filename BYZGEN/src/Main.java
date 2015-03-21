import java.util.Arrays;
import java.util.Random;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		if (args.length < 2) {
			System.out.println("Usage: Main <n> <t>");
			System.exit(-1);
		}

		int n = Integer.parseInt(args[0]);
		int t = Integer.parseInt(args[1]);

		int iterations = run(n, t);

		System.out.println("Iterations: " + iterations);
	}

	public static int run(int n, int t) {
		Actor[] processes = new Actor[n];

		// Initial
		int[] initial = new int[n-t];

		for (int i = 0; i < n-t; i++) {
			initial[i] = new Random().nextInt(2);
		}

		// Create t adversaries that send random votes
		for (int i = 0; i < t; i++) {
			processes[i] = new Adversary(i, n);
		}

		// Create n process with *random* initial value
		for (int i = t; i < n; i++) {
			processes[i] = new Process(i, n, initial[i-t]);
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

			// Terminate if all processes have decided
			if (Arrays.stream(processes).allMatch(Actor::hasDecided)) {
				break;
			}

			// Terminate if you've reached more then 50 iterations
			if (iterations == 20) {
				return -1;
			}
		}

		// Correctness checking
		Object[] goodProcesses = Arrays.stream(processes).filter(p -> !(p instanceof Adversary)).toArray();

		if (Arrays.stream(initial).allMatch(x -> x == initial[0])) {
			if (!Arrays.stream(goodProcesses).allMatch(x -> ((Process) x).getDecision() == initial[0])) {
				return -1;
			}
		} else {
			if (!Arrays.stream(goodProcesses).allMatch(x -> ((Process) x).getDecision() == ((Process) goodProcesses[0]).getDecision())) {
				return -1;
			}
		}

		return iterations;
	}
}
