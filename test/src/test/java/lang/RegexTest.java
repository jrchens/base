package lang;

import org.junit.Test;

import java.util.regex.Pattern;

public class RegexTest {
    @Test
    public void testIndexOf() {
        String str = "许  文,潘  群";
        System.out.println(str.indexOf("\u3000"));
        System.out.println(str.indexOf("\u0020"));
        System.out.println(Integer.toHexString(' '));
    }


    @Test
    public void testReplaceEntireSpace() {
        char spaceChar = 32; // 20
        System.out.println(Integer.toHexString(spaceChar));

        char entireSpaceChar = 12288; // 3000
        System.out.println(Integer.toHexString(entireSpaceChar));

        char tabChar = 9; // 9
        System.out.println(Integer.toHexString(tabChar));

        String spaceString = String.valueOf(spaceChar);
        String entireSpaceString = String.valueOf(entireSpaceChar);
        String tabString = String.valueOf(tabChar);

        StringBuffer buffer = new StringBuffer();
        buffer.append(spaceString);
        buffer.append(entireSpaceString);
        buffer.append(tabString);
        buffer.append(spaceString);
        buffer.append(entireSpaceString);
        buffer.append(tabString);
        buffer.append(spaceString);
        buffer.append(entireSpaceString);
        buffer.append(tabString);

        String regex = "[\u0009|\u0020|\u3000]+";
        String result = Pattern.compile(regex).matcher(buffer).replaceAll("o");
        System.out.println(result);
    }
}
