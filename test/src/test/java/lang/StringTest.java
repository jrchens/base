package lang;

import org.junit.Test;

import java.util.Date;

public class StringTest {

    @Test
    public void testFormat() {
        String time = new Date().toString();
        String result = String.format("parse datetime (%s) success", time);
        // Mon Dec 18 13:17:28 CST 2017
        System.out.print(result);
    }


    @Test
    public void testSpecialCharacter() {
        char space = ' '; // 32
        char entireSpace = '　'; // 12288
        char tab = '	'; // 9
        char comma = ',';
        char entireComma = '，';
        char[] chars = {space, entireSpace, tab, comma, entireComma};
        for (char c : chars) {
            System.out.println(String.format("bin: (%s), dec: (%s), hex: (%s)", Integer.toBinaryString(c), Integer.valueOf(c), Integer.toHexString(c)));
        }
        char entireSpaceChar = 12288;
        String entireSpaceString = String.valueOf(entireSpaceChar);
        System.out.println(String.format("|%s|", entireSpaceString));
    }
}
