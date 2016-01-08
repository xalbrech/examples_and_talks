package simple_tests;

public class MeasureManyTimes {
	
	private static final int MEASUREMENTS = 2000;
	public static long result;
	
	private static long factorial(int n) {
		long result = 1;
		for(int i=1; i<=n; i++) {
			result = result * i;
		}
		return result;
	}
	
	public static void main(String[] args) {

		for (int i = 0; i < MEASUREMENTS; i++) {
			long start = System.nanoTime();
			result = factorial(5000);
			long duration = System.nanoTime() - start;
			System.out.printf("%d\n", duration);
		}	
	}
	
}
