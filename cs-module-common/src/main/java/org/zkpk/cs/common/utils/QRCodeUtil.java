package org.zkpk.cs.common.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QRCodeUtil {
	
	//日志
    private static final Logger logger = LoggerFactory.getLogger(QRCodeUtil.class);
	
	public static void createQRCode(String imagePath, int width, int height, String format, String content) {
		//设置生个图片宽度
		if (width == 0) {
			width = 300;
		}
		//设置生个图片高度
		if (height == 0) {
			height = 300;
		}
		//设置生个图片格式
		if (format == null || "".equals(format)) {
			format = "png";
		}
        //设置二维码内容
		if (content == null || "".equals(content)) {
			content = "您什么也没设置";
		}
        //设置额外参数
        Map<EncodeHintType, Object> map = new HashMap<>();
        //设置编码集
        map.put(EncodeHintType.CHARACTER_SET, "utf-8");
        //容错率，指定容错等级，例如二维码中使用的ErrorCorrectionLevel
        //L级：约可纠错7%的数据码字,M级：约可纠错15%的数据码字,Q级：约可纠错25%的数据码字,H级：约可纠错30%的数据码字
        map.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        //生成条码的时候使用，指定边距，单位像素，受格式的影响。类型Integer, 或String代表的数字类型
        map.put(EncodeHintType.MARGIN, 1);
        try {
            //生成二维码，（参数为：编码的内容、编码的方式（二维码、条形码...）、首选的宽度、首选的高度、编码时的额外参数）
            BitMatrix encode = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, map);
            //生成二维码图片，并将二维码写到文件里
            File codeFile = new File(imagePath);
            if (!codeFile.exists()) {
            	codeFile.getParentFile().mkdirs();
            	codeFile.createNewFile();
            }
            MatrixToImageWriter.writeToPath(encode, format, codeFile.toPath());
        } catch (WriterException | IOException e) {
        	logger.error("创建二维码出错了！错误信息：" + e.getMessage());
        }
	}
	
	public static String readQRCode(String imagePath) {
		String content = "";
		try {
			MultiFormatReader formatReader = new MultiFormatReader();
	        File file = new File(imagePath);
	        BufferedImage image = ImageIO.read(file);
	        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));
	        //定义二维码的参数
	        HashMap<DecodeHintType, Object> hints = new HashMap<>();
	        hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
	        Result result = formatReader.decode(binaryBitmap, hints); 
	        content = result.toString();
		} catch (IOException | NotFoundException e) {
			content = "读取出错了！";
			logger.error("读取二维码出错了！错误信息：" + e.getMessage());
        }
        return content;
	}

}
