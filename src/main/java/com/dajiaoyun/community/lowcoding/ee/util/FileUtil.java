package com.dajiaoyun.community.lowcoding.ee.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class FileUtil {
	private static Map<String, Properties> props;

	public static void main(String[] args) {
		String path = "E:/web/apache-tomcat-7.0.33/webapps/oms/WEB-INF/classes/thirdpartdata.properties";
		readValue(path, "E102920226");
	}

	public static String getAbsolutefilepath() {
		// String path =
		// FileUtil.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		// System.out.println("aaaaaa:"+path);
		String path = null;
		if (Thread.currentThread().getContextClassLoader().getResource("/") != null) {
			path = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
		}
		if (path == null && ClassLoader.getSystemResource(".") != null) {
			path = ClassLoader.getSystemResource(".").toString();
			path = path.substring(6);
			path = path.substring(0, path.lastIndexOf("/"));
			String osname = System.getProperty("os.name");
			if (!osname.contains("Windows")) {
				path = "/" + path;
			}
		}
		if (path == null && FileUtil.class.getProtectionDomain().getCodeSource() != null && FileUtil.class.getProtectionDomain().getCodeSource().getLocation() != null && FileUtil.class.getProtectionDomain().getCodeSource().getLocation().getPath() != null) {
			// /E:/project/com.eshopinone.jetblue.core.model/bin/
			path = FileUtil.class.getProtectionDomain().getCodeSource().getLocation().getPath();
			int end = path.indexOf("WEB-INF");
			path = path.substring(1, path.length());
			if (end > 0) {
				path = path.substring(1, end + 7) + "/classes";
			}

		}
		return path;
	}

	public static String readValue(InputStream is, String key) {
		try {
			if (props == null) {
				props = new HashMap<String, Properties>();
			}
			Properties prop = new Properties();
			prop.load(is);
			String value = prop.getProperty(key);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String readValue(String filePath, String key) {
		try {
			if (props == null) {
				props = new HashMap<String, Properties>();
			}
			Properties prop = null;
			if (props.get(filePath) == null) {
				prop = new Properties();
				InputStream in = new BufferedInputStream(new FileInputStream(filePath));
				prop.load(in);
				props.put(filePath, prop);
				in.close();
			} else {
				prop = props.get(filePath);
			}
			String value = prop.getProperty(key);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void reload(String filePath) {
		try {
			if (props == null) {
				props = new HashMap<String, Properties>();
			}
			Properties prop = new Properties();
			InputStream in = new BufferedInputStream(new FileInputStream(filePath));
			prop.load(in);
			props.put(filePath, prop);
			in.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String readutf8Value(String filePath, String key) {
		String str = "";
		try {
			if (props == null) {
				props = new HashMap<String, Properties>();
			}
			Properties prop = new Properties();
			if (props.get(filePath) == null) {
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "utf-8"));// 中文
				while ((str = br.readLine()) != null) {
					if (!str.contains("="))
						continue;
					String[] strs = str.split("=");
					prop.setProperty(strs[0], strs[1]);
				}
				props.put(filePath, prop);
				br.close();
			} else {
				prop = props.get(filePath);
			}
			String value = prop.getProperty(key);
			return value;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	public static void readProperties(String filePath) {
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(filePath));
			props.load(in);
			Enumeration en = props.propertyNames();
			while (en.hasMoreElements()) {
				String key = (String) en.nextElement();
				String Property = props.getProperty(key);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void writeProperties(String filePath, String parameterName, String parameterValue) {
		Properties prop = new Properties();
		try {
			InputStream fis = new FileInputStream(filePath);
			prop.load(fis);
			OutputStream fos = new FileOutputStream(filePath);
			prop.setProperty(parameterName, parameterValue);
			prop.store(fos, "Update '" + parameterName + "' value");
			fos.close();
			fis.close();
		} catch (IOException e) {
			System.err.println("Visit " + filePath + " for updating " + parameterName + " value error");
		}
	}

	public static File rename(File file) {
		String body = "";
		String ext = "";
		Date date = new Date();
		int pot = file.getName().lastIndexOf(".");
		// oldfilename=file.getName().substring(0,pot);
		if (pot != -1) {
			body = date.getTime() + "";
			ext = file.getName().substring(pot).toLowerCase();
		} else {
			body = (new Date()).getTime() + "";
			ext = "";
		}
		String newName = body + ext;
		file = new File(file.getParent(), newName);
		return file;

	}
	
//	public static String loadResource(String filePath) {
//		Resource resource= new ClassPathResource(filePath);
//		String str = "";
//		String uiXml = "";
//		try {
//			FileInputStream fis=new FileInputStream(resource.getFile());
//			InputStreamReader is=new InputStreamReader(fis, "utf-8");
//			BufferedReader br = new BufferedReader(is);// 中文
//			while ((str = br.readLine()) != null) {
//				uiXml = uiXml + str;
//			}
//			fis.close();
//			is.close();
//			br.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return uiXml;
//	}
	
	public static String loadResource(String filePath) {
		String str = "";
		String uiXml = "";
		try {
			InputStream fis = FileUtil.class.getClassLoader().getResourceAsStream(filePath);
			InputStreamReader is=new InputStreamReader(fis, "utf-8");
			BufferedReader br = new BufferedReader(is);// 中文
			while ((str = br.readLine()) != null) {
				uiXml = uiXml + str;
			}
			fis.close();
			is.close();
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return uiXml;
	}
	
	public static String loadfile(String filePath) {
		String str = "";
		String value = "";
		try {
			FileInputStream fi=new FileInputStream(filePath);
			InputStreamReader is=new InputStreamReader(fi, "utf-8");
			BufferedReader br = new BufferedReader(is);// 中文
			while ((str = br.readLine()) != null) {
				value = value + str;
			}
			
			fi.close();
			is.close();
			br.close();

			return value;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}
}