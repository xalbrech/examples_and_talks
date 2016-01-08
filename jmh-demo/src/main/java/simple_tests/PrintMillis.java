package simple_tests;

public class PrintMillis {
	
	public static void main(String[] args) throws InterruptedException {
		for (int i=0; i<10000; i++) {
			long start = System.currentTimeMillis();
			Thread.sleep(0, 100);
			long end = System.currentTimeMillis();
			if (end - start < 0) {
				System.out.println(end - start);
			}	
		}
	}

}
