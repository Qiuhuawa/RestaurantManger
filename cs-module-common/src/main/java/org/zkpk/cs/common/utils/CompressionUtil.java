package org.zkpk.cs.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class CompressionUtil {

	public static void zipFiles(File[] srcFiles, File zipFile) {
		if (srcFiles.length > 0) {
			// 判断压缩后的文件存在不，不存在则创建
			if (!zipFile.exists()) {
				try {
					zipFile.getParentFile().mkdirs();
					zipFile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			// 创建 FileOutputStream 对象
			FileOutputStream fileOutputStream = null;
			// 创建 ZipOutputStream
			ZipOutputStream zipOutputStream = null;
			// 创建 FileInputStream 对象
			FileInputStream fileInputStream = null;
			
			try {
				// 实例化 FileOutputStream 对象
				fileOutputStream = new FileOutputStream(zipFile);
				// 实例化 ZipOutputStream 对象
				zipOutputStream = new ZipOutputStream(fileOutputStream);
				// 创建 ZipEntry 对象
				ZipEntry zipEntry = null;
				// 遍历源文件数组
				for (int i = 0; i < srcFiles.length; i++) {
					// 将源文件数组中的当前文件读入 FileInputStream 流中
					fileInputStream = new FileInputStream(srcFiles[i]);
					// 实例化 ZipEntry 对象，源文件数组中的当前文件
					zipEntry = new ZipEntry(srcFiles[i].getName());
					zipOutputStream.putNextEntry(zipEntry);
					// 该变量记录每次真正读的字节个数
					int len;
					// 定义每次读取的字节数组
					byte[] buffer = new byte[1024];
					while ((len = fileInputStream.read(buffer)) > 0) {
						zipOutputStream.write(buffer, 0, len);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					zipOutputStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					zipOutputStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					fileInputStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					fileOutputStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
