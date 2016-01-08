package simple_tests;

/**
 * Show the limits of nanoTime() accuracy.
 */
public class NanoTimeAccuracyTest_Factorial {

	static final int MEASUREMENTS = 200;
	public static long result;

	private static long factorial(int n) {
		long result = 1;
		for (int i = 1; i <= n; i++) {
			result = result * i;
		}
		return result;
	}

	public static void main(String[] args) {

		for (int j = 0; j < MEASUREMENTS; j++) {

			result = factorial(150 * j);

			long start = System.nanoTime();
			result = factorial(5_000);
			long duration = System.nanoTime() - start;

			System.out.printf("%d\n", duration);
		}
	}

}
