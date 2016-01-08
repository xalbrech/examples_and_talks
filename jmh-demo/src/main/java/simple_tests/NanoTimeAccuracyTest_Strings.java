package simple_tests;

/**
 * Show the limits of nanoTime() accuracy.
 */
public class NanoTimeAccuracyTest_Strings {

	static final int MEASUREMENTS = 200;
	public static String result;
	public static long facResult;
	
	private static long factorial(int n) {
		long result = 1;
		for (int i = 1; i <= n; i++) {
			result = result * i;
		}
		return result;
	}	

	public static void main(String[] args) {

		for (int k = 0; k < MEASUREMENTS; k++) {

			long acc = 0;
			for (int j = 0; j < 200; j++) {
				
				// add a variable delay (depends on the measurement iteration)
				for (int i = 0; i < MEASUREMENTS * (k % 40); i++) {
					facResult = factorial(500);
				}
				
				long start = System.nanoTime();
				facResult = factorial(500);
				long duration = System.nanoTime() - start;
				acc += duration;
			}

			System.out.printf("%d\n", acc / 200);
		}
	}

}
