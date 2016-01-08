package simple_tests;

/**
 * Show the limits of nanoTime() accuracy.
 */
public class NanoTimeAccuracyTest_Strings {

	static final int MEASUREMENTS = 200;
	public static String result;

	public static void main(String[] args) {

		for (int k = 0; k < MEASUREMENTS; k++) {

			long acc = 0;
			for (int j = 0; j < 200; j++) {
				
				// add a variable delay (depends on the measurement iteration)
				for (int i = 0; i < MEASUREMENTS * (k % 40); i++) {
					result = "aaa".replaceAll("a", "b");
				}
				
				long start = System.nanoTime();
				result = "aaa".replaceAll("a", "b");
				long timing = System.nanoTime() - start;
				acc += timing;
			}

			System.out.printf("%d\n", acc / 200);
		}
	}

}
