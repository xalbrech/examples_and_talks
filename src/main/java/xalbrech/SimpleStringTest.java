package xalbrech;


public class SimpleStringTest {
	
	public static void main(String[] args) {
		

		 long start = System.currentTimeMillis();
			String res = null;		 
		for (int i = 0; i < 22_000; i++) {
			res = ""; 
			res = res + new String("aaa") + new String("bbb");
		} 
		
		System.out.println(System.currentTimeMillis() - start);
		
		new StringBuilder(res);
	}

}
