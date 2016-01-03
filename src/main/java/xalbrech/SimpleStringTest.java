package xalbrech;

public class SimpleStringTest {

	public static String result;

	public static void main(String[] args) {

		for (int k = 0; k < 2000; k++) {

			for (int i = 0; i < 10 * k; i++) {
				result = "" + new String("aaa").replaceAll("a", "b");
			}

			long acc = 0;
			for (int j = 0; j < 200; j++) {
				long start = System.nanoTime();
				for (int i = 0; i < 500; i++) {
					result = "";
					result = "" + new String("aaa").replaceAll("a", "b");
				}
				long timing = System.nanoTime() - start;
				// System.out.println(timing);
				acc += timing;
			}

			System.out.printf("%d\n", acc);
		}
	}

}
