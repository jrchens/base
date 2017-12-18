package pinyin4j;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Pinyin4jTest {

    private static final Logger logger = LoggerFactory.getLogger(Pinyin4jTest.class);

    /**
     * @throws Exception
     */
    @Test
    public void pinyin() throws Exception {
        char c1 = '和';
        String[] res = null;

//        res = PinyinHelper.toGwoyeuRomatzyhStringArray(c1);
//        for (String py : res
//                ) {
//            logger.info("toGwoyeuRomatzyhStringArray {}", py);
//        }
//
//        res = PinyinHelper.toTongyongPinyinStringArray(c1);
//        for (String py : res
//                ) {
//            logger.info("toTongyongPinyinStringArray {}", py);
//        }
//
//        res = PinyinHelper.toHanyuPinyinStringArray(c1);
//        for (String py : res
//                ) {
//            logger.info("toHanyuPinyinStringArray {}", py);
//        }
//        res = PinyinHelper.toMPS2PinyinStringArray(c1);
//        for (String py : res
//                ) {
//            logger.info("toMPS2PinyinStringArray {}", py);
//        }
//        res = PinyinHelper.toWadeGilesPinyinStringArray(c1);
//        for (String py : res
//                ) {
//            logger.info("toWadeGilesPinyinStringArray {}", py);
//        }
//        res = PinyinHelper.toYalePinyinStringArray(c1);
//        for (String py : res
//                ) {
//            logger.info("toYalePinyinStringArray {}", py);
//        }

        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);
        format.setToneType(HanyuPinyinToneType.WITH_TONE_MARK);

        res = PinyinHelper.toHanyuPinyinStringArray(c1, format);
        for (String py : res
                ) {
            logger.info("toHanyuPinyinStringArray HanyuPinyinOutputFormat {}", py);
        }
    }
}