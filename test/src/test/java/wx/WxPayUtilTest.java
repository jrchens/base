package wx;

import com.github.wxpay.sdk.WXPayUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class WxPayUtilTest {


    private static final Logger logger = LoggerFactory.getLogger(WxPayUtilTest.class);

    @Test
    public void testXmlToMap() throws Exception {
        String xml = "<xml><appid><![CDATA[wx0fd9ea5b91f95791]]></appid><bank_type><![CDATA[CFT]]></bank_type><cash_fee><![CDATA[1]]></cash_fee><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[Y]]></is_subscribe><mch_id><![CDATA[1488351332]]></mch_id><nonce_str><![CDATA[aMW3mI0StisS4YFf]]></nonce_str><openid><![CDATA[ovVfEs7WV5jXDA7Q-U9jftZGn4G0]]></openid><out_trade_no><![CDATA[20180203013624972]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code><return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[C59FDA91D78FB7E20A4A5C7DE9F31597]]></sign><time_end><![CDATA[20180202173715]]></time_end><total_fee>1</total_fee><trade_type><![CDATA[JSAPI]]></trade_type><transaction_id><![CDATA[4200000055201802025683337425]]></transaction_id></xml>";
        Map<String, String> wxPayCallback = WXPayUtil.xmlToMap(xml);
        logger.info("bank_type: {}", wxPayCallback.get("bank_type"));
    }
}
