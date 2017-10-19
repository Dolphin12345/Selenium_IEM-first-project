package lib;

import org.testng.Assert;

public class EqualCompare {
	
	public static boolean isEqual(String Actual, String Expect) {
		boolean isEqual = false;
		try {
			// Verify TC: Display error message: “Wrong password. Try again.”
			Assert.assertEquals(Actual,Expect);
			isEqual = true;
		} catch (AssertionError e) {
		}
		return isEqual;
	}
}
