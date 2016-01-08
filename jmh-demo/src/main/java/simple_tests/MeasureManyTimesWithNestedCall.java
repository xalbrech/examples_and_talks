package simple_tests;

public class MeasureManyTimesWithNestedCall {
	
	private static final int MEASUREMENTS = 200;
	public static long result;
	private static final int WARMUP = 20_000;
	
	private static long factorial(int n) {
		long result = 1;
		for(int i=1; i<=n; i++) {
			result = result * i;
		}
		return result;
	}
	
	private static void callFactorial() {
		factorial(5_000);
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
