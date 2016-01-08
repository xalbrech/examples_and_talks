package simple_tests;

public class MeasureManyTimesWithWarmupAndNestedCall {
	
	private static final int MEASUREMENTS = 200;
	private static final int WARMUP = 20_000;

	private static void callFactorial() {
		ClassUnderTest.factorial(5_000);
	}

	public static void main(String[] args) {
		
		for (int i = 0; i < WARMUP; i++) {
			callFactorial();
		}	

		for (int i = 0; i < MEASUREMENTS; i++) {
			long start = System.nanoTime();
			callFactorial();
			long duration = System.nanoTime() - start;
			System.out.printf("%d\n", duration);
		}	
	}
	
}
