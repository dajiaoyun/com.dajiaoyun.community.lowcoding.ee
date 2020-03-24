package com.dajiaoyun.community.lowcoding.ee.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

public class UtilHelper {
	public static final String[] DANWEI = { "万", "仟", "佰", "拾", "亿", "仟", "佰", "拾", "万", "仟", "佰", "拾", "元", "角", "分" };// 15个
	public static final String[] DAXIE = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };// 10个
	public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",
        "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
        "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
        "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
        "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
        "W", "X", "Y", "Z" };
	
	public static void main(String[] args){
		isNumeric2("a73.00");
	}
	
	public static String trimspace(String str){
		str=str.trim();
		String ret="";
		for(int i=0;i<str.length();i++){
			if(!str.substring(i, i+1).equals(" ")){
				ret=ret+str.substring(i, i+1);
			}
		}
		return ret;
	}
	
	/**
	 * 补充小数点至两位
	 * @param curr
	 * @return
	 */
	public static String getDotFullNumber(String curr) {
		int start=curr.indexOf(".");
		if(start>0){
			String dot=curr.substring(start+1);
			if(dot.length()==0){
				curr=curr+"00";
			}else if(dot.length()==1){
				curr=curr+"0";
			}
		}else{
			curr=curr+".00";
		}
		
		return curr;
	}
	/**
	 * 前面数字凑长度
	 * @param curr
	 * @param length
	 * @return
	 */
	public static String getFullNumber(int curr, int length) {
		String ret = String.valueOf(curr);
		while (ret.length() < length) {
			ret = "0" + ret;
		}
		return ret;
	}
	
	/**
	 * 判断这个字符串是否全是数字,不带小数点
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str){ 
		if(str!=null&&!str.trim().equals("")&&str.matches("\\d*")){
			return true; 
		}else{
			return false;
		}
	}
	
	/**
	 * 判断这个字符串是否全是数字,带小数点
	 * @param str
	 * @return
	 */
	public static boolean isNumeric2(String str){ 
		String regex="([-\\+]?[1-9]([0-9]*)(\\.[0-9]+)?)|(^0$)";
		if(str!=null&&!str.trim().equals("")&&str.matches(regex)){
			return true; 
		}else{
			return false;
		}
	}
	
	public static boolean isNumeric3(String str){
		String reg="^[-|+]?\\d*([.]\\d+)?$";
		return str.matches(reg);
	}
	
	public static boolean hasValue(Object o){
		if(o==null||o.toString().trim().equals("")){
			return false;
		}
		return true;
	}
	
	/**
	 * 四舍五入
	 * @param val
	 * @return
	 */
	public static double getScaleValue(double val) {
		BigDecimal mData = new BigDecimal(val).setScale(2, BigDecimal.ROUND_HALF_UP);
		val = mData.doubleValue();
		return val;
	}

	public static double getScaleValue(double val, int scale) {
		BigDecimal mData = new BigDecimal(val).setScale(scale, BigDecimal.ROUND_HALF_UP);
		val = mData.doubleValue();
		return val;
	}
	public static String enocdeurlhz(String s,String encode) {
		String ret=s;
		try {
			ret = URLEncoder.encode(s, encode);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return ret;
	}
//	public static String enocdeurlhz(String s,String encode) {
//		StringBuffer sb = new StringBuffer();
//		for (int i = 0; i < s.length(); i++) {
//			char c = s.charAt(i);
//			if (c >= 0 && c <= 255) {
//				sb.append(c);
//			} else {
//				byte[] b;
//				try {
//					b = String.valueOf(c).getBytes(encode);
//				} catch (Exception ex) {
//					System.out.println(ex);
//					b = new byte[0];
//				}
//				for (int j = 0; j < b.length; j++) {
//					int k = b[j];
//					if (k < 0)
//						k += 256;
//					sb.append("%" + Integer.toHexString(k).toUpperCase());
//				}
//			}
//		}
//		return sb.toString();
//	}
	
	/**
	 * 返回财务格式数字，例如：160000.00 返回的格式是：160,000.00
	 * @param source
	 * @return
	 */
	public static String getCaiWuData(String source) {
		StringBuffer str = new StringBuffer("");
		if (source != null && !source.equals("") && source.length() > 0 && !source.equals("null")) {
			int dotIndex = 0;
			if (source.indexOf(".") < 0) {
				source += ".00";
			}
			dotIndex = source.indexOf(".");
			int index = 0;
			String opt = "";
			opt = source.substring(0, 1);
			if (opt.equals("-")) {
				source = source.substring(1);
				str.append("-");
				dotIndex = source.indexOf(".");
			}
			if (dotIndex < 3) {
				index += 1;
				str.append(source.substring(0, dotIndex));
			}
			if (dotIndex % 3 == 0) {
				index += dotIndex / 3;
			} else {
				index += (dotIndex - dotIndex % 3) / 3;
			}
			if (index > 0 && dotIndex >= 3) {
				for (int i = index; i > 0; i--) {
					if (i == index) {
						str.append(source.substring(0, dotIndex - i * 3));
					}
					if (dotIndex - i * 3 > 0) {
						str.append(",");
					}
					if (i >= 1) {
						str.append(source.substring(dotIndex - i * 3, dotIndex - (i - 1) * 3));
					}
				}
			}
			str.append(source.substring(dotIndex));
		}
		if (source.length() - source.lastIndexOf(".") < 3) {
			str.append("0");
		}
		int dot_index = str.toString().indexOf(".") + 2;
		int str_len = str.toString().length();
		char[] strArr = str.toString().toCharArray();
		StringBuffer rev = new StringBuffer();
		for (int i = str_len - 1; i > 0; i--) {// 除去尾数0，小数点后保留2位
			if (i > dot_index && Integer.parseInt(new Character(strArr[i]).toString()) > 0) {
				rev.append(str.toString().substring(0, i + 1));
				break;
			} else if (i == dot_index && (int) strArr[i] >= 0) {
				rev.append(str.toString().substring(0, dot_index + 1));
				break;
			}
		}
		return rev.toString();
	}

	public static String toBigType(String amount) {
		double d_amount = Double.parseDouble(amount);
		if (d_amount > 0) {
			return toBigType(d_amount);
		}
		return "";
	}
	
	public static String toBigType(double amount) {
		String str = "";
		String temp = String.valueOf((long) (amount * 100));
		int i, j, pre = 0;

		for (i = DANWEI.length - 1, j = temp.length() - 1; i >= 0 && j >= 0; i--, j--) {
			String tail = "";
			int num = temp.charAt(j) - '0';
			if (num == 0) {
				if (DANWEI[i].equals("亿") || DANWEI[i].equals("万") || DANWEI[i].equals("元")) {
					tail = DANWEI[i];
				} else if (pre == 0) {
					tail = "";
				} else {
					tail = "零";
				}
			} else {
				tail = DAXIE[num] + DANWEI[i];
			}
			pre = num;
			str = tail + str;
		}
		if (str.endsWith("元"))
			str = str.concat("整");
		return str;
	}
	
	/**
	 * 小数点后，不足两位，补足两位
	 * @param str
	 * @return
	 */
	public static String getfulldotnum(String str){
		String ret=str;
		if(ret!=null){
			int start=ret.indexOf(".");
			if(start>=0){
				String dot=ret.substring(start+1);
				if(dot.length()==1){
					ret=ret+"0";
				}
			}else{
				ret=ret+".00";
			}
			
		}
		return ret;
	}
	
	/**
	 * 判断ajax请求
	 * @param request
	 * @return
	 */
	public boolean isAjax(HttpServletRequest request){
		return  (request.getHeader("X-Requested-With") != null  && "XMLHttpRequest".equals( request.getHeader("X-Requested-With").toString())   ) ;
	}
	
	public static String generateShortUuid() {
	    StringBuffer shortBuffer = new StringBuffer();
	    String uuid = UUID.randomUUID().toString().replace("-", "");
	    for (int i = 0; i < 8; i++) {
	        String str = uuid.substring(i * 4, i * 4 + 4);
	        int x = Integer.parseInt(str, 16);
	        shortBuffer.append(chars[x % 0x3E]);
	    }
	    return shortBuffer.toString();
	 
	}
	
	public static <T> List<T> mapToList(Map<String,T> maps){
		Collection<T> cc=maps.values();
		Iterator<T> it=cc.iterator();
		List<T> data=new ArrayList<T>();
		while(it.hasNext()){
			data.add(it.next());
		}
		return data;
	}
	
	/**
	 * -----------------------------------------------------------------------
	 * getRunningPath需要一个当前程序使用的Java类的class属性参数，它可以返回打包过的
	 * Java可执行文件（jar，war）所处的系统目录名或非打包Java程序所处的目录
	 * 
	 * @param cls为Class类型
	 * @return 返回值为该类所在的Java程序运行的目录
	 *         -------------------------------------------------------------------------
	 */
	public static String getAppPath(Class cls) {
		// 检查用户传入的参数是否为空 cls:class com.util.PropertiesUtil
		if (cls == null) {
			throw new java.lang.IllegalArgumentException("参数不能为空！");
		}

		ClassLoader loader = cls.getClassLoader();
		// 获得类的全名，包括包名 //com.util.PropertiesUtil.class
		String clsName = cls.getName() + ".class";
		// 获得传入参数所在的包 package com.util
		Package pack = cls.getPackage();
		String path = "";// 包名相对应路径
		// 如果不是匿名包，将包名转化为路径
		if (pack != null) {
			String packName = pack.getName();// com.util
			// 此处简单判定是否是Java基础类库，防止用户传入JDK内置的类库
			if (packName.startsWith("java.") || packName.startsWith("javax.")) {
				throw new java.lang.IllegalArgumentException("请不要传送系统内置类！");
			}

			// 在类的名称中，去掉包名的部分，获得类的文件名 PropertiesUtil.class
			clsName = clsName.substring(packName.length() + 1);
			// 判定包名是否是简单包名，如果是，则直接将包名转换为路径
			if (packName.indexOf(".") < 0) {
				path = packName + "/";
			} else {// 否则按照包名的组成部分，将包名转换为路径
				int start = 0, end = 0;
				end = packName.indexOf(".");
				while (end != -1) {
					path = path + packName.substring(start, end) + "/";
					start = end + 1;
					end = packName.indexOf(".", start);
				}
				path = path + packName.substring(start) + "/"; // com/util/
			}
		}
		// 调用ClassLoader的getResource方法，传入包含路径信息的类文件名
		// file:/D:/Workspaces/springjdbc/bin/com/util/PropertiesUtil.class
		java.net.URL url = loader.getResource(path + clsName);
		// 从URL对象中获取路径信息
		// /D:/Workspaces/springjdbc/bin/com/util/PropertiesUtil.class
		String realPath = url.getPath();
		// 去掉路径信息中的协议名"file:"
		int pos = realPath.indexOf("file:");
		if (pos > -1) {
			realPath = realPath.substring(pos + 5);
		}
		// 去掉路径信息最后包含类文件信息的部分，得到类所在的路径
		pos = realPath.indexOf(path + clsName);
		realPath = realPath.substring(0, pos - 1);// /D:/Workspaces/springjdbc/bin
		// 如果类文件被打包到JAR等文件中时，去掉对应的JAR等打包文件名
		if (realPath.endsWith("!")) {
			realPath = realPath.substring(0, realPath.lastIndexOf("/"));
		}

		/*------------------------------------------------------------ 
		 ClassLoader的getResource方法使用了utf-8对路径信息进行了编码，当路径 
		              中存在中文和空格时，他会对这些字符进行转换，这样，得到的往往不是我们想要 
		               的真实路径，在此，调用了URLDecoder的decode方法进行解码，以便得到原始的 
		               中文及空格路径 
		-------------------------------------------------------------*/
		try {
			realPath = java.net.URLDecoder.decode(realPath, "utf-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return realPath;
	}
}