package simple_tests;

/**
 * Show the nanoTime() accuracy.
 */
public class NanoTimeAccuracy {

	private static final int MEASUREMENTS = 200;
	private static final int WARMUP = 200;
	public static long result;

	public static void main(String[] args) {
				
		for (int i = 0; i < WARMUP; i++) {
			long result = ClassUnderTest.factorial(5_000);
		}			

		for (int j = 0; j < MEASUREMENTS; j++) {

			long start = System.nanoTime();
			long result = ClassUnderTest.factorial(1500);
			long duration = System.nanoTime() - start;

			System.out.printf("%d\n", duration);
		}
	}

}
