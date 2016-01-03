package xalbrech;

public class SimpleStringTest {

	public static String result;

	public static void main(String[] args) {

		for (int k = 0; k < 2000; k++) {

			long acc = 0;
			for (int j = 0; j < 200; j++) {
				
				for (int i = 0; i < 200 * (k % 40); i++) {
					result = "" + new String("aaa").replaceAll("a", "b");
				}				
				
				long start = System.nanoTime();
				//for (int i = 0; i < 100; i++) {
					result = "" + new String("aaa").replaceAll("a", "b");
				//}
				long timing = System.nanoTime() - start;
				// System.out.println(timing);
				acc += timing;
			}

			System.out.printf("%d\n", acc / 200);
		}
	}

}
