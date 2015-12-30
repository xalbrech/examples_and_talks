package xalbrech;

public class SimpleStringTest {

	public static String result;

	public static void main(String[] args) {

		long acc = 0;
		for (int j = 0; j < 100; j++) {
			long start = System.currentTimeMillis();
			for (int i = 0; i < 3_000_000; i++) {
				result = "";
				result = "" + new String("aaa");
			}
			long timing = System.currentTimeMillis() - start;
			System.out.println(timing);
			acc += timing;
		}

		System.out.printf("Average: %d", acc / 100);
	}

}
