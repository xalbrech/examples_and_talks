package simple_tests;

public class PrintMillis {
	
	public static void main(String[] args) throws InterruptedException {
		for (int i=0; i<100000; i++) {
			long start = System.currentTimeMillis();
			long end = System.currentTimeMillis();
			if (end - start < 0) {
				System.out.println(end - start);
			}	
		}
	}

}
