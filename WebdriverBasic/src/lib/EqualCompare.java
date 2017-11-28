package lib;

public class EqualCompare {
	
	public static boolean isEqual(String Actual, String Expect) {
		Actual = Actual.trim();
		Expect = Expect.trim();
		return Actual.equals(Expect);
	}
}
