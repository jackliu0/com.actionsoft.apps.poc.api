package com.actionsoft.apps.poc.api.executor.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * 处理file
 * 
 * @author ZhangMing
 * @date 20140226
 */

public class FileUtil {
	public static String readFile(String fileName) {
		StringBuilder fileStr = new StringBuilder();
		Reader reader = null;
		try {
			// 一次读一个字符
			reader = new InputStreamReader(new FileInputStream(fileName), "UTF-8");
			int tempchar;
			while ((tempchar = reader.read()) != -1) {
				if ((char) tempchar == '<') {
					fileStr.append("&lt;");
				} else {
					fileStr.append((char) tempchar);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return fileStr.toString();
	}
}
