package Snippets;
public class UnitTest {
    public static void test(boolean got, boolean expected, String name) {
        int l = name.length();
        if (got == expected) {
            System.out.println("Test " + name + String.format("%"+(32-l)+"s", "")+ " passed");
        } else {
            System.out.println("Test " + name + String.format("%"+(32-l)+"s", "")+ " FAILED X");
        }
    }
}