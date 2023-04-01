package com.lhb.core;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ContextLoader {

	static List<String> classNames = new ArrayList<String>();

	private static Properties properties = new Properties();

	static String path = "com";

	public void init() {
		doLoadConfig("application.properties");
		doScanner(properties.getProperty("ComponentScan"));
	}

	private static void doLoadConfig(final String location) {

		InputStream resourceAsStream = new Object() {
			public InputStream getInputStream() {
				return this.getClass().getClassLoader().getResourceAsStream(location);
			}
		}.getInputStream();

		try {
			// 用Properties文件加载文件里的内容
			properties.load(resourceAsStream);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关流
			if (null != resourceAsStream) {
				try {
					resourceAsStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void inti(String path) {
		if (path == null || "".equals(path)) {
			ContextLoader.path = "com";
		} else {
			ContextLoader.path = path;
		}
	}

	private static void doScanner(String packageName) {
		// 把所有的.替换成/

		String path = Thread.currentThread().getContextClassLoader()
				.getResource("/" + packageName.replaceAll("\\.", "/")).getPath();
		File dir = new File(path);

		for (File file : dir.listFiles()) {
			if (file.isDirectory()) {
				// 递归读取包
				doScanner(packageName + "." + file.getName());
			} else {
				String className = packageName + "." + file.getName().replace(".class", "");
				classNames.add(className);
			}
		}
	}

}
