package xg.exportedEnU3D.Controller;

public class mainDeTest {
	public static void main(String[] args) {
		long f = 10;
		int i = Float.floatToIntBits( f );
		System.out.printf( "X = %X",i );
		System.out.println(Integer.toHexString(i));
	}
}
