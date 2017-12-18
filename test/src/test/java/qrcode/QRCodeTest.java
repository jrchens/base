package qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.junit.Test;

import java.io.File;

public class QRCodeTest {
    // 20160626220055033

    @Test
    public void write() throws Exception {
        BitMatrix bitMatrix = new MultiFormatWriter().encode("20160626220055033", BarcodeFormat.CODE_128, 200, 80,
                null);
        MatrixToImageWriter.writeToPath(bitMatrix, "png", new File("/Users/shengchen/Pictures/20160626220055033.png").toPath());

    }
}
