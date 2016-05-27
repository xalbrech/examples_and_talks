package simple_tests;

public class MeasureManyTimes {
	
	private static final int MEASUREMENTS = 2000;
	public static long result;
	
	public static void main(String[] args) {

		for (int i = 0; i < MEASUREMENTS; i++) {
			long start = System.nanoTime();
			result = ClassUnderTest.factorial(1_000);
			long duration = System.nanoTime() - start;
			System.out.printf("%d\n", duration);
		}	
	}
	
}
