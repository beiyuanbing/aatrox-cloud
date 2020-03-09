package com.aatrox.web.base.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author aatrox
 * @desc 二维码工具类
 * @date 2019-08-15
 */
public class QrCodeUtil {
    private static final int BLACK = -16777216;
    private static final int WHITE = -1;

    public static boolean createQrCode(OutputStream outputStream, String content, int qrCodeSize, String imageFormat) throws Exception {
        Map<EncodeHintType, String> his = new HashMap<EncodeHintType, String>();
        his.put(EncodeHintType.CHARACTER_SET, "utf-8");
        BitMatrix encode = (new MultiFormatWriter()).encode(content, BarcodeFormat.QR_CODE, qrCodeSize, qrCodeSize, his);
        int codeWidth = encode.getWidth();
        int codeHeight = encode.getHeight();
        BufferedImage image = new BufferedImage(codeWidth, codeHeight, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < codeWidth; i++) {
            for (int j = 0; j < codeHeight; j++) {
                image.setRGB(i, j, encode.get(i, j) ? -16777216 : -1);
            }
        }
        return ImageIO.write(image, imageFormat, outputStream);
    }

    public static void main(String[] args) throws Exception {
        File file=new File("/Users/apple/Desktop/Qrcode.png");
        FileOutputStream fileOutputStream=new FileOutputStream(file);
        createQrCode(fileOutputStream,"/nnnn",250,"png");
    }
}